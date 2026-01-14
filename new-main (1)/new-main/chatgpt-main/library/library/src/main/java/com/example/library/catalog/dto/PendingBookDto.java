package com.example.library.catalog.dto;

public class PendingBookDto {

    private String isbn;       // ISBN
    private String bookName;   // 书名
    private int amount;        // 数量
    private String source;     // 来源（采访验收 / 手动）

    public PendingBookDto() {
    }

    public PendingBookDto(String isbn, String bookName, int amount, String source) {
        this.isbn = isbn;
        this.bookName = bookName;
        this.amount = amount;
        this.source = source;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
