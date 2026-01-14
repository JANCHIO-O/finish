package com.example.library.circulation.repository;

import com.example.library.circulation.entity.ReaderInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderInfoMapper extends JpaRepository<ReaderInfoEntity, String> {
}