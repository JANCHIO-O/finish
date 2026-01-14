package com.example.library.catalog.repository;

import com.example.library.catalog.entity.CatalogBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogBookRepository extends JpaRepository<CatalogBookEntity, String> {

    CatalogBookEntity findByBookId(String bookId);
}
