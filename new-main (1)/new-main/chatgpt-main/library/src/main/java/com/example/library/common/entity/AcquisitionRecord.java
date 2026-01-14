package com.example.library.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "acquisition_record")
public class AcquisitionRecord {

    @Id
    private String purchasedId;

    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private String docType;

    // 无参构造函数
    public AcquisitionRecord() {
    }

    // 构造函数
    public AcquisitionRecord(String purchasedId, String title, String author, String isbn, String publisher, String docType) {
        this.purchasedId = purchasedId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.docType = docType;
    }

    // Getters 和 Setters
    public String getPurchasedId() {
        return purchasedId;
    }

    public void setPurchasedId(String purchasedId) {
        this.purchasedId = purchasedId;
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
}

