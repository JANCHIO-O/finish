package com.example.library.system.repository;

import com.example.library.system.entity.SystemInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemInfoRepository extends JpaRepository<SystemInfo, Long> {
    Optional<SystemInfo> findFirstByOrderByIdAsc();
}
