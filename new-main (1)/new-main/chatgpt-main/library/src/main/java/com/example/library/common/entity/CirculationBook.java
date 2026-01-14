package com.example.library.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@SuppressWarnings("ALL")
@Entity
@Table(name = "circulation_book")
public class CirculationBook {

    @Id
    private String isbn;

    private String bookId;
    private String title;
    private String catalogDate;
    private String author;
    private String publisher;
    private String docType;

    // 无参构造函数
    public CirculationBook() {
    }

    // 构造函数
    public CirculationBook(String isbn, String bookId, String title, String catalogDate) {
        this.isbn = isbn;
        this.bookId = bookId;
        this.title = title;
        this.catalogDate = catalogDate;
    }

    public CirculationBook(String isbn, String bookId, String title, String catalogDate,
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

    public String getCatalogDate() {
        return catalogDate;
    }

    public void setCatalogDate(String catalogDate) {
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
}
