package com.example.library.circulation.dto;

import java.util.Date;

/**
 * 借阅记录数据传输对象
 * 对应 borrow_record 表，超期查询/借阅记录查询使用
 */
public class BorrowRecordDto {
    private String flowId;      //流通编号(主键,10位)
    private String bookId;      //图书编号
    private String isbn;        //ISBN号
    private String title;       //图书名称
    private String author;      //作者
    private String eventType;   //借阅/还书
    private String cardNo;      //借书证号
    private String name;        //读者姓名
    private Date flowDate;      //流通日期
    private Date returnDate;    //归还日期
    private Integer overdueDays;//超期天数
    private Double penalty;     //罚金

    // 全参GETTER & SETTER
    public String getFlowId() { return flowId; }
    public void setFlowId(String flowId) { this.flowId = flowId; }
    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }
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
    public Date getFlowDate() { return flowDate; }
    public void setFlowDate(Date flowDate) { this.flowDate = flowDate; }
    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
    public Integer getOverdueDays() { return overdueDays; }
    public void setOverdueDays(Integer overdueDays) { this.overdueDays = overdueDays; }
    public Double getPenalty() { return penalty; }
    public void setPenalty(Double penalty) { this.penalty = penalty; }
}