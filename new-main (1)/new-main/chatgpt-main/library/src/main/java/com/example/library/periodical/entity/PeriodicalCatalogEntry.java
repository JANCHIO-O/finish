package com.example.library.periodical.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "periodical_catalog_entry")
public class PeriodicalCatalogEntry {

    @Id
    private String catalogId;

    private String title;
    private String issn;
    private String publisher;
    private Date publishDate;
    private String location;

    public PeriodicalCatalogEntry() {
    }

    public PeriodicalCatalogEntry(String catalogId, String title, String issn, String publisher, Date publishDate, String location) {
        this.catalogId = catalogId;
        this.title = title;
        this.issn = issn;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.location = location;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
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

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
