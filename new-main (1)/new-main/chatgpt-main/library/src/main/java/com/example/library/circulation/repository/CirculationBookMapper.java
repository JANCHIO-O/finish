package com.example.library.circulation.repository;

import com.example.library.common.entity.CirculationBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CirculationBookMapper extends JpaRepository<CirculationBook, String> {
    Optional<CirculationBook> findByBookId(String bookId);
    List<CirculationBook> findByStatus(Integer status);
}
