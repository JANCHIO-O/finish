package com.example.library.system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "system_log")
public class SystemLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String operatorType;

    @Column(nullable = false)
    private String actionType;

    @Column(nullable = false)
    private LocalDateTime actionTime;

    @Column(nullable = false, length = 255)
    private String description;

    public SystemLog() {
    }

    public SystemLog(String operatorType, String actionType, LocalDateTime actionTime, String description) {
        this.operatorType = operatorType;
        this.actionType = actionType;
        this.actionTime = actionTime;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public LocalDateTime getActionTime() {
        return actionTime;
    }

    public void setActionTime(LocalDateTime actionTime) {
        this.actionTime = actionTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
