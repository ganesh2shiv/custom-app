package com.custom.app.ui.notification;

import com.core.app.ui.base.BasePresenter;

public abstract class NotificationPresenter extends BasePresenter<NotificationView> {

    abstract void fetchNotifications();

    abstract void clearNotifications();

}