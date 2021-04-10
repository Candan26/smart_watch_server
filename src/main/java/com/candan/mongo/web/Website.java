package com.candan.mongo.web;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

public class Website {
    @Id
    private String id;
    private String userName;
    private List<String> receivedMessageIdList;
    private List<String> activePhotoIdList;
    private List<String> activePublishedMessageIdList;
    private Date date;

    public Website( String userName, List<String> receivedMessageIdList, List<String> activePhotoIdList, List<String> activePublishedMessageIdList, Date date) {

        this.userName = userName;
        this.receivedMessageIdList = receivedMessageIdList;
        this.activePhotoIdList = activePhotoIdList;
        this.activePublishedMessageIdList = activePublishedMessageIdList;
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getActivePhotoIdList() {
        return activePhotoIdList;
    }

    public void setActivePhotoIdList(List<String> activePhotoIdList) {
        this.activePhotoIdList = activePhotoIdList;
    }

    public List<String> getActivePublishedMessageIdList() {
        return activePublishedMessageIdList;
    }

    public void setActivePublishedMessageIdList(List<String> activePublishedMessageIdList) {
        this.activePublishedMessageIdList = activePublishedMessageIdList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getReceivedMessageIdList() {
        return receivedMessageIdList;
    }

    public void setReceivedMessageIdList(List<String> receivedMessageIdList) {
        this.receivedMessageIdList = receivedMessageIdList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "WebSite{" +
                "id='" + id + '\'' +
                ", receivedMessage='" + receivedMessageIdList + '\'' +
                ", userName='" + userName + '\'' +
                ", activePhotoIdList=" + activePhotoIdList +
                ", activePublishedMessageList=" + activePublishedMessageIdList +
                ", date=" + date +
                '}';
    }
}
