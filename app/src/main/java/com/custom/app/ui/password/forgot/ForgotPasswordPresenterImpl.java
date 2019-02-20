package com.custom.app.ui.password.forgot;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ForgotPasswordPresenterImpl extends ForgotPasswordPresenter {

    private ForgotPasswordView view;
    private ForgotPasswordInteractor interactor;

    public ForgotPasswordPresenterImpl(ForgotPasswordInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void setView(ForgotPasswordView view) {
        super.setView(view);
        this.view = view;
    }

    @Override
    public void callForgotPassword(String username) {
        showProgressBar();

        disposable = interactor.forgotPassword(username)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(message -> {
                    hideProgressBar();

                    if (isViewAttached()) {
                        view.showLoginScreen(message);
                    }
                }, error -> {
                    hideProgressBar();
                    showMessage(error.getMessage());
                });
    }
}