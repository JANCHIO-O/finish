package com.example.library.circulation.service.impl;

import com.example.library.circulation.dto.BorrowRecordDto;
import com.example.library.circulation.dto.CirculationBookDto;
import com.example.library.circulation.dto.NoticeDto;
import com.example.library.circulation.entity.NoticeEntity;
import com.example.library.circulation.entity.ReservationEntity;
import com.example.library.circulation.repository.BorrowRecordMapper;
import com.example.library.circulation.repository.CirculationBookMapper;
import com.example.library.circulation.repository.NoticeRepository;
import com.example.library.circulation.repository.ReaderInfoMapper;
import com.example.library.circulation.repository.ReservationRepository;
import com.example.library.circulation.service.CirculationService;
import com.example.library.common.entity.BorrowRecord;
import com.example.library.common.entity.CirculationBook;
import com.example.library.common.entity.ReaderInfo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CirculationServiceImpl implements CirculationService {
    @Autowired
    private ReaderInfoMapper readerInfoRepository;
    @Autowired
    private CirculationBookMapper circulationBookRepository;
    @Autowired
    private BorrowRecordMapper borrowRecordRepository;
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    // 超期配置 - 直接修改此处数值即可生效
    private static final int BORROW_FREE_DAYS = 30;  // 免费借阅天数
    private static final double PENALTY_PER_DAY = 0.10; // 超期每日罚款金额

    // 借阅图书 + 自动生成借阅成功公告
    @Override
    @Transactional(rollbackOn = Exception.class)
    public String borrowBook(String cardNo, String bookId) {
        Optional<ReaderInfo> readerOpt = readerInfoRepository.findById(cardNo);
        if (readerOpt.isEmpty()) return "借书证号不存在！";
        // 修复：图书主键是ISBN，用findByBookId查询，不是findById
        Optional<CirculationBook> bookOpt = circulationBookRepository.findByBookId(bookId);
        if (bookOpt.isEmpty()) return "图书编号不存在！";

        CirculationBook book = bookOpt.get();
        if (book.getStatus() == 0) return "图书已借出，无法借阅！";

        // 预约人专属借阅权限
        if (book.getStatus() == 2) {
            List<ReservationEntity> myReserve = reservationRepository.findByBookIdAndCardNoAndStatus(bookId, cardNo, "有效");
            if (myReserve == null || myReserve.size() == 0) {
                return "图书已被他人预约，无法借阅！";
            }
        }

        // 重复借阅判断：存在未归还借阅记录时禁止借阅
        Optional<BorrowRecord> latestBorrow = borrowRecordRepository
                .findTopByBookIdAndCardNoAndEventTypeOrderByFlowDateDesc(bookId, cardNo, "借阅");
        Optional<BorrowRecord> latestReturn = borrowRecordRepository
                .findTopByBookIdAndCardNoAndEventTypeOrderByFlowDateDesc(bookId, cardNo, "还书");
        boolean hasActiveBorrow = latestBorrow.isPresent()
                && (latestReturn.isEmpty()
                || latestBorrow.get().getFlowDate().after(latestReturn.get().getFlowDate()))
                && latestBorrow.get().getReturnDate() == null;
        if (hasActiveBorrow) {
            return "您已借阅过该图书，归还后才能再次借阅！";
        }

        // 生成借阅记录 - 补全所有字段，适配完整DTO
        BorrowRecord record = new BorrowRecord();
        record.setFlowId(UUID.randomUUID().toString().replace("-","").substring(0,10));
        record.setBookId(bookId);
        record.setIsbn(book.getIsbn());
        record.setTitle(book.getTitle());
        record.setAuthor(book.getAuthor());
        record.setEventType("借阅");
        record.setCardNo(cardNo);
        record.setName(readerOpt.get().getName());
        record.setFlowDate(new java.sql.Date(System.currentTimeMillis()));
        borrowRecordRepository.save(record);

        // 更新图书状态+清空本人预约记录
        book.setStatus(0);
        List<ReservationEntity> myReserve = reservationRepository.findByBookIdAndCardNoAndStatus(bookId, cardNo, "有效");
        if (myReserve != null && !myReserve.isEmpty()) {
            myReserve.get(0).setStatus("失效");
            reservationRepository.save(myReserve.get(0));
        }
        circulationBookRepository.save(book);

        // 自动生成借阅成功通知公告
        createNotice("借阅成功通知",
                "您好！您已成功借阅图书《"+book.getTitle()+"》(编号："+bookId+")，免费借阅期"+BORROW_FREE_DAYS+"天，请按时归还。",
                cardNo, "borrow", bookId);

        return "图书借阅成功！";
    }

    // 归还图书 + 自动生成还书/超期公告
    @Override
    @Transactional(rollbackOn = Exception.class)
    public String returnBook(String cardNo, String bookId) {
        Optional<ReaderInfo> readerOpt = readerInfoRepository.findById(cardNo);
        if (readerOpt.isEmpty()) return "借书证号不存在！";
        // 修复：图书主键是ISBN，用findByBookId查询
        Optional<CirculationBook> bookOpt = circulationBookRepository.findByBookId(bookId);
        if (bookOpt.isEmpty()) return "图书编号不存在！";

        CirculationBook book = bookOpt.get();
        if (book.getStatus() == 1) return "图书未被借出，无需归还！";

        Optional<BorrowRecord> latestBorrow = borrowRecordRepository
                .findTopByBookIdAndCardNoAndEventTypeOrderByFlowDateDesc(bookId, cardNo, "借阅");
        Optional<BorrowRecord> latestReturn = borrowRecordRepository
                .findTopByBookIdAndCardNoAndEventTypeOrderByFlowDateDesc(bookId, cardNo, "还书");
        boolean hasActiveBorrow = latestBorrow.isPresent()
                && (latestReturn.isEmpty()
                || latestBorrow.get().getFlowDate().after(latestReturn.get().getFlowDate()))
                && latestBorrow.get().getReturnDate() == null;
        if (!hasActiveBorrow) return "无此图书的借阅记录！";
        BorrowRecord borrowEntity = latestBorrow.get();

        // 生成还书记录 - 补全所有字段
        BorrowRecord returnRecord = new BorrowRecord();
        returnRecord.setFlowId(UUID.randomUUID().toString().replace("-","").substring(0,10));
        returnRecord.setBookId(bookId);
        returnRecord.setIsbn(book.getIsbn());
        returnRecord.setTitle(book.getTitle());
        returnRecord.setAuthor(book.getAuthor());
        returnRecord.setEventType("还书");
        returnRecord.setCardNo(cardNo);
        returnRecord.setName(readerOpt.get().getName());
        returnRecord.setFlowDate(new java.sql.Date(System.currentTimeMillis()));
        returnRecord.setReturnDate(new java.sql.Date(System.currentTimeMillis()));

        // 超期计算
        long borrowTime = borrowEntity.getFlowDate().getTime();
        long nowTime = System.currentTimeMillis();
        long daysDiff = (nowTime - borrowTime) / (1000 * 60 * 60 * 24);
        int overdueDays = daysDiff > BORROW_FREE_DAYS ? (int) (daysDiff - BORROW_FREE_DAYS) : 0;
        double penalty = overdueDays * PENALTY_PER_DAY;
        returnRecord.setOverdueDays(overdueDays);
        returnRecord.setPenalty(penalty);
        borrowEntity.setReturnDate(new java.sql.Date(System.currentTimeMillis()));
        borrowEntity.setOverdueDays(overdueDays);
        borrowEntity.setPenalty(penalty);

        // 归还后图书状态：有预约→已预约，无预约→可借阅
        List<ReservationEntity> reservations = reservationRepository.findByBookIdAndStatus(bookId, "有效");
        if (reservations != null && !reservations.isEmpty()) {
            book.setStatus(2);
        } else {
            book.setStatus(1);
        }
        circulationBookRepository.save(book);
        borrowRecordRepository.save(borrowEntity);
        borrowRecordRepository.save(returnRecord);

        String returnMsg;
        if (overdueDays > 0) {
            List<NoticeEntity> overdueNotices = noticeRepository
                    .findByBizTypeAndBizIdAndTargetCardNoAndIsValid("overdue", bookId, cardNo, "有效");
            for (NoticeEntity notice : overdueNotices) {
                notice.setIsValid("无效");
                noticeRepository.save(notice);
            }
            returnMsg = "还书成功！超期"+overdueDays+"天，罚金¥"+String.format("%.2f",penalty);
            createNotice("超期归还通知",
                    "您好！您归还的图书《"+book.getTitle()+"》(编号："+bookId+")已超期"+overdueDays+"天，已缴纳罚金¥"+String.format("%.2f",penalty)+"，感谢配合！",
                    cardNo, "borrow", bookId);
        } else {
            returnMsg = "还书成功！无超期";
            createNotice("还书成功通知",
                    "您好！您已成功归还图书《"+book.getTitle()+"》(编号："+bookId+")，感谢借阅，期待您再次借阅！",
                    cardNo, "borrow", bookId);
        }
        return returnMsg;
    }

    // 预约图书 + 自动生成预约成功公告
    @Override
    @Transactional(rollbackOn = Exception.class)
    public String reserveBook(String cardNo, String bookId) {
        Optional<ReaderInfo> readerOpt = readerInfoRepository.findById(cardNo);
        if (readerOpt.isEmpty()) return "借书证号不存在！";
        // 修复：图书主键是ISBN，用findByBookId查询
        Optional<CirculationBook> bookOpt = circulationBookRepository.findByBookId(bookId);
        if (bookOpt.isEmpty()) return "图书编号不存在！";

        CirculationBook book = bookOpt.get();
        if (book.getStatus() == 1) return "图书可直接借阅，无需预约！";
        if (book.getStatus() == 2) return "图书已被他人预约，无法重复预约！";

        List<ReservationEntity> myReservations = reservationRepository.findByBookIdAndCardNoAndStatus(bookId, cardNo, "有效");
        if (myReservations != null && !myReservations.isEmpty()) {
            return "您已预约过该图书，无需重复预约！";
        }

        // 生成预约记录
        ReservationEntity reservation = new ReservationEntity();
        reservation.setReserveId("RES"+UUID.randomUUID().toString().replace("-","").substring(0,10));
        reservation.setBookId(bookId);
        reservation.setCardNo(cardNo);
        reservation.setReaderName(readerOpt.get().getName());
        reservation.setReserveTime(new Date());
        reservation.setStatus("有效");
        reservationRepository.save(reservation);

        book.setStatus(2);
        circulationBookRepository.save(book);

        // 自动生成预约成功通知公告
        createNotice("图书预约成功通知",
                "您好！您已成功预约图书《"+book.getTitle()+"》(编号："+bookId+")，图书归还后您可优先借阅，请留意通知！",
                cardNo, "reserve", bookId);

        return "图书预约成功！归还后您可优先借阅";
    }

    @Override
    public List<BorrowRecordDto> getBorrowRecords(String cardNo) {
        List<BorrowRecord> entityList = borrowRecordRepository.findByCardNo(cardNo);
        List<BorrowRecordDto> dtoList = new ArrayList<>();
        for (BorrowRecord entity : entityList) {
            BorrowRecordDto dto = new BorrowRecordDto();
            // 修复：补全DTO所有字段赋值，匹配完整实体
            dto.setFlowId(entity.getFlowId());
            dto.setBookId(entity.getBookId());
            dto.setIsbn(entity.getIsbn());
            dto.setTitle(entity.getTitle());
            dto.setAuthor(entity.getAuthor());
            dto.setEventType(entity.getEventType());
            dto.setCardNo(entity.getCardNo());
            dto.setName(entity.getName());
            dto.setFlowDate(entity.getFlowDate());
            dto.setReturnDate(entity.getReturnDate());
            dto.setOverdueDays(entity.getOverdueDays());
            dto.setPenalty(entity.getPenalty());
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public List<BorrowRecordDto> getOverdueRecords() {
        Date freeDaysAgo = new Date(System.currentTimeMillis() - (long)BORROW_FREE_DAYS * 24 * 60 * 60 * 1000);
        List<BorrowRecord> entityList = borrowRecordRepository.findByReturnDateIsNullAndFlowDateBefore(freeDaysAgo);
        return buildOverdueDtos(entityList);
    }

    @Override
    public List<BorrowRecordDto> getOverdueRecords(String cardNo) {
        if (cardNo == null || cardNo.trim().isEmpty()) {
            return new ArrayList<>();
        }
        Date freeDaysAgo = new Date(System.currentTimeMillis() - (long)BORROW_FREE_DAYS * 24 * 60 * 60 * 1000);
        List<BorrowRecord> entityList = borrowRecordRepository
                .findByCardNoAndReturnDateIsNullAndFlowDateBefore(cardNo, freeDaysAgo);
        return buildOverdueDtos(entityList);
    }

    @Override
    public List<CirculationBookDto> getAvailableBooks() {
        List<CirculationBook> entityList = circulationBookRepository.findAll();
        List<CirculationBookDto> dtoList = new ArrayList<>();
        for (CirculationBook entity : entityList) {
            CirculationBookDto dto = new CirculationBookDto();
            dto.setBookId(entity.getBookId());
            dto.setIsbn(entity.getIsbn());
            dto.setTitle(entity.getTitle());
            dto.setCatalogDate(entity.getCatalogDate());
            Optional<BorrowRecord> latestBorrow = borrowRecordRepository
                    .findTopByBookIdAndEventTypeOrderByFlowDateDesc(entity.getBookId(), "借阅");
            Optional<BorrowRecord> latestReturn = borrowRecordRepository
                    .findTopByBookIdAndEventTypeOrderByFlowDateDesc(entity.getBookId(), "还书");
            boolean activeBorrow = latestBorrow.isPresent()
                    && (latestReturn.isEmpty()
                    || latestBorrow.get().getFlowDate().after(latestReturn.get().getFlowDate()))
                    && latestBorrow.get().getReturnDate() == null;
            if (activeBorrow) {
                dto.setStatus(0);
            } else if (entity.getStatus() != null && entity.getStatus() == 2) {
                dto.setStatus(2);
            } else {
                dto.setStatus(1);
            }
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public List<NoticeDto> getValidNotices(String cardNo) {
        // 修复空指针：cardNo为空时赋值空字符串
        String queryCardNo = cardNo == null || cardNo.trim().isEmpty() ? "" : cardNo;
        List<NoticeEntity> entityList = noticeRepository.findByIsValidAndTargetCardNoIsNullOrTargetCardNoOrderByPublishTimeDesc("有效", queryCardNo);
        List<NoticeDto> dtoList = new ArrayList<>();
        for (NoticeEntity entity : entityList) {
            NoticeDto dto = new NoticeDto();
            setNoticeDtoValue(dto, entity);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public List<NoticeDto> getAllNotices() {
        // 修复核心错误：调用正确的无参JPA方法
        List<NoticeEntity> entityList = noticeRepository.findAllByOrderByPublishTimeDesc();
        List<NoticeDto> dtoList = new ArrayList<>();
        for (NoticeEntity entity : entityList) {
            NoticeDto dto = new NoticeDto();
            setNoticeDtoValue(dto, entity);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public String addNotice(String noticeTitle, String noticeContent, String publisher) {
        if (noticeTitle.isEmpty() || noticeContent.isEmpty()) {
            return "公告标题和内容不能为空！";
        }
        NoticeEntity notice = new NoticeEntity();
        notice.setNoticeId("G"+UUID.randomUUID().toString().replace("-","").substring(0,11));
        notice.setNoticeTitle(noticeTitle);
        notice.setNoticeContent(noticeContent);
        notice.setTargetCardNo(null);
        notice.setBizType("system");
        notice.setBizId(null);
        notice.setPublishTime(new Date());
        notice.setPublisher(publisher);
        notice.setIsValid("有效");
        noticeRepository.save(notice);
        return "公告发布成功！";
    }

    @Override
    public String disableNotice(String noticeId) {
        Optional<NoticeEntity> noticeOpt = noticeRepository.findById(noticeId);
        if (noticeOpt.isEmpty()) {
            return "公告编号不存在！";
        }
        NoticeEntity notice = noticeOpt.get();
        notice.setIsValid("无效");
        noticeRepository.save(notice);
        return "公告已停用，不再展示！";
    }

    // 公告实体转DTO
    private void setNoticeDtoValue(NoticeDto dto, NoticeEntity entity) {
        dto.setNoticeId(entity.getNoticeId());
        dto.setNoticeTitle(entity.getNoticeTitle());
        dto.setNoticeContent(entity.getNoticeContent());
        dto.setTargetCardNo(entity.getTargetCardNo());
        dto.setBizType(entity.getBizType());
        dto.setBizId(entity.getBizId());
        dto.setPublishTime(entity.getPublishTime());
        dto.setPublisher(entity.getPublisher());
        dto.setIsValid(entity.getIsValid());
    }

    // 自动生成公告工具方法
    private void createNotice(String title, String content, String targetCardNo, String bizType, String bizId) {
        NoticeEntity notice = new NoticeEntity();
        notice.setNoticeId("G"+UUID.randomUUID().toString().replace("-","").substring(0,11));
        notice.setNoticeTitle(title);
        notice.setNoticeContent(content);
        notice.setTargetCardNo(targetCardNo);
        notice.setBizType(bizType);
        notice.setBizId(bizId);
        notice.setPublishTime(new Date());
        notice.setPublisher("系统");
        notice.setIsValid("有效");
        noticeRepository.save(notice);
    }

    private List<BorrowRecordDto> buildOverdueDtos(List<BorrowRecord> entityList) {
        List<BorrowRecordDto> dtoList = new ArrayList<>();
        for (BorrowRecord entity : entityList) {
            BorrowRecordDto dto = new BorrowRecordDto();
            dto.setFlowId(entity.getFlowId());
            dto.setBookId(entity.getBookId());
            dto.setIsbn(entity.getIsbn());
            dto.setTitle(entity.getTitle());
            dto.setAuthor(entity.getAuthor());
            dto.setCardNo(entity.getCardNo());
            dto.setName(entity.getName());
            dto.setFlowDate(entity.getFlowDate());
            long daysDiff = (System.currentTimeMillis() - entity.getFlowDate().getTime()) / (1000 * 60 * 60 * 24);
            int overdueDays = (int) (daysDiff - BORROW_FREE_DAYS);
            dto.setOverdueDays(overdueDays);
            dto.setPenalty(overdueDays * PENALTY_PER_DAY);
            dtoList.add(dto);

            boolean noticeExists = noticeRepository
                    .existsByBizTypeAndBizIdAndTargetCardNoAndIsValid("overdue", entity.getBookId(), entity.getCardNo(), "有效");
            if (!noticeExists) {
                createNotice("图书超期提醒",
                        "您好！您借阅的图书《"+entity.getTitle()+"》(编号："+entity.getBookId()+")已超期"+overdueDays+"天，预计罚金¥"+String.format("%.2f",overdueDays * PENALTY_PER_DAY)+"，请尽快归还！",
                        entity.getCardNo(), "overdue", entity.getBookId());
            }
        }
        return dtoList;
    }
}
