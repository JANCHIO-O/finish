package com.example.library.acquisition.service;

import com.example.library.acquisition.entity.AcquisitionOrder;
import com.example.library.acquisition.entity.AcquisitionReturnRecord;
import com.example.library.acquisition.repository.AcquisitionOrderRepository;
import com.example.library.acquisition.repository.AcquisitionReturnRecordRepository;
import com.example.library.common.entity.AcceptanceRecord;
import com.example.library.common.entity.AcquisitionRecord;
import com.example.library.common.repository.AcceptanceRecordRepository;
import com.example.library.common.repository.AcquisitionRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class AcquisitionService {

    private static final String STATUS_PENDING = "待验收";
    private static final String STATUS_SUCCESS = "验收成功";
    private static final String STATUS_FAIL = "验收失败";

    private final AcquisitionRecordRepository acquisitionRecordRepository;
    private final AcquisitionOrderRepository acquisitionOrderRepository;
    private final AcceptanceRecordRepository acceptanceRecordRepository;
    private final AcquisitionReturnRecordRepository returnRecordRepository;

    public AcquisitionService(AcquisitionRecordRepository acquisitionRecordRepository,
                              AcquisitionOrderRepository acquisitionOrderRepository,
                              AcceptanceRecordRepository acceptanceRecordRepository,
                              AcquisitionReturnRecordRepository returnRecordRepository) {
        this.acquisitionRecordRepository = acquisitionRecordRepository;
        this.acquisitionOrderRepository = acquisitionOrderRepository;
        this.acceptanceRecordRepository = acceptanceRecordRepository;
        this.returnRecordRepository = returnRecordRepository;
    }

    public List<AcquisitionRecord> listAcquisitions() {
        return acquisitionRecordRepository.findAll();
    }

    public List<AcquisitionOrder> listOrders() {
        return acquisitionOrderRepository.findAll();
    }

    public List<AcceptanceRecord> listAcceptanceRecords() {
        return acceptanceRecordRepository.findAll();
    }

    public List<AcquisitionReturnRecord> listReturnRecords() {
        return returnRecordRepository.findAll();
    }

    public void addAcquisitionRecord(String title, String author, String isbn, String publisher, String docType) {
        validateIsbn(isbn);
        String purchasedId = generateAcquisitionId();
        AcquisitionRecord record = new AcquisitionRecord(purchasedId, title, author, isbn, publisher, docType);
        acquisitionRecordRepository.save(record);
    }

    public void addOrder(String title, String author, String isbn, String publisher, String docType, String orderer,
                         Integer quantity, Double unitPrice) {
        validateIsbn(isbn);
        String orderId = generateOrderId();
        Date date = Date.valueOf(LocalDate.now());
        AcquisitionOrder order = new AcquisitionOrder(orderId, date, orderer, title, author, isbn, publisher, docType, unitPrice,
                quantity, STATUS_PENDING);
        acquisitionOrderRepository.save(order);
    }

    public AcquisitionOrder getOrder(String orderId) {
        return acquisitionOrderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("未找到订单信息"));
    }

    public void updateOrder(String orderId, String orderer, Integer quantity, Double unitPrice) {
        AcquisitionOrder order = getOrder(orderId);
        if (!STATUS_PENDING.equals(order.getStatus())) {
            throw new IllegalArgumentException("已验收订单无法修改");
        }
        order.setOrderer(orderer);
        order.setOrderDate(Date.valueOf(LocalDate.now()));
        order.setQuantity(quantity);
        order.setUnitPrice(unitPrice);
        acquisitionOrderRepository.save(order);
    }

    @Transactional
    public void addAcceptanceRecord(String orderId, String checker, Integer receivedQuantity, String status) {
        AcquisitionOrder order = getOrder(orderId);
        validateIsbn(order.getIsbn());
        if (receivedQuantity > order.getQuantity()) {
            throw new IllegalArgumentException("到馆数量不能大于订购数量");
        }
        if (STATUS_SUCCESS.equals(status) && order.getQuantity().equals(receivedQuantity)) {
            String checkId = generateAcceptanceId();
            Date date = Date.valueOf(LocalDate.now());
            AcceptanceRecord record = new AcceptanceRecord(checkId, order.getTitle(), order.getAuthor(), order.getIsbn(),
                    order.getPublisher(), order.getDocType(), checker, date);
            acceptanceRecordRepository.save(record);
            order.setStatus(STATUS_SUCCESS);
            acquisitionOrderRepository.save(order);
        } else {
            throw new IllegalArgumentException("验收错误！");
        }
    }

    @Transactional
    public void addReturnRecord(String orderId, String orderDate, String orderer, String title, String author, String isbn, String publisher,
                                String docType, Double unitPrice, String currency, Integer orderQuantity, String orderStatus,
                                Integer returnQuantity, String returner, String returnReason) {
        validateIsbn(isbn);
        String returnId = generateReturnId();
        Date date = Date.valueOf(orderDate);
        AcquisitionReturnRecord record = new AcquisitionReturnRecord(returnId, date, orderer, title, author, isbn, publisher,
                docType, unitPrice, currency, orderQuantity, orderStatus, returnQuantity, returner, returnReason);
        returnRecordRepository.save(record);
        acquisitionOrderRepository.deleteById(orderId);
    }

    private String generateAcquisitionId() {
        long count = acquisitionRecordRepository.count() + 1;
        return String.format("AR%06d", count);
    }

    private String generateOrderId() {
        long count = acquisitionOrderRepository.count() + 1;
        return String.format("AO%06d", count);
    }

    private String generateAcceptanceId() {
        long count = acceptanceRecordRepository.count() + 1;
        return String.format("AC%06d", count);
    }

    private String generateReturnId() {
        long count = returnRecordRepository.count() + 1;
        return String.format("RT%06d", count);
    }

    private void validateIsbn(String isbn) {
        if (isbn == null || !isbn.matches("\\d{13}")) {
            throw new IllegalArgumentException("ISBN必须是13位数字");
        }
    }
}
