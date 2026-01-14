package com.example.library.statistics.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "statistics_record")
public class StatisticsRecord {

    @Id
    private String statId;

    private String statType;
    private String statPeriod;
    private Date statDate;
    private long totalBorrow;
    private long activeReaderCount;
    private String remark;

    public StatisticsRecord() {
    }

    public StatisticsRecord(String statId, String statType, String statPeriod, Date statDate, long totalBorrow, long activeReaderCount, String remark) {
        this.statId = statId;
        this.statType = statType;
        this.statPeriod = statPeriod;
        this.statDate = statDate;
        this.totalBorrow = totalBorrow;
        this.activeReaderCount = activeReaderCount;
        this.remark = remark;
    }

    public String getStatId() {
        return statId;
    }

    public void setStatId(String statId) {
        this.statId = statId;
    }

    public String getStatType() {
        return statType;
    }

    public void setStatType(String statType) {
        this.statType = statType;
    }

    public String getStatPeriod() {
        return statPeriod;
    }

    public void setStatPeriod(String statPeriod) {
        this.statPeriod = statPeriod;
    }

    public Date getStatDate() {
        return statDate;
    }

    public void setStatDate(Date statDate) {
        this.statDate = statDate;
    }

    public long getTotalBorrow() {
        return totalBorrow;
    }

    public void setTotalBorrow(long totalBorrow) {
        this.totalBorrow = totalBorrow;
    }

    public long getActiveReaderCount() {
        return activeReaderCount;
    }

    public void setActiveReaderCount(long activeReaderCount) {
        this.activeReaderCount = activeReaderCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
