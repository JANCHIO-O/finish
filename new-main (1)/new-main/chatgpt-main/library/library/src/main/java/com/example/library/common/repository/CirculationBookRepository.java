package com.example.library.common.repository;

import com.example.library.common.entity.CirculationBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CirculationBookRepository extends JpaRepository<CirculationBook, String> {
}
