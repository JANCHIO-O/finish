package com.example.library.circulation.dto;
import java.util.Date;

public class NoticeDto {
    private String noticeId;
    private String noticeTitle;
    private String noticeContent;
    private String targetCardNo;
    private String bizType;
    private String bizId;
    private Date publishTime;
    private String publisher;
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