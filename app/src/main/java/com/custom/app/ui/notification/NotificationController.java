package com.custom.app.ui.notification;

import com.core.app.ui.epoxy.BaseEpoxy;
import com.custom.app.data.model.notification.NotificationItem;

import java.util.List;

public class NotificationController extends BaseEpoxy {

    private NotificationView view;
    private List<NotificationItem> notifications;

    NotificationController(NotificationView view) {
        this.view = view;
    }

    void setList(List<NotificationItem> notifications) {
        this.notifications = notifications;
        requestModelBuild();
    }

    @Override
    protected void buildModels() {
        for (NotificationItem notification : notifications) {
            new NotificationItemModel_()
                    .id(notification.getId())
                    .view(view)
                    .title(notification.getTitle())
                    .desc(notification.getDesc())
                    .timestamp(notification.getTimestamp())
                    .clickListener(itemView -> view.onItemClicked(notification))
                    .addTo(this);
        }
    }
}