package com.example.library.periodical.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "periodical_binding_record")
public class PeriodicalBindingRecord {

    @Id
    private String bindId;

    private String title;
    private String issn;
    private String publisher;
    private String binder;
    private Date bindDate;
    private String shelfLocation;

    public PeriodicalBindingRecord() {
    }

    public PeriodicalBindingRecord(String bindId, String title, String issn, String publisher, String binder, Date bindDate, String shelfLocation) {
        this.bindId = bindId;
        this.title = title;
        this.issn = issn;
        this.publisher = publisher;
        this.binder = binder;
        this.bindDate = bindDate;
        this.shelfLocation = shelfLocation;
    }

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
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

    public String getBinder() {
        return binder;
    }

    public void setBinder(String binder) {
        this.binder = binder;
    }

    public Date getBindDate() {
        return bindDate;
    }

    public void setBindDate(Date bindDate) {
        this.bindDate = bindDate;
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    public void setShelfLocation(String shelfLocation) {
        this.shelfLocation = shelfLocation;
    }
}
