package com.example.library.catalog.repository;

import com.example.library.catalog.entity.DamageRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DamageRecordRepository extends JpaRepository<DamageRecordEntity, String> {
}
