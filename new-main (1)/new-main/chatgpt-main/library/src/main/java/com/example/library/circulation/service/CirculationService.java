package com.example.library.circulation.service;

import com.example.library.circulation.dto.BorrowRecordDto;
import com.example.library.circulation.dto.CirculationBookDto;
import com.example.library.circulation.dto.NoticeDto;

import java.util.List;

public interface CirculationService {
    String borrowBook(String cardNo, String bookId);
    String returnBook(String cardNo, String bookId);
    String reserveBook(String cardNo, String bookId);
    List<BorrowRecordDto> getBorrowRecords(String cardNo);
    List<BorrowRecordDto> getOverdueRecords();
    List<CirculationBookDto> getAvailableBooks();
    List<NoticeDto> getValidNotices(String cardNo);
    List<NoticeDto> getAllNotices();
    String addNotice(String noticeTitle, String noticeContent, String publisher);
    String disableNotice(String noticeId);
}