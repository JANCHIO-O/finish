package com.example.library.periodical.repository;

import com.example.library.periodical.entity.PeriodicalAcceptanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodicalAcceptanceRecordRepository extends JpaRepository<PeriodicalAcceptanceRecord, String> {
}
