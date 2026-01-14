package com.example.library.periodical.repository;

import com.example.library.periodical.entity.PeriodicalOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodicalOrderRepository extends JpaRepository<PeriodicalOrder, String> {
}
