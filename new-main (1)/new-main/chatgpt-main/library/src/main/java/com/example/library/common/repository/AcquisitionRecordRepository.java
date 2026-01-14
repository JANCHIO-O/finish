package com.example.library.common.repository;

import com.example.library.common.entity.AcquisitionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcquisitionRecordRepository extends JpaRepository<AcquisitionRecord, String> {
}
