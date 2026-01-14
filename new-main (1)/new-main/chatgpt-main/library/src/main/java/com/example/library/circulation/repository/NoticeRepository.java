package com.example.library.circulation.repository;

import com.example.library.circulation.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, String> {
    List<NoticeEntity> findByIsValidOrderByPublishTimeDesc(String isValid);
    List<NoticeEntity> findByIsValidAndTargetCardNoIsNullOrTargetCardNoOrderByPublishTimeDesc(String isValid, String targetCardNo);
    // 修复：删除错误的带参方法，添加正确的无参排序方法
    List<NoticeEntity> findAllByOrderByPublishTimeDesc();
}