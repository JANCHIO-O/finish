package com.example.library.common.repository;

import com.example.library.common.entity.BorrowRecord;
import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, String> {
    List<BorrowRecord> findByFlowDateBetween(Date startDate, Date endDate);
}
