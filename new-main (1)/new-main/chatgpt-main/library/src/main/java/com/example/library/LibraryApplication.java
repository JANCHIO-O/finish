package com.example.library;

import com.example.library.common.entity.AcceptanceRecord;
import com.example.library.common.entity.AcquisitionRecord;
import com.example.library.common.entity.BorrowRecord;
import com.example.library.common.entity.CirculationBook;
import com.example.library.common.entity.ReaderInfo;
import com.example.library.common.entity.UserAccount;
import com.example.library.common.repository.AcceptanceRecordRepository;
import com.example.library.common.repository.AcquisitionRecordRepository;
import com.example.library.common.repository.BorrowRecordRepository;
import com.example.library.common.repository.CirculationBookRepository;
import com.example.library.common.repository.ReaderInfoRepository;
import com.example.library.common.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Date;
import java.time.LocalDate;

@SpringBootApplication
public class LibraryApplication implements CommandLineRunner {

    @Autowired
    private AcceptanceRecordRepository acceptanceRecordRepository;

    @Autowired
    private AcquisitionRecordRepository acquisitionRecordRepository;

    @Autowired
    private CirculationBookRepository circulationBookRepository;

    @Autowired
    private ReaderInfoRepository readerInfoRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // 插入一些采访问卷数据
        if (!acquisitionRecordRepository.existsById("001")) {
            acquisitionRecordRepository.save(new AcquisitionRecord("001", "Java编程", "作者1", "1234567890123", "出版社1", "图书"));
        }
        if (!acquisitionRecordRepository.existsById("002")) {
            acquisitionRecordRepository.save(new AcquisitionRecord("002", "Spring入门", "作者2", "1234567890124", "出版社2", "教程"));
        }

        // 插入一些验收清单数据（包含与流通库重合的 ISBN）
        if (!acceptanceRecordRepository.existsById("A001")) {
            acceptanceRecordRepository.save(new AcceptanceRecord("A001", "Java编程", "作者1", "1234567890123", "出版社1", "图书", "验收员甲", Date.valueOf("2025-03-01")));
        }
        if (!acceptanceRecordRepository.existsById("A002")) {
            acceptanceRecordRepository.save(new AcceptanceRecord("A002", "Spring入门", "作者2", "1234567890124", "出版社2", "教程", "验收员乙", Date.valueOf("2025-03-02")));
        }
        if (!acceptanceRecordRepository.existsById("A003")) {
            acceptanceRecordRepository.save(new AcceptanceRecord("A003", "算法导论", "作者3", "1234567890125", "出版社3", "教材", "验收员丙", Date.valueOf("2025-03-03")));
        }

        // 插入一些图书流通库数据
        if (!circulationBookRepository.existsById("1234567890123")) {
            circulationBookRepository.save(new CirculationBook(
                    "1234567890123", "B001", "Java编程", Date.valueOf("2026-01-12"), "作者1", "出版社1", "图书"));
        }
        if (!circulationBookRepository.existsById("1234567890124")) {
            circulationBookRepository.save(new CirculationBook(
                    "1234567890124", "B002", "Spring入门", Date.valueOf("2026-01-12"), "作者2", "出版社2", "教程"));
        }
        if (!circulationBookRepository.existsById("1234567890126")) {
            circulationBookRepository.save(new CirculationBook(
                    "1234567890126", "B003", "数据结构", Date.valueOf("2026-01-12"), "作者3", "出版社3", "教材"));
        }
        if (!circulationBookRepository.existsById("1234567890127")) {
            circulationBookRepository.save(new CirculationBook(
                    "1234567890127", "B004", "数据库系统", Date.valueOf("2026-01-12"), "作者4", "出版社4", "教材"));
        }
        if (!circulationBookRepository.existsById("1234567890128")) {
            circulationBookRepository.save(new CirculationBook(
                    "1234567890128", "B005", "计算机网络", Date.valueOf("2026-01-12"), "作者5", "出版社5", "教材"));
        }
        if (!circulationBookRepository.existsById("1234567890129")) {
            circulationBookRepository.save(new CirculationBook(
                    "1234567890129", "B006", "操作系统", Date.valueOf("2026-01-12"), "作者6", "出版社6", "教材"));
        }
        if (!circulationBookRepository.existsById("1234567890130")) {
            circulationBookRepository.save(new CirculationBook(
                    "1234567890130", "B007", "软件工程", Date.valueOf("2026-01-12"), "作者7", "出版社7", "图书"));
        }
        if (!circulationBookRepository.existsById("1234567890131")) {
            circulationBookRepository.save(new CirculationBook(
                    "1234567890131", "B008", "人工智能导论", Date.valueOf("2026-01-12"), "作者8", "出版社8", "图书"));
        }
        if (!circulationBookRepository.existsById("1234567890132")) {
            circulationBookRepository.save(new CirculationBook(
                    "1234567890132", "B009", "概率论与数理统计", Date.valueOf("2026-01-12"), "作者9", "出版社9", "教材"));
        }
        if (!circulationBookRepository.existsById("1234567890133")) {
            circulationBookRepository.save(new CirculationBook(
                    "1234567890133", "B010", "线性代数", Date.valueOf("2026-01-12"), "作者10", "出版社10", "教材"));
        }

        // 插入一些读者信息初始数据
        if (!readerInfoRepository.existsById("T9001")) {
            readerInfoRepository.save(new ReaderInfo("T9001", "TEACHER", "赵敏", "女", "13900000001", "工号", "T9001"));
        }
        if (!readerInfoRepository.existsById("T9002")) {
            readerInfoRepository.save(new ReaderInfo("T9002", "TEACHER", "陈浩", "男", "13900000002", "工号", "T9002"));
        }
        if (!readerInfoRepository.existsById("S2001")) {
            readerInfoRepository.save(new ReaderInfo("S2001", "STUDENT", "周然", "男", "13700000001", "借书证", "S2001"));
        }
        if (!readerInfoRepository.existsById("S2002")) {
            readerInfoRepository.save(new ReaderInfo("S2002", "STUDENT", "刘萱", "女", "13700000002", "借书证", "S2002"));
        }
        if (!readerInfoRepository.existsById("S2003")) {
            readerInfoRepository.save(new ReaderInfo("S2003", "STUDENT", "徐晴", "女", "13700000003", "借书证", "S2003"));
        }
        if (!readerInfoRepository.existsById("S2004")) {
            readerInfoRepository.save(new ReaderInfo("S2004", "STUDENT", "杜凯", "男", "13700000004", "借书证", "S2004"));
        }
        if (!readerInfoRepository.existsById("S2005")) {
            readerInfoRepository.save(new ReaderInfo("S2005", "STUDENT", "唐嘉", "女", "13700000005", "借书证", "S2005"));
        }

        if (!userAccountRepository.existsByAccountIdAndRole("T9001", "TEACHER")) {
            userAccountRepository.save(new UserAccount("T9001", "TEACHER", "123456"));
        }
        if (!userAccountRepository.existsByAccountIdAndRole("T9002", "TEACHER")) {
            userAccountRepository.save(new UserAccount("T9002", "TEACHER", "123456"));
        }
        if (!userAccountRepository.existsByAccountIdAndRole("S2001", "STUDENT")) {
            userAccountRepository.save(new UserAccount("S2001", "STUDENT", "123456"));
        }
        if (!userAccountRepository.existsByAccountIdAndRole("S2002", "STUDENT")) {
            userAccountRepository.save(new UserAccount("S2002", "STUDENT", "123456"));
        }
        if (!userAccountRepository.existsByAccountIdAndRole("S2003", "STUDENT")) {
            userAccountRepository.save(new UserAccount("S2003", "STUDENT", "123456"));
        }
        if (!userAccountRepository.existsByAccountIdAndRole("S2004", "STUDENT")) {
            userAccountRepository.save(new UserAccount("S2004", "STUDENT", "123456"));
        }
        if (!userAccountRepository.existsByAccountIdAndRole("S2005", "STUDENT")) {
            userAccountRepository.save(new UserAccount("S2005", "STUDENT", "123456"));
        }

        LocalDate borrowBaseDate = LocalDate.now().minusDays(14);
        saveBorrowRecordWithReturn("BR001", "1234567890123", "Java编程", "作者1", "S2001", "周然", "B001",
                borrowBaseDate, borrowBaseDate.plusDays(7));
        saveBorrowRecordWithReturn("BR002", "1234567890124", "Spring入门", "作者2", "S2002", "刘萱", "B002",
                borrowBaseDate.plusDays(1), borrowBaseDate.plusDays(8));
        saveBorrowRecordWithReturn("BR003", "1234567890126", "数据结构", "作者3", "S2003", "徐晴", "B003",
                borrowBaseDate.plusDays(1), borrowBaseDate.plusDays(9));
        saveBorrowRecordWithReturn("BR004", "1234567890127", "数据库系统", "作者4", "S2004", "杜凯", "B004",
                borrowBaseDate.plusDays(2), borrowBaseDate.plusDays(10));
        saveBorrowRecordWithReturn("BR005", "1234567890128", "计算机网络", "作者5", "S2005", "唐嘉", "B005",
                borrowBaseDate.plusDays(2), borrowBaseDate.plusDays(10));
        saveBorrowRecordWithReturn("BR006", "1234567890129", "操作系统", "作者6", "S2001", "周然", "B006",
                borrowBaseDate.plusDays(3), borrowBaseDate.plusDays(11));
        saveBorrowRecordWithReturn("BR007", "1234567890130", "软件工程", "作者7", "S2002", "刘萱", "B007",
                borrowBaseDate.plusDays(4), borrowBaseDate.plusDays(12));
        saveBorrowRecordWithReturn("BR008", "1234567890131", "人工智能导论", "作者8", "S2003", "徐晴", "B008",
                borrowBaseDate.plusDays(4), borrowBaseDate.plusDays(12));
        saveBorrowRecordWithReturn("BR009", "1234567890132", "概率论与数理统计", "作者9", "S2004", "杜凯", "B009",
                borrowBaseDate.plusDays(5), borrowBaseDate.plusDays(13));
        saveBorrowRecordWithReturn("BR010", "1234567890133", "线性代数", "作者10", "S2005", "唐嘉", "B010",
                borrowBaseDate.plusDays(5), borrowBaseDate.plusDays(13));
        saveBorrowRecordWithReturn("BR011", "1234567890123", "Java编程", "作者1", "S2002", "刘萱", "B001",
                borrowBaseDate.plusDays(6), borrowBaseDate.plusDays(14));
        saveBorrowRecordWithReturn("BR012", "1234567890124", "Spring入门", "作者2", "S2003", "徐晴", "B002",
                borrowBaseDate.plusDays(6), borrowBaseDate.plusDays(14));
        saveBorrowRecordWithReturn("BR013", "1234567890126", "数据结构", "作者3", "S2004", "杜凯", "B003",
                borrowBaseDate.plusDays(7), borrowBaseDate.plusDays(15));
        saveBorrowRecordWithReturn("BR014", "1234567890127", "数据库系统", "作者4", "S2005", "唐嘉", "B004",
                borrowBaseDate.plusDays(7), borrowBaseDate.plusDays(15));
        saveBorrowRecordWithReturn("BR015", "1234567890128", "计算机网络", "作者5", "S2001", "周然", "B005",
                borrowBaseDate.plusDays(8), borrowBaseDate.plusDays(16));
        saveBorrowRecordWithReturn("BR016", "1234567890123", "Java编程", "作者1", "S2003", "徐晴", "B001",
                LocalDate.of(2026, 1, 12), LocalDate.of(2026, 1, 20));
        saveBorrowRecordWithReturn("BR017", "1234567890124", "Spring入门", "作者2", "S2004", "杜凯", "B002",
                LocalDate.of(2026, 1, 12), LocalDate.of(2026, 1, 20));
        if (!borrowRecordRepository.existsById("BR018")) {
            borrowRecordRepository.save(new BorrowRecord("BR018", "1234567890132", "概率论与数理统计", "作者9", "借阅", "S2005", "唐嘉", "B009", Date.valueOf("2024-05-20")));
        }
        if (!borrowRecordRepository.existsById("BR019")) {
            borrowRecordRepository.save(new BorrowRecord("BR019", "1234567890130", "软件工程", "作者7", "借阅", "S2002", "刘萱", "B007", Date.valueOf("2024-11-03")));
        }
    }

    private void saveBorrowRecordWithReturn(String flowId, String isbn, String title, String author,
                                            String cardNo, String name, String bookId,
                                            LocalDate borrowDate, LocalDate returnDate) {
        BorrowRecord borrowRecord = new BorrowRecord(flowId, isbn, title, author, "借阅", cardNo, name, bookId,
                Date.valueOf(borrowDate));
        borrowRecord.setReturnDate(Date.valueOf(returnDate));
        borrowRecord.setOverdueDays(0);
        borrowRecord.setPenalty(0.0);
        if (!borrowRecordRepository.existsById(flowId)) {
            borrowRecordRepository.save(borrowRecord);
        }

        String returnFlowId = flowId + "R";
        if (!borrowRecordRepository.existsById(returnFlowId)) {
            BorrowRecord returnRecord = new BorrowRecord(returnFlowId, isbn, title, author, "还书", cardNo, name, bookId,
                    Date.valueOf(returnDate));
            returnRecord.setReturnDate(Date.valueOf(returnDate));
            returnRecord.setOverdueDays(0);
            returnRecord.setPenalty(0.0);
            borrowRecordRepository.save(returnRecord);
        }
    }
}
