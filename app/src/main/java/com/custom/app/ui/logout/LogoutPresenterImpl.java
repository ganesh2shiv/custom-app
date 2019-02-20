package com.custom.app.ui.logout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LogoutPresenterImpl extends LogoutPresenter {

    private LogoutView view;
    private LogoutInteractor interactor;

    public LogoutPresenterImpl(LogoutInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void setView(LogoutView view) {
        super.setView(view);
        this.view = view;
    }

    @Override
    public void callLogout() {
        disposable = interactor.logout()
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(msg -> {
                    showMessage(msg);
                    destroy();
                }, error -> showMessage(error.getMessage()));

        if (isViewAttached()) {
            view.showLoginScreen();
        }
    }
}