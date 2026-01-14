package com.example.library.statistics.repository;

import com.example.library.statistics.entity.StatisticsRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticsRecordRepository extends JpaRepository<StatisticsRecord, String> {
}
