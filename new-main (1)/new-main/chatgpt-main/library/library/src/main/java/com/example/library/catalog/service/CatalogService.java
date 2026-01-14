package com.example.library.catalog.service;

import com.example.library.catalog.entity.CatalogBookEntity;
import com.example.library.catalog.entity.TransferRecordEntity;
import com.example.library.catalog.repository.CatalogBookRepository;
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

    public CatalogService(AcceptanceRecordRepository acceptanceRepo,
                          CirculationBookRepository circulationRepo,
                          CatalogBookRepository catalogRepo,
                          TransferRecordRepository transferRepo) {
        this.acceptanceRepo = acceptanceRepo;
        this.circulationRepo = circulationRepo;
        this.catalogRepo = catalogRepo;
        this.transferRepo = transferRepo;
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
            circulationRepo.save(cb);
        }

        // 清空本批次编目清单
        catalogRepo.deleteAll();
    }

    /** 查询移送清单 */
    public List<TransferRecordEntity> listLatestTransfer() {
        return transferRepo.findAll();
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
}
