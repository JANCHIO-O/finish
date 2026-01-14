package com.example.library.circulation.repository;

import com.example.library.common.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BorrowRecordMapper extends JpaRepository<BorrowRecord, String> {
    List<BorrowRecord> findByCardNo(String cardNo);
    List<BorrowRecord> findByBookIdAndCardNoAndEventType(String bookId, String cardNo, String eventType);
    List<BorrowRecord> findByReturnDateIsNullAndFlowDateBefore(Date flowDate);
    List<BorrowRecord> findByCardNoAndReturnDateIsNullAndFlowDateBefore(String cardNo, Date flowDate);
}
