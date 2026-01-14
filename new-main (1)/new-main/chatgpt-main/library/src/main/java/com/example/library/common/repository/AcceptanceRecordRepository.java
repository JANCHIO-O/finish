package com.example.library.common.repository;

import com.example.library.common.entity.AcceptanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcceptanceRecordRepository extends JpaRepository<AcceptanceRecord, String> {

    // 方便按 ISBN 找到验收记录（编目后删除会用到）
    List<AcceptanceRecord> findByIsbn(String isbn);
}
