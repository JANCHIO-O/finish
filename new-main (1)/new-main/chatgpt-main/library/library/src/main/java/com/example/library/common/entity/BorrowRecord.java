package com.example.library.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@SuppressWarnings("ALL")
@Entity
@Table(name = "borrow_record")
public class BorrowRecord {

    @Id
    private String flowId;

    private String isbn;
    private String title;
    private String author;
    private String eventType;
    private String cardNo;
    private String name;
    private String bookId;
    private java.sql.Date flowDate;

    // 无参构造函数
    public BorrowRecord() {
    }

    // 构造函数
    public BorrowRecord(String flowId, String isbn, String title, String author, String eventType, String cardNo, String name, String bookId, java.sql.Date flowDate) {
        this.flowId = flowId;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.eventType = eventType;
        this.cardNo = cardNo;
        this.name = name;
        this.bookId = bookId;
        this.flowDate = flowDate;
    }

    // Getters 和 Setters
    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public java.sql.Date getFlowDate() {
        return flowDate;
    }

    public void setFlowDate(java.sql.Date flowDate) {
        this.flowDate = flowDate;
    }
}
