package com.custom.app.ui.notification;

import com.core.app.data.user.UserManager;
import com.custom.app.data.model.notification.NotificationItem;
import com.custom.app.data.model.notification.NotificationRequest;
import com.custom.app.network.RestService;

import java.util.List;

import io.reactivex.Single;

public class NotificationInteractorImpl implements NotificationInteractor {

    private RestService restService;
    private UserManager userManager;

    public NotificationInteractorImpl(RestService restService, UserManager userManager) {
        this.restService = restService;
        this.userManager = userManager;
    }

    @Override
    public Single<List<NotificationItem>> fetchNotifications() {
        return restService.notificationList(new NotificationRequest(userManager.getUserId()))
                .map(NotificationParser::parse);
    }

    @Override
    public Single<String> clearNotifications() {
        return restService.clearNotification(new NotificationRequest(userManager.getUserId()))
                .map(NotificationParser::clear);
    }
}