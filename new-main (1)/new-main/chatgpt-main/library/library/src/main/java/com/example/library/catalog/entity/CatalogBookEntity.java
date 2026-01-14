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

    @Column(length = 20)
    private String cataloger;

    private LocalDate catalogDate;

    public CatalogBookEntity() {}

    public CatalogBookEntity(String bookId, String isbn, String bookName, String cataloger, LocalDate catalogDate) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.bookName = bookName;
        this.cataloger = cataloger;
        this.catalogDate = catalogDate;
    }

    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getBookName() { return bookName; }
    public void setBookName(String bookName) { this.bookName = bookName; }

    public String getCataloger() { return cataloger; }
    public void setCataloger(String cataloger) { this.cataloger = cataloger; }

    public LocalDate getCatalogDate() { return catalogDate; }
    public void setCatalogDate(LocalDate catalogDate) { this.catalogDate = catalogDate; }
}
