package com.example.library.periodical.repository;

import com.example.library.periodical.entity.PeriodicalVisitRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodicalVisitRecordRepository extends JpaRepository<PeriodicalVisitRecord, String> {
    void deleteByIssn(String issn);
}
