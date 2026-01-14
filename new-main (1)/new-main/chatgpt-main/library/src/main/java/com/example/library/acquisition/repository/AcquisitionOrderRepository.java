package com.example.library.acquisition.repository;

import com.example.library.acquisition.entity.AcquisitionOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcquisitionOrderRepository extends JpaRepository<AcquisitionOrder, String> {
}
