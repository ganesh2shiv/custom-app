package com.custom.app.ui.notification;

import com.core.app.ui.base.BaseView;
import com.custom.app.data.model.notification.NotificationItem;

import java.util.List;

interface NotificationView extends BaseView {

    void showList(List<NotificationItem> notifications);

    void onItemClicked(NotificationItem notification);

    void clearNotifications();

}