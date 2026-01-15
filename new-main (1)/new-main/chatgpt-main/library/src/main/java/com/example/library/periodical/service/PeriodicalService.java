package com.example.library.periodical.service;

import com.example.library.periodical.entity.PeriodicalAcceptanceRecord;
import com.example.library.periodical.entity.PeriodicalBindingRecord;
import com.example.library.periodical.entity.PeriodicalCatalogEntry;
import com.example.library.periodical.entity.PeriodicalOrder;
import com.example.library.periodical.entity.PeriodicalVisitRecord;
import com.example.library.periodical.repository.PeriodicalAcceptanceRecordRepository;
import com.example.library.periodical.repository.PeriodicalBindingRecordRepository;
import com.example.library.periodical.repository.PeriodicalCatalogEntryRepository;
import com.example.library.periodical.repository.PeriodicalOrderRepository;
import com.example.library.periodical.repository.PeriodicalVisitRecordRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PeriodicalService {

    private final PeriodicalVisitRecordRepository visitRecordRepository;
    private final PeriodicalOrderRepository orderRepository;
    private final PeriodicalAcceptanceRecordRepository acceptanceRecordRepository;
    private final PeriodicalBindingRecordRepository bindingRecordRepository;
    private final PeriodicalCatalogEntryRepository catalogEntryRepository;

    public PeriodicalService(PeriodicalVisitRecordRepository visitRecordRepository,
                             PeriodicalOrderRepository orderRepository,
                             PeriodicalAcceptanceRecordRepository acceptanceRecordRepository,
                             PeriodicalBindingRecordRepository bindingRecordRepository,
                             PeriodicalCatalogEntryRepository catalogEntryRepository) {
        this.visitRecordRepository = visitRecordRepository;
        this.orderRepository = orderRepository;
        this.acceptanceRecordRepository = acceptanceRecordRepository;
        this.bindingRecordRepository = bindingRecordRepository;
        this.catalogEntryRepository = catalogEntryRepository;
    }

    @PostConstruct
    public void seedVisitRecords() {
        if (visitRecordRepository.count() > 0) {
            return;
        }
        visitRecordRepository.save(new PeriodicalVisitRecord(generateVisitId(), "现代图书馆", "12345678", "教育出版社", "张晨", Date.valueOf("2024-09-01"), "教学参考"));
        visitRecordRepository.save(new PeriodicalVisitRecord(generateVisitId(), "科技前沿", "87654321", "科技出版社", "李雯", Date.valueOf("2024-09-10"), "学科建设"));
        visitRecordRepository.save(new PeriodicalVisitRecord(generateVisitId(), "医学动态", "11223344", "健康出版社", "王磊", Date.valueOf("2024-09-15"), "科研支持"));
    }

    public List<PeriodicalVisitRecord> listVisits() {
        return visitRecordRepository.findAll();
    }

    public List<PeriodicalOrder> listOrders() {
        return orderRepository.findAll();
    }

    public List<PeriodicalAcceptanceRecord> listAcceptanceRecords() {
        return acceptanceRecordRepository.findAll();
    }

    public List<PeriodicalBindingRecord> listBindingRecords() {
        return bindingRecordRepository.findAll();
    }

    public List<PeriodicalCatalogEntry> listCatalogEntries() {
        return catalogEntryRepository.findAll();
    }

    public Set<String> listBoundIssns() {
        return bindingRecordRepository.findAll().stream()
                .map(PeriodicalBindingRecord::getIssn)
                .collect(Collectors.toSet());
    }

    public void addVisitRecord(String title, String issn, String publisher, String recommender, String reason) {
        String visitId = generateVisitId();
        validateIssn(issn);
        Date date = Date.valueOf(LocalDate.now());
        PeriodicalVisitRecord record = new PeriodicalVisitRecord(visitId, title, issn, publisher, recommender, date, reason);
        visitRecordRepository.save(record);
    }

    public void addOrder(String title, String issn, String publisher, Integer quantity, Double unitPrice) {
        String orderId = generateOrderId();
        validateIssn(issn);
        Date date = Date.valueOf(LocalDate.now());
        PeriodicalOrder order = new PeriodicalOrder(orderId, title, issn, publisher, quantity, unitPrice, date, "已下单");
        orderRepository.save(order);
    }

    @Transactional
    public void addAcceptanceRecord(String orderId, String title, String issn, String publisher, Integer receivedQuantity, String checker, String status) {
        validateIssn(issn);
        PeriodicalOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("未找到订购记录"));
        if (!Objects.equals(order.getStatus(), "已下单")) {
            throw new IllegalArgumentException("已验收订单无法再次验收");
        }
        if (!Objects.equals(order.getIssn(), issn)) {
            throw new IllegalArgumentException("验收信息与订购记录不一致");
        }
        if (receivedQuantity > order.getQuantity()) {
            throw new IllegalArgumentException("到馆数量不能大于订购数量");
        }
        String acceptanceId = generateAcceptanceId();
        Date date = Date.valueOf(LocalDate.now());
        PeriodicalAcceptanceRecord record = new PeriodicalAcceptanceRecord(acceptanceId, title, issn, publisher, receivedQuantity, checker, date, status);
        if ("验收成功".equals(status)) {
            acceptanceRecordRepository.save(record);
            order.setStatus("验收成功");
            orderRepository.save(order);
            visitRecordRepository.deleteByIssn(issn);
        } else if ("验收失败".equals(status)) {
            orderRepository.delete(order);
        } else {
            throw new IllegalArgumentException("验收状态无效");
        }
    }

    @Transactional
    public void addBindingRecordAndCatalog(String title, String issn, String publisher, String binder, String shelfLocation) {
        String bindId = generateBindId();
        validateIssn(issn);
        if (bindingRecordRepository.existsByIssn(issn)) {
            throw new IllegalArgumentException("已装订期刊无法再次装订");
        }
        Date date = Date.valueOf(LocalDate.now());
        PeriodicalBindingRecord record = new PeriodicalBindingRecord(bindId, title, issn, publisher, binder, date, shelfLocation);
        bindingRecordRepository.save(record);

        String catalogId = generateCatalogId();
        PeriodicalCatalogEntry entry = new PeriodicalCatalogEntry(catalogId, title, issn, publisher, date, shelfLocation);
        catalogEntryRepository.save(entry);
    }

    private String generateVisitId() {
        long count = visitRecordRepository.count() + 1;
        return String.format("PV%06d", count);
    }

    private String generateOrderId() {
        long count = orderRepository.count() + 1;
        return String.format("PO%06d", count);
    }

    private String generateAcceptanceId() {
        long count = acceptanceRecordRepository.count() + 1;
        return String.format("PA%06d", count);
    }

    private String generateBindId() {
        long count = bindingRecordRepository.count() + 1;
        return String.format("PB%06d", count);
    }

    private String generateCatalogId() {
        long count = catalogEntryRepository.count() + 1;
        return String.format("PC%06d", count);
    }

    private void validateIssn(String issn) {
        if (issn == null || !issn.matches("\\d{8}")) {
            throw new IllegalArgumentException("ISSN必须是8位数字");
        }
    }
}
