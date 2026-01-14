package com.example.library.periodical.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "periodical_acceptance_record")
public class PeriodicalAcceptanceRecord {

    @Id
    private String acceptanceId;

    private String title;
    private String issn;
    private String publisher;
    private Integer receivedQuantity;
    private String checker;
    private Date acceptanceDate;
    private String status;

    public PeriodicalAcceptanceRecord() {
    }

    public PeriodicalAcceptanceRecord(String acceptanceId, String title, String issn, String publisher, Integer receivedQuantity, String checker, Date acceptanceDate, String status) {
        this.acceptanceId = acceptanceId;
        this.title = title;
        this.issn = issn;
        this.publisher = publisher;
        this.receivedQuantity = receivedQuantity;
        this.checker = checker;
        this.acceptanceDate = acceptanceDate;
        this.status = status;
    }

    public String getAcceptanceId() {
        return acceptanceId;
    }

    public void setAcceptanceId(String acceptanceId) {
        this.acceptanceId = acceptanceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(Integer receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public Date getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(Date acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
