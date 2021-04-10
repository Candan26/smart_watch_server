package com.candan.utils.message;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Message {
    @Id
    private String id;

    private String header;

    private String footer;

    private List<String> messageList;

    private  String receivedMessage;

    private String dateString;

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getReceivedMessage() {
        return receivedMessage;
    }

    public void setReceivedMessage(String receivedMessage) {
        this.receivedMessage = receivedMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public List<String> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<String> messageList) {
        this.messageList = messageList;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", header='" + header + '\'' +
                ", footer='" + footer + '\'' +
                ", messageList=" + messageList +
                ", receivedMessage='" + receivedMessage + '\'' +
                ", date=" + dateString +
                '}';
    }
}
