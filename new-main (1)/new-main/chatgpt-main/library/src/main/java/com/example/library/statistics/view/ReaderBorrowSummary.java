package com.example.library.statistics.view;

public class ReaderBorrowSummary {

    private final String cardNo;
    private final String name;
    private final long borrowCount;
    private final String borrowedTitlesText;

    public ReaderBorrowSummary(String cardNo, String name, long borrowCount, String borrowedTitlesText) {
        this.cardNo = cardNo;
        this.name = name;
        this.borrowCount = borrowCount;
        this.borrowedTitlesText = borrowedTitlesText;
    }

    public String getCardNo() {
        return cardNo;
    }

    public String getName() {
        return name;
    }

    public long getBorrowCount() {
        return borrowCount;
    }

    public String getBorrowedTitlesText() {
        return borrowedTitlesText;
    }
}
