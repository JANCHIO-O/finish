package com.example.library.periodical.repository;

import com.example.library.periodical.entity.PeriodicalCatalogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodicalCatalogEntryRepository extends JpaRepository<PeriodicalCatalogEntry, String> {
}
