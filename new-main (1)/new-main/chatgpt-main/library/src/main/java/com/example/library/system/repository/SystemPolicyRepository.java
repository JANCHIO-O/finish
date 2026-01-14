package com.example.library.system.repository;

import com.example.library.system.entity.SystemPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemPolicyRepository extends JpaRepository<SystemPolicy, Long> {
    Optional<SystemPolicy> findByRole(String role);
}
