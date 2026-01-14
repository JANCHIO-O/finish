package com.example.library.statistics.service;

import java.sql.Date;

public class StatisticsPeriodRange {
    private final Date startDate;
    private final Date endDate;

    public StatisticsPeriodRange(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
