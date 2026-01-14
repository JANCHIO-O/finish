package com.example.library.system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "system_policy")
public class SystemPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String role;

    @Column(nullable = false)
    private int maxBorrowDays;

    @Column(nullable = false)
    private int maxBorrowCount;

    @Column(nullable = false, length = 200)
    private String overduePenalty;

    public SystemPolicy() {
    }

    public SystemPolicy(String role, int maxBorrowDays, int maxBorrowCount, String overduePenalty) {
        this.role = role;
        this.maxBorrowDays = maxBorrowDays;
        this.maxBorrowCount = maxBorrowCount;
        this.overduePenalty = overduePenalty;
    }

    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getMaxBorrowDays() {
        return maxBorrowDays;
    }

    public void setMaxBorrowDays(int maxBorrowDays) {
        this.maxBorrowDays = maxBorrowDays;
    }

    public int getMaxBorrowCount() {
        return maxBorrowCount;
    }

    public void setMaxBorrowCount(int maxBorrowCount) {
        this.maxBorrowCount = maxBorrowCount;
    }

    public String getOverduePenalty() {
        return overduePenalty;
    }

    public void setOverduePenalty(String overduePenalty) {
        this.overduePenalty = overduePenalty;
    }
}
