package com.example.library.web.repository;

import com.example.library.web.entity.WebMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebMessageRepository extends JpaRepository<WebMessage, Long> {
    List<WebMessage> findAllByOrderByCreatedAtDesc();
}
