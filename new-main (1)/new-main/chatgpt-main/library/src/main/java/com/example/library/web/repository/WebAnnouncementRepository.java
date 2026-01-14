package com.example.library.web.repository;

import com.example.library.web.entity.WebAnnouncement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebAnnouncementRepository extends JpaRepository<WebAnnouncement, Long> {
    List<WebAnnouncement> findAllByOrderByCreatedAtDesc();
}
