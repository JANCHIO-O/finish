package com.example.library.catalog.dto;

public class CirculationBookDto {

    private String isbn;
    private String bookName;

    public CirculationBookDto(String isbn, String bookName) {
        this.isbn = isbn;
        this.bookName = bookName;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getBookName() {
        return bookName;
    }
}
