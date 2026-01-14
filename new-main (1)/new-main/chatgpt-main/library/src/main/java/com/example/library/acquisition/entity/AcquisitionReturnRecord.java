package com.example.library.acquisition.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "acquisition_return")
public class AcquisitionReturnRecord {

    @Id
    private String returnId;

    private Date orderDate;
    private String orderer;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private String docType;
    private Double unitPrice;
    private String currency;
    private Integer orderQuantity;
    private String orderStatus;
    private Integer returnQuantity;
    private String returner;
    private String returnReason;

    public AcquisitionReturnRecord() {
    }

    public AcquisitionReturnRecord(String returnId, Date orderDate, String orderer, String title, String author, String isbn,
                                   String publisher, String docType, Double unitPrice, String currency, Integer orderQuantity,
                                   String orderStatus, Integer returnQuantity, String returner, String returnReason) {
        this.returnId = returnId;
        this.orderDate = orderDate;
        this.orderer = orderer;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.docType = docType;
        this.unitPrice = unitPrice;
        this.currency = currency;
        this.orderQuantity = orderQuantity;
        this.orderStatus = orderStatus;
        this.returnQuantity = returnQuantity;
        this.returner = returner;
        this.returnReason = returnReason;
    }

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderer() {
        return orderer;
    }

    public void setOrderer(String orderer) {
        this.orderer = orderer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getReturnQuantity() {
        return returnQuantity;
    }

    public void setReturnQuantity(Integer returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    public String getReturner() {
        return returner;
    }

    public void setReturner(String returner) {
        this.returner = returner;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }
}
