package com.example.library.catalog.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transfer_record")
public class TransferRecordEntity {

    @Id
    @Column(length = 8)
    private String transferId;

    @Column(length = 13, nullable = false)
    private String isbn;

    @Column(length = 100, nullable = false)
    private String bookName;


    @Column(length = 8, nullable = false)
    private String bookId;

    @Column(length = 20, nullable = false)
    private String movePos;

    private LocalDate transferDate;

    public TransferRecordEntity() {}

    public TransferRecordEntity(String transferId, String isbn, String bookName, String bookId, String movePos, LocalDate transferDate) {
        this.transferId = transferId;
        this.isbn = isbn;
        this.bookName = bookName;
        this.bookId = bookId;
        this.movePos = movePos;
        this.transferDate = transferDate;
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


    public String getTransferId() {
        return transferId;
    }
    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public String getBookId() {
        return bookId;
    }
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getMovePos() {
        return movePos;
    }
    public void setMovePos(String movePos) {
        this.movePos = movePos;
    }

    public LocalDate getTransferDate() {
        return transferDate;
    }
    public void setTransferDate(LocalDate transferDate) {
        this.transferDate = transferDate;
    }

}
