package com.example.library.catalog.repository;

import com.example.library.catalog.entity.DamageRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DamageRequestRepository extends JpaRepository<DamageRequestEntity, String> {
}
