package com.example.library.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_delete_record")
public class UserDeleteRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String accountId;

    @Column(nullable = false, length = 20)
    private String role;

    @Column(length = 30)
    private String name;

    @Column(length = 10)
    private String gender;

    @Column(length = 20)
    private String mobile;

    @Column(nullable = false)
    private LocalDateTime deletedAt;

    public UserDeleteRecord() {
    }

    public UserDeleteRecord(String accountId,
                            String role,
                            String name,
                            String gender,
                            String mobile,
                            LocalDateTime deletedAt) {
        this.accountId = accountId;
        this.role = role;
        this.name = name;
        this.gender = gender;
        this.mobile = mobile;
        this.deletedAt = deletedAt;
    }

    public Long getId() {
        return id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
