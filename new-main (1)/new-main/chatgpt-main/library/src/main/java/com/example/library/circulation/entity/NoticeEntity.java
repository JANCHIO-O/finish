package com.example.library.circulation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "notice")
public class NoticeEntity {
    @Id
    @Column(name = "notice_id", length = 13, nullable = false)
    private String noticeId;

    @Column(name = "notice_title", length = 50, nullable = false)
    private String noticeTitle;

    @Column(name = "notice_content", length = 255, nullable = false)
    private String noticeContent;

    @Column(name = "target_card_no", length = 10)
    private String targetCardNo;

    @Column(name = "biz_type", length = 10, nullable = false)
    private String bizType;

    @Column(name = "biz_id", length = 15)
    private String bizId;

    @Column(name = "publish_time", nullable = false)
    private Date publishTime;

    @Column(name = "publisher", length = 20, nullable = false)
    private String publisher;

    @Column(name = "is_valid", length = 2, nullable = false)
    private String isValid;

    // 全参GETTER & SETTER
    public String getNoticeId() { return noticeId; }
    public void setNoticeId(String noticeId) { this.noticeId = noticeId; }
    public String getNoticeTitle() { return noticeTitle; }
    public void setNoticeTitle(String noticeTitle) { this.noticeTitle = noticeTitle; }
    public String getNoticeContent() { return noticeContent; }
    public void setNoticeContent(String noticeContent) { this.noticeContent = noticeContent; }
    public String getTargetCardNo() { return targetCardNo; }
    public void setTargetCardNo(String targetCardNo) { this.targetCardNo = targetCardNo; }
    public String getBizType() { return bizType; }
    public void setBizType(String bizType) { this.bizType = bizType; }
    public String getBizId() { return bizId; }
    public void setBizId(String bizId) { this.bizId = bizId; }
    public Date getPublishTime() { return publishTime; }
    public void setPublishTime(Date publishTime) { this.publishTime = publishTime; }
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public String getIsValid() { return isValid; }
    public void setIsValid(String isValid) { this.isValid = isValid; }
}