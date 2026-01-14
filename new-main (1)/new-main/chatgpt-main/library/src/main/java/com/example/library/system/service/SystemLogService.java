package com.example.library.system.service;

import com.example.library.system.entity.SystemLog;
import com.example.library.system.repository.SystemLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SystemLogService {

    private final SystemLogRepository systemLogRepository;

    public SystemLogService(SystemLogRepository systemLogRepository) {
        this.systemLogRepository = systemLogRepository;
    }

    public void log(String operatorType, String actionType, String description) {
        SystemLog log = new SystemLog(operatorType, actionType, LocalDateTime.now(), description);
        systemLogRepository.save(log);
    }
}
