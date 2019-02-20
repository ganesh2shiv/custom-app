package com.custom.app.data.model.notification;

public class NotificationItem {

    private String id;
    private String title;
    private String desc;
    private String timestamp;

    public NotificationItem(String title, String desc, String timestamp) {
        this.title = title;
        this.desc = desc;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getTimestamp() {
        return timestamp;
    }
}