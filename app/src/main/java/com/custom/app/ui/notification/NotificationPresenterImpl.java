package com.custom.app.ui.notification;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NotificationPresenterImpl extends NotificationPresenter {

    private NotificationView view;
    private NotificationInteractor interactor;

    public NotificationPresenterImpl(NotificationInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void setView(NotificationView view) {
        super.setView(view);
        this.view = view;
    }

    @Override
    public void fetchNotifications() {
        showProgressBar();

        disposable = interactor.fetchNotifications()
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(notifications -> {
                    hideProgressBar();

                    if (isViewAttached()) {
                        view.showList(notifications);
                    }
                }, error -> {
                    showEmptyView();
                    hideProgressBar();
                    showMessage(error.getMessage());
                });
    }

    @Override
    public void clearNotifications() {
        showProgressDialog();

        disposable = interactor.clearNotifications()
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(msg -> {
                    hideProgressDialog();
                    showMessage(msg);
                    showEmptyView();
                }, error -> {
                    hideProgressDialog();
                    showMessage(error.getMessage());
                });
    }
}