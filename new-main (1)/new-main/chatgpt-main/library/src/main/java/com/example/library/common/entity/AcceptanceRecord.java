package com.example.library.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "\"acceptance_record\"")
public class AcceptanceRecord {

    @Id
    private String checkId;

    private String title;
    private String isbn;
    private String publisher;
    private String docType;
    private String checker;
    private Date publishDate;

    // 无参构造函数
    public AcceptanceRecord() {
    }

    // 构造函数
    public AcceptanceRecord(String checkId, String title, String isbn, String publisher, String docType, String checker, Date publishDate) {
        this.checkId = checkId;
        this.title = title;
        this.isbn = isbn;
        this.publisher = publisher;
        this.docType = docType;
        this.checker = checker;
        this.publishDate = publishDate;
    }

    // Getters 和 Setters
    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}
