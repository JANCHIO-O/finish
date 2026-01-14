package com.example.library.catalog.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "damage_request")
public class DamageRequestEntity {

    @Id
    @Column(length = 8)
    private String requestId;

    @Column(length = 8, nullable = false)
    private String bookId;

    @Column(length = 13, nullable = false)
    private String isbn;

    @Column(length = 100, nullable = false)
    private String bookName;

    @Column(length = 100, nullable = false)
    private String damageReason;

    private LocalDate damageDate;

    @Column(length = 20, nullable = false)
    private String applicant;

    public DamageRequestEntity() {
    }

    public DamageRequestEntity(String requestId, String bookId, String isbn, String bookName,
                               String damageReason, LocalDate damageDate, String applicant) {
        this.requestId = requestId;
        this.bookId = bookId;
        this.isbn = isbn;
        this.bookName = bookName;
        this.damageReason = damageReason;
        this.damageDate = damageDate;
        this.applicant = applicant;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getDamageReason() {
        return damageReason;
    }

    public void setDamageReason(String damageReason) {
        this.damageReason = damageReason;
    }

    public LocalDate getDamageDate() {
        return damageDate;
    }

    public void setDamageDate(LocalDate damageDate) {
        this.damageDate = damageDate;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }
}
