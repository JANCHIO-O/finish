package com.example.library.catalog.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "damage_record")
public class DamageRecordEntity {

    @Id
    @Column(length = 8)
    private String damageId;

    @Column(length = 8, nullable = false)
    private String bookId;

    @Column(length = 13, nullable = false)
    private String isbn;

    @Column(length = 100, nullable = false)
    private String bookName;

    @Column(length = 100, nullable = false)
    private String damageReason;

    private LocalDate damageDate;

    @Column(length = 20)
    private String operator;

    @Column(length = 10, nullable = false)
    private String approveStatus;

    public DamageRecordEntity() {}

    public DamageRecordEntity(String damageId, String bookId, String isbn, String bookName,
                              String damageReason, LocalDate damageDate, String operator, String approveStatus) {
        this.damageId = damageId;
        this.bookId = bookId;
        this.isbn = isbn;
        this.bookName = bookName;
        this.damageReason = damageReason;
        this.damageDate = damageDate;
        this.operator = operator;
        this.approveStatus = approveStatus;
    }

    public String getDamageId() { return damageId; }
    public void setDamageId(String damageId) { this.damageId = damageId; }

    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getBookName() { return bookName; }
    public void setBookName(String bookName) { this.bookName = bookName; }

    public String getDamageReason() { return damageReason; }
    public void setDamageReason(String damageReason) { this.damageReason = damageReason; }

    public LocalDate getDamageDate() { return damageDate; }
    public void setDamageDate(LocalDate damageDate) { this.damageDate = damageDate; }

    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }

    public String getApproveStatus() { return approveStatus; }
    public void setApproveStatus(String approveStatus) { this.approveStatus = approveStatus; }
}
