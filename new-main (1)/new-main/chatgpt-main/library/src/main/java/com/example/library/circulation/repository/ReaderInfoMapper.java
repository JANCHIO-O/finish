package com.example.library.circulation.repository;

import com.example.library.common.entity.ReaderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderInfoMapper extends JpaRepository<ReaderInfo, String> {
}
