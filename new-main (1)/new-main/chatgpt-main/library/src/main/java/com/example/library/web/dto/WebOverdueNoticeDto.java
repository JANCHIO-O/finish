package com.example.library.web.dto;

import java.time.LocalDate;

public class WebOverdueNoticeDto {
    private final String readerName;
    private final String bookTitle;
    private final String bookId;
    private final long overdueDays;
    private final LocalDate borrowDate;
    private final LocalDate dueDate;

    public WebOverdueNoticeDto(String readerName, String bookTitle, String bookId, long overdueDays,
                               LocalDate borrowDate, LocalDate dueDate) {
        this.readerName = readerName;
        this.bookTitle = bookTitle;
        this.bookId = bookId;
        this.overdueDays = overdueDays;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    public String getReaderName() {
        return readerName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookId() {
        return bookId;
    }

    public long getOverdueDays() {
        return overdueDays;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
