package com.example.library.circulation.dto;
import java.util.Date;
public class CirculationBookDto {
    private String bookId;
    private String isbn;
    private String title;
    private Date catalogDate;
    private Integer status;
    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Date getCatalogDate() { return catalogDate; }
    public void setCatalogDate(Date catalogDate) { this.catalogDate = catalogDate; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}