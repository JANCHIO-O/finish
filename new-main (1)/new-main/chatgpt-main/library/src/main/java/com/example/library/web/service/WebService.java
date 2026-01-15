package com.example.library.web.service;

import com.example.library.common.entity.BorrowRecord;
import com.example.library.common.entity.CirculationBook;
import com.example.library.common.entity.UserAccount;
import com.example.library.common.repository.BorrowRecordRepository;
import com.example.library.common.repository.CirculationBookRepository;
import com.example.library.common.repository.UserAccountRepository;
import com.example.library.catalog.entity.TransferRecordEntity;
import com.example.library.catalog.repository.TransferRecordRepository;
import com.example.library.web.dto.WebNewBookDto;
import com.example.library.web.dto.WebOverdueNoticeDto;
import com.example.library.web.entity.WebAnnouncement;
import com.example.library.web.entity.WebMessage;
import com.example.library.web.repository.WebAnnouncementRepository;
import com.example.library.web.repository.WebMessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WebService {

    private static final long BORROW_DAYS_LIMIT = 30L;

    private final UserAccountRepository userAccountRepository;
    private final BorrowRecordRepository borrowRecordRepository;
    private final CirculationBookRepository circulationBookRepository;
    private final TransferRecordRepository transferRecordRepository;
    private final WebAnnouncementRepository webAnnouncementRepository;
    private final WebMessageRepository webMessageRepository;

    public WebService(UserAccountRepository userAccountRepository,
                      BorrowRecordRepository borrowRecordRepository,
                      CirculationBookRepository circulationBookRepository,
                      TransferRecordRepository transferRecordRepository,
                      WebAnnouncementRepository webAnnouncementRepository,
                      WebMessageRepository webMessageRepository) {
        this.userAccountRepository = userAccountRepository;
        this.borrowRecordRepository = borrowRecordRepository;
        this.circulationBookRepository = circulationBookRepository;
        this.transferRecordRepository = transferRecordRepository;
        this.webAnnouncementRepository = webAnnouncementRepository;
        this.webMessageRepository = webMessageRepository;
    }

    public Optional<UserAccount> authenticate(String accountId, String password) {
        return userAccountRepository.findByAccountId(accountId)
                .filter(account -> account.getPassword().equals(password));
    }

    public List<WebNewBookDto> listNewBooks() {
        Map<String, CirculationBook> circulationMap = circulationBookRepository.findAll().stream()
                .collect(Collectors.toMap(CirculationBook::getIsbn, record -> record, (a, b) -> a));
        return transferRecordRepository.findAll().stream()
                .sorted(Comparator.comparing(TransferRecordEntity::getTransferDate,
                        Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .map(record -> {
                    CirculationBook circulationBook = circulationMap.get(record.getIsbn());
                    String author = circulationBook != null && circulationBook.getAuthor() != null
                            && !circulationBook.getAuthor().isBlank() ? circulationBook.getAuthor() : "未知";
                    String publisher = circulationBook != null && circulationBook.getPublisher() != null
                            && !circulationBook.getPublisher().isBlank() ? circulationBook.getPublisher() : "未知";
                    String docType = circulationBook != null && circulationBook.getDocType() != null
                            && !circulationBook.getDocType().isBlank() ? circulationBook.getDocType() : "未知";
                    return new WebNewBookDto(
                            record.getBookName(),
                            author,
                            record.getIsbn(),
                            publisher,
                            docType,
                            record.getTransferDate()
                    );
                })
                .toList();
    }

    public List<String> listCategories() {
        return circulationBookRepository.findAll().stream()
                .map(CirculationBook::getDocType)
                .filter(docType -> docType != null && !docType.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    public List<CirculationBook> searchBooks(String category, String title, String author, String isbn, String keyword) {
        return circulationBookRepository.findAll().stream()
                .filter(record -> matchesCategory(record, category))
                .filter(record -> matchesBasicInfo(record, title, author, isbn))
                .filter(record -> matchesKeyword(record, keyword))
                .sorted(Comparator.comparing(CirculationBook::getIsbn))
                .toList();
    }

    private boolean matchesCategory(CirculationBook record, String category) {
        if (category == null || category.isBlank()) {
            return true;
        }
        return record.getDocType() != null && record.getDocType().contains(category.trim());
    }

    private boolean matchesBasicInfo(CirculationBook record, String title, String author, String isbn) {
        boolean titleMatch = title == null || title.isBlank() ||
                (record.getTitle() != null && record.getTitle().contains(title.trim()));
        boolean authorMatch = author == null || author.isBlank() ||
                (record.getAuthor() != null && record.getAuthor().contains(author.trim()));
        boolean isbnMatch = isbn == null || isbn.isBlank() ||
                (record.getIsbn() != null && record.getIsbn().contains(isbn.trim()));
        return titleMatch && authorMatch && isbnMatch;
    }

    private boolean matchesKeyword(CirculationBook record, String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return true;
        }
        String trimmed = keyword.trim();
        return containsField(record.getTitle(), trimmed)
                || containsField(record.getAuthor(), trimmed)
                || containsField(record.getIsbn(), trimmed)
                || containsField(record.getPublisher(), trimmed)
                || containsField(record.getDocType(), trimmed);
    }

    private boolean containsField(String field, String keyword) {
        return field != null && field.contains(keyword);
    }

    public List<WebAnnouncement> listAnnouncements() {
        return webAnnouncementRepository.findAllByOrderByCreatedAtDesc();
    }

    public void addAnnouncement(String title, String content, String publisher) {
        WebAnnouncement announcement = new WebAnnouncement(title, content, publisher, LocalDateTime.now());
        webAnnouncementRepository.save(announcement);
    }

    public List<WebMessage> listMessages() {
        return webMessageRepository.findAllByOrderByCreatedAtDesc();
    }

    public void addMessage(String readerName, String content) {
        WebMessage message = new WebMessage(readerName, content, LocalDateTime.now());
        webMessageRepository.save(message);
    }

    public List<WebOverdueNoticeDto> listOverdueNotices() {
        LocalDate today = LocalDate.now();
        return borrowRecordRepository.findAll().stream()
                .filter(record -> "借阅".equals(record.getEventType()) && record.getReturnDate() == null)
                .map(record -> {
                    LocalDate borrowDate = record.getFlowDate().toLocalDate();
                    LocalDate dueDate = borrowDate.plusDays(BORROW_DAYS_LIMIT);
                    long overdueDays = ChronoUnit.DAYS.between(dueDate, today);
                    if (overdueDays <= 0) {
                        return null;
                    }
                    return new WebOverdueNoticeDto(
                            record.getName(),
                            record.getTitle(),
                            record.getBookId(),
                            overdueDays,
                            borrowDate,
                            dueDate
                    );
                })
                .filter(dto -> dto != null)
                .sorted(Comparator.comparing(WebOverdueNoticeDto::getOverdueDays).reversed())
                .toList();
    }
}
