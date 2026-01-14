package com.example.library.periodical.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "periodical_interview_record")
public class PeriodicalInterviewRecord {

    @Id
    private String interviewId;

    private String title;
    private String issn;
    private String publisher;
    private String interviewer;
    private Date interviewDate;
    private String notes;

    public PeriodicalInterviewRecord() {
    }

    public PeriodicalInterviewRecord(String interviewId, String title, String issn, String publisher, String interviewer, Date interviewDate, String notes) {
        this.interviewId = interviewId;
        this.title = title;
        this.issn = issn;
        this.publisher = publisher;
        this.interviewer = interviewer;
        this.interviewDate = interviewDate;
        this.notes = notes;
    }

    public String getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(String interviewId) {
        this.interviewId = interviewId;
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

    public String getInterviewer() {
        return interviewer;
    }

    public void setInterviewer(String interviewer) {
        this.interviewer = interviewer;
    }

    public Date getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(Date interviewDate) {
        this.interviewDate = interviewDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
