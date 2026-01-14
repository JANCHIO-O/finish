package com.example.library.circulation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "reservation")
public class ReservationEntity {
    @Id
    @Column(name = "reserve_id", length = 13, nullable = false) // 核心修改：12 → 13，适配生成的编号长度
    private String reserveId;

    @Column(name = "book_id", length = 8, nullable = false)
    private String bookId;

    @Column(name = "card_no", length = 10, nullable = false)
    private String cardNo;

    @Column(name = "reader_name", length = 20, nullable = false)
    private String readerName;

    @Column(name = "reserve_time", nullable = false)
    private Date reserveTime;

    @Column(name = "status", length = 4, nullable = false) // 顺带修改：2→4，防止"有效/失效"长度不够
    private String status;

    // 全参GETTER & SETTER 不变
    public String getReserveId() { return reserveId; }
    public void setReserveId(String reserveId) { this.reserveId = reserveId; }
    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }
    public String getCardNo() { return cardNo; }
    public void setCardNo(String cardNo) { this.cardNo = cardNo; }
    public String getReaderName() { return readerName; }
    public void setReaderName(String readerName) { this.readerName = readerName; }
    public Date getReserveTime() { return reserveTime; }
    public void setReserveTime(Date reserveTime) { this.reserveTime = reserveTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}