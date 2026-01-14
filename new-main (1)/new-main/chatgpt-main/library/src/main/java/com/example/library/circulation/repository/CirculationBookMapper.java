package com.example.library.circulation.repository;

import com.example.library.circulation.entity.CirculationBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CirculationBookMapper extends JpaRepository<CirculationBookEntity, String> {
    Optional<CirculationBookEntity> findByBookId(String bookId);
    List<CirculationBookEntity> findByStatus(Integer status);
}