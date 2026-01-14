package com.example.library.web.dto;

import java.time.LocalDate;

public class WebNewBookDto {
    private final String title;
    private final String author;
    private final String isbn;
    private final String publisher;
    private final String docType;
    private final LocalDate transferDate;

    public WebNewBookDto(String title, String author, String isbn, String publisher, String docType, LocalDate transferDate) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.docType = docType;
        this.transferDate = transferDate;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getDocType() {
        return docType;
    }

    public LocalDate getTransferDate() {
        return transferDate;
    }
}
