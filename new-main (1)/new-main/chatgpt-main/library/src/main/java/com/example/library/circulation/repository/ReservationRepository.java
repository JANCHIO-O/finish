package com.example.library.circulation.repository;

import com.example.library.circulation.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, String> {
    List<ReservationEntity> findByBookIdAndCardNoAndStatus(String bookId, String cardNo, String status);
    List<ReservationEntity> findByBookIdAndStatus(String bookId, String status);
}