package com.example.library.circulation.repository;

import com.example.library.circulation.entity.BorrowRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BorrowRecordMapper extends JpaRepository<BorrowRecordEntity, String> {
    List<BorrowRecordEntity> findByCardNo(String cardNo);
    List<BorrowRecordEntity> findByBookIdAndCardNoAndEventType(String bookId, String cardNo, String eventType);
    List<BorrowRecordEntity> findByReturnDateIsNullAndFlowDateBefore(Date flowDate);
}