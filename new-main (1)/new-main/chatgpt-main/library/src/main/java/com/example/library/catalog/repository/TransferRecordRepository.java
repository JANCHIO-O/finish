package com.example.library.catalog.repository;

import com.example.library.catalog.entity.TransferRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRecordRepository extends JpaRepository<TransferRecordEntity, String> {
}
