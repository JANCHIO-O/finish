package com.example.library.circulation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "circulation_book", schema = "PUBLIC")
public class CirculationBookEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "isbn", nullable = false, unique = true, length = 13)
    private String isbn;

    @Column(name = "book_id", nullable = false, length = 8)
    private String bookId;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "catalog_date", nullable = false)
    private Date catalogDate;

    // ========== 修复：图书默认状态为【可借】 ==========
    @Column(name = "status", nullable = false, columnDefinition = "INT DEFAULT 1")
    private Integer status = 1;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}