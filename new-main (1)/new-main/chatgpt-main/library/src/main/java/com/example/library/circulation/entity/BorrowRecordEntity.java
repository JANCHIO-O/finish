package com.example.library.circulation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;
@Entity
@Table(name = "borrow_record")
public class BorrowRecordEntity {
    @Id
    @Column(name = "FlowId", length = 10, nullable = false)
    private String flowId;
    @Column(name = "ISBN", length = 13)
    private String isbn;
    @Column(name = "Title", length = 100, nullable = false)
    private String title;
    @Column(name = "Author", length = 50, nullable = false)
    private String author;
    @Column(name = "EventType", length = 10, nullable = false)
    private String eventType;
    @Column(name = "CardNo", length = 10, nullable = false)
    private String cardNo;
    @Column(name = "Name", length = 20, nullable = false)
    private String name;
    @Column(name = "BookId", length = 8, nullable = false)
    private String bookId;
    @Column(name = "FlowDate", nullable = false)
    private Date flowDate;
    @Column(name = "ReturnDate")
    private Date returnDate;
    @Column(name = "OverdueDays")
    private Integer overdueDays;
    @Column(name = "Penalty")
    private Double penalty;
    public String getFlowId() { return flowId; }
    public void setFlowId(String flowId) { this.flowId = flowId; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public String getCardNo() { return cardNo; }
    public void setCardNo(String cardNo) { this.cardNo = cardNo; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }
    public Date getFlowDate() { return flowDate; }
    public void setFlowDate(Date flowDate) { this.flowDate = flowDate; }
    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
    public Integer getOverdueDays() { return overdueDays; }
    public void setOverdueDays(Integer overdueDays) { this.overdueDays = overdueDays; }
    public Double getPenalty() { return penalty; }
    public void setPenalty(Double penalty) { this.penalty = penalty; }
}