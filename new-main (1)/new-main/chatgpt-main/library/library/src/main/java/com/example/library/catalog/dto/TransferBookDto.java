package com.example.library.catalog.dto;

public class TransferBookDto {

    private String isbn;
    private String bookName;
    private int amount;
    private String location;

    // ⭐ 新增字段
    private String transferStatus; // 未通报 / 已通报

    public TransferBookDto(String isbn, String bookName, int amount, String location) {
        this.isbn = isbn;
        this.bookName = bookName;
        this.amount = amount;
        this.location = location;
        this.transferStatus = "未通报";
    }

    // getter / setter
    public String getIsbn() { return isbn; }
    public String getBookName() { return bookName; }
    public int getAmount() { return amount; }
    public String getLocation() { return location; }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }
}
