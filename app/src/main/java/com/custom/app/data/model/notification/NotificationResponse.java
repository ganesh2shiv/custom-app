package com.custom.app.data.model.notification;

import java.util.List;

public class NotificationResponse {

    private String msg;
    private boolean status;
    private List<NotificationItem> notificationList;

    public boolean isStatus() {
        return status;
    }

    public List<NotificationItem> getNotificationList() {
        return notificationList;
    }

    public String getMsg() {
        return msg;
    }
}