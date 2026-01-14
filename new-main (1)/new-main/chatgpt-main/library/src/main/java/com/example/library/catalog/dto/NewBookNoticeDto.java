package com.example.library.catalog.dto;

import java.util.Date;
import java.util.List;

public class NewBookNoticeDto {

    private String noticeId;
    private String title;
    private List<TransferBookDto> books;
    private Date publishTime;

    // ⭐ 新增字段
    private String noticeStatus; // 草稿 / 已发布

    public NewBookNoticeDto(String noticeId, String title,
                            List<TransferBookDto> books, Date publishTime) {
        this.noticeId = noticeId;
        this.title = title;
        this.books = books;
        this.publishTime = publishTime;
        this.noticeStatus = "草稿";
    }

    public String getNoticeId() { return noticeId; }
    public String getTitle() { return title; }
    public List<TransferBookDto> getBooks() { return books; }
    public Date getPublishTime() { return publishTime; }

    public String getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(String noticeStatus) {
        this.noticeStatus = noticeStatus;
    }
}
