package com.custom.app.ui.notification;

import com.custom.app.data.model.notification.NotificationItem;
import com.custom.app.data.model.notification.NotificationResponse;

import java.util.List;

import androidx.annotation.NonNull;
import retrofit2.Response;

class NotificationParser {

    @NonNull
    static List<NotificationItem> parse(Response<NotificationResponse> response) throws NullPointerException {

        if (response.isSuccessful()) {
            NotificationResponse body = response.body();

            if (body.isStatus()) {
                List<NotificationItem> notifications = body.getNotificationList();
                if (notifications != null && !notifications.isEmpty()) {
                    return notifications;
                } else {
                    throw new RuntimeException("Response payload is empty!");
                }
            } else {
                throw new RuntimeException(body.getMsg());
            }
        } else {
            throw new RuntimeException(response.message());
        }
    }

    @NonNull
    static String clear(Response<NotificationResponse> response) throws NullPointerException {

        if (response.isSuccessful()) {
            NotificationResponse body = response.body();

            if (body.isStatus()) {
                return body.getMsg();
            } else {
                throw new RuntimeException(body.getMsg());
            }
        } else {
            throw new RuntimeException(response.message());
        }
    }
}