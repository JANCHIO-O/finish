package com.example.library.catalog.service;

import com.example.library.catalog.entity.CatalogBookEntity;
import com.example.library.catalog.entity.DamageRecordEntity;
import com.example.library.catalog.entity.DamageRequestEntity;
import com.example.library.catalog.entity.TransferRecordEntity;
import com.example.library.catalog.repository.CatalogBookRepository;
import com.example.library.catalog.repository.DamageRecordRepository;
import com.example.library.catalog.repository.DamageRequestRepository;
import com.example.library.catalog.repository.TransferRecordRepository;
import com.example.library.catalog.dto.TransferBookDto;
import com.example.library.common.entity.AcceptanceRecord;
import com.example.library.common.entity.CirculationBook;
import com.example.library.common.repository.AcceptanceRecordRepository;
import com.example.library.common.repository.CirculationBookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
@Service
public class CatalogService {

    private final AcceptanceRecordRepository acceptanceRepo;
    private final CirculationBookRepository circulationRepo;
    private final CatalogBookRepository catalogRepo;
    private final TransferRecordRepository transferRepo;
    private final DamageRequestRepository damageRequestRepo;
    private final DamageRecordRepository damageRecordRepo;

    public CatalogService(AcceptanceRecordRepository acceptanceRepo,
                          CirculationBookRepository circulationRepo,
                          CatalogBookRepository catalogRepo,
                          TransferRecordRepository transferRepo,
                          DamageRequestRepository damageRequestRepo,
                          DamageRecordRepository damageRecordRepo) {
        this.acceptanceRepo = acceptanceRepo;
        this.circulationRepo = circulationRepo;
        this.catalogRepo = catalogRepo;
        this.transferRepo = transferRepo;
        this.damageRequestRepo = damageRequestRepo;
        this.damageRecordRepo = damageRecordRepo;
    }

    /** 4.1 待编书目查看：来自验收清单 */
    public List<AcceptanceRecord> listPendingFromAcceptance() {
        return acceptanceRepo.findAll();
    }

    /** 4.1 手动添加到验收清单（现在就写 acceptance_record） */
    @Transactional
    public void addAcceptanceRecordManually(String title, String isbn, String publisher,
                                            String docType, String checker, Date publishDate) {

        String checkId = generateCheckId10();
        AcceptanceRecord record = new AcceptanceRecord(
                checkId, title, isbn, publisher, docType, checker, publishDate
        );
        acceptanceRepo.save(record);
    }

    /** 查流通库是否存在（按 ISBN） */
    public boolean existsInCirculationByIsbn(String isbn) {
        return circulationRepo.existsById(isbn);
    }

    /** 4.2 直接编目（自动查重）：
     * - 若流通库已存在：从验收清单删掉该 ISBN，返回 DUPLICATE
     * - 若不存在：写入编目清单，并从验收清单删掉，返回 OK
     */
    @Transactional
    public String directCatalogAndRemovePending(String isbn, String bookName, String cataloger) {

        // 如果流通库已存在：直接从验收清单移除
        if (existsInCirculationByIsbn(isbn)) {
            acceptanceRepo.deleteAll(acceptanceRepo.findByIsbn(isbn));
            return "DUPLICATE";
        }

        // 写入编目清单
        String bookId = generateBookId8();
        CatalogBookEntity entity = new CatalogBookEntity(
                bookId, isbn, bookName, cataloger, LocalDate.now()
        );
        catalogRepo.save(entity);

        // 从验收清单移除
        acceptanceRepo.deleteAll(acceptanceRepo.findByIsbn(isbn));

        return "OK";
    }

    /** 编目清单查询 */
    public List<CatalogBookEntity> listCatalogBatch() {
        return catalogRepo.findAll();
    }

    /** 4.4 移送：生成 transfer_record + 写入 circulation_book + 清空 catalog_book */
    @Transactional
    public void transferBatch(String movePos) {
        List<CatalogBookEntity> batch = catalogRepo.findAll();

        // “最近一次移送”清单：先清空旧的
        transferRepo.deleteAll();
        long transferSequence = 1L;

        for (CatalogBookEntity b : batch) {
            // 写移送清单
            TransferRecordEntity tr = new TransferRecordEntity(
                    generateTransferId8(transferSequence++),
                    b.getIsbn(),
                    b.getBookName(),
                    b.getBookId(),
                    movePos,
                    LocalDate.now()
            );
            tr.setIsbn(b.getIsbn());
            transferRepo.save(tr);

            // 写入流通库（永久）
            CirculationBook cb = new CirculationBook();
            cb.setIsbn(b.getIsbn());        // 通常是主键
            cb.setBookId(b.getBookId());
            cb.setTitle(b.getBookName());
            cb.setCatalogDate(LocalDate.now().toString());
            cb.setAuthor("未知");
            cb.setPublisher("未知");
            cb.setDocType("未知");
            circulationRepo.save(cb);
        }

        // 清空本批次编目清单
        catalogRepo.deleteAll();
    }

    /** 查询移送清单 */
    public List<TransferRecordEntity> listLatestTransfer() {
        return transferRepo.findAll();
    }

    /** 4.6.1 报损申请：展示流通库 */
    public List<CirculationBook> listCirculationBooks() {
        return circulationRepo.findAll();
    }

    /** 4.6.1 报损申请提交 */
    @Transactional
    public void submitDamageRequest(String isbn, String damageReason, String applicant, LocalDate damageDate) {
        CirculationBook book = circulationRepo.findById(isbn)
                .orElseThrow(() -> new IllegalArgumentException("未找到流通库图书"));
        String requestId = generateDamageRequestId8();
        DamageRequestEntity request = new DamageRequestEntity(
                requestId,
                book.getBookId(),
                book.getIsbn(),
                book.getTitle(),
                damageReason,
                damageDate,
                applicant
        );
        damageRequestRepo.save(request);
    }

    /** 4.6.2 报损申请列表 */
    public List<DamageRequestEntity> listDamageRequests() {
        return damageRequestRepo.findAll();
    }

    /** 4.6.2 审核通过：写入报损记录并移除流通库 */
    @Transactional
    public void approveDamageRequest(String requestId, String operator) {
        DamageRequestEntity request = damageRequestRepo.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("未找到报损申请"));
        String damageId = generateDamageRecordId8();
        DamageRecordEntity record = new DamageRecordEntity(
                damageId,
                request.getBookId(),
                request.getIsbn(),
                request.getBookName(),
                request.getDamageReason(),
                request.getDamageDate(),
                operator,
                "通过"
        );
        damageRecordRepo.save(record);
        circulationRepo.deleteById(request.getIsbn());
        damageRequestRepo.deleteById(requestId);
    }

    /** 4.6.2 审核拒绝：移除申请 */
    @Transactional
    public void rejectDamageRequest(String requestId) {
        damageRequestRepo.deleteById(requestId);
    }

    /** 4.6.3 报损记录 */
    public List<DamageRecordEntity> listDamageRecords() {
        return damageRecordRepo.findAll();
    }
    // ===== ID 生成 =====

    private String generateBookId8() {
        long count = catalogRepo.count() + 1;
        return String.format("B%07d", count); // 8位
    }

    private String generateTransferId8(long sequence) {
        return String.format("T%07d", sequence); // 8位
    }

    private String generateCheckId10() {
        long count = acceptanceRepo.count() + 1;
        return String.format("C%09d", count); // 10位：C + 9位数字
    }

    private String generateDamageRequestId8() {
        long count = damageRequestRepo.count() + 1;
        return String.format("R%07d", count); // 8位
    }

    private String generateDamageRecordId8() {
        long count = damageRecordRepo.count() + 1;
        return String.format("D%07d", count); // 8位
    }
}
