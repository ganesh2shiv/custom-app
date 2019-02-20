package com.custom.app.ui.notification;

import com.custom.app.data.model.notification.NotificationItem;

import java.util.List;

import io.reactivex.Single;

public interface NotificationInteractor {

    Single<List<NotificationItem>> fetchNotifications();

    Single<String> clearNotifications();

}