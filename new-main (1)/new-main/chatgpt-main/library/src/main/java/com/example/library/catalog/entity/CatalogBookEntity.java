package com.example.library.catalog.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "catalog_book")
public class CatalogBookEntity {

    @Id
    @Column(length = 8)
    private String bookId;

    @Column(length = 13, nullable = false)
    private String isbn;

    @Column(length = 100, nullable = false)
    private String bookName;

    @Column(length = 100)
    private String author;

    @Column(length = 100)
    private String publisher;

    @Column(length = 50)
    private String docType;

    @Column(length = 20)
    private String cataloger;

    private LocalDate catalogDate;

    public CatalogBookEntity() {}

    public CatalogBookEntity(String bookId, String isbn, String bookName, String author, String publisher, String docType,
                             String cataloger, LocalDate catalogDate) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.docType = docType;
        this.cataloger = cataloger;
        this.catalogDate = catalogDate;
    }

    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getBookName() { return bookName; }
    public void setBookName(String bookName) { this.bookName = bookName; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public String getDocType() { return docType; }
    public void setDocType(String docType) { this.docType = docType; }

    public String getCataloger() { return cataloger; }
    public void setCataloger(String cataloger) { this.cataloger = cataloger; }

    public LocalDate getCatalogDate() { return catalogDate; }
    public void setCatalogDate(LocalDate catalogDate) { this.catalogDate = catalogDate; }
}
