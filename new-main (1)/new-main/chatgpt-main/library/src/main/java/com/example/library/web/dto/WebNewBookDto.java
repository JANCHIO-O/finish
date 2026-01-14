package com.example.library.web.dto;

import java.sql.Date;

public class WebNewBookDto {
    private final String title;
    private final String author;
    private final String isbn;
    private final String publisher;
    private final String docType;
    private final Date publishDate;

    public WebNewBookDto(String title, String author, String isbn, String publisher, String docType, Date publishDate) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.docType = docType;
        this.publishDate = publishDate;
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

    public Date getPublishDate() {
        return publishDate;
    }
}
