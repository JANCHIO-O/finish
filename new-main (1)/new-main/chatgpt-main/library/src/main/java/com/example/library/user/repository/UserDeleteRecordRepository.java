package com.example.library.user.repository;

import com.example.library.user.entity.UserDeleteRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDeleteRecordRepository extends JpaRepository<UserDeleteRecord, Long> {
    List<UserDeleteRecord> findAllByOrderByDeletedAtDesc();
}
