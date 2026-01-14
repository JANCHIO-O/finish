package com.example.library.catalog.dto;

import java.util.Date;

public class CatalogBookDto {

    private String bookId;
    private String isbn;
    private String bookName;
    private String classNo;
    private String cataloger;
    private Date catalogDate;

    public CatalogBookDto(String bookId, String isbn, String bookName,
                          String classNo, String cataloger, Date catalogDate) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.bookName = bookName;
        this.classNo = classNo;
        this.cataloger = cataloger;
        this.catalogDate = catalogDate;
    }

    public String getBookId() {
        return bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getBookName() {
        return bookName;
    }

    public String getClassNo() {
        return classNo;
    }

    public String getCataloger() {
        return cataloger;
    }

    public Date getCatalogDate() {
        return catalogDate;
    }
}
