package com.example.library.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Date;

@SuppressWarnings("ALL")
@Entity
@Table(name = "circulation_book")
public class CirculationBook {

    @Id
    private String isbn;

    private String bookId;
    private String title;
    private Date catalogDate;
    private String author;
    private String publisher;
    private String docType;
    private Integer status = 1;

    // 无参构造函数
    public CirculationBook() {
    }

    // 构造函数
    public CirculationBook(String isbn, String bookId, String title, Date catalogDate) {
        this.isbn = isbn;
        this.bookId = bookId;
        this.title = title;
        this.catalogDate = catalogDate;
    }

    public CirculationBook(String isbn, String bookId, String title, Date catalogDate,
                           String author, String publisher, String docType) {
        this.isbn = isbn;
        this.bookId = bookId;
        this.title = title;
        this.catalogDate = catalogDate;
        this.author = author;
        this.publisher = publisher;
        this.docType = docType;
    }

    // Getters 和 Setters
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCatalogDate() {
        return catalogDate;
    }

    public void setCatalogDate(Date catalogDate) {
        this.catalogDate = catalogDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
