package com.example.library.periodical.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "periodical_visit_record")
public class PeriodicalVisitRecord {

    @Id
    private String visitId;

    private String title;
    private String issn;
    private String publisher;
    private String recommender;
    private Date recommendDate;
    private String reason;

    public PeriodicalVisitRecord() {
    }

    public PeriodicalVisitRecord(String visitId, String title, String issn, String publisher, String recommender, Date recommendDate, String reason) {
        this.visitId = visitId;
        this.title = title;
        this.issn = issn;
        this.publisher = publisher;
        this.recommender = recommender;
        this.recommendDate = recommendDate;
        this.reason = reason;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
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

    public String getRecommender() {
        return recommender;
    }

    public void setRecommender(String recommender) {
        this.recommender = recommender;
    }

    public Date getRecommendDate() {
        return recommendDate;
    }

    public void setRecommendDate(Date recommendDate) {
        this.recommendDate = recommendDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
