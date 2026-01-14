package com.example.library.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@SuppressWarnings("ALL")
@Entity
@Table(name = "reader_info")
public class ReaderInfo {

    @Id
    private String cardNo;

    private String role;
    private String name;
    private String gender;
    private String mobile;
    private String idType;
    private String idNumber;

    // 无参构造函数
    public ReaderInfo() {
    }

    // 构造函数
    public ReaderInfo(String cardNo, String role, String name, String gender, String mobile, String idType, String idNumber) {
        this.cardNo = cardNo;
        this.role = role;
        this.name = name;
        this.gender = gender;
        this.mobile = mobile;
        this.idType = idType;
        this.idNumber = idNumber;
    }

    // Getters 和 Setters
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
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

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}
