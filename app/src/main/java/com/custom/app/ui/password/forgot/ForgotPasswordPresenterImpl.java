package com.custom.app.ui.password.forgot;

import com.custom.app.ui.password.PasswordInteractor;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ForgotPasswordPresenterImpl extends ForgotPasswordPresenter {

    private ForgotPasswordView view;
    private PasswordInteractor interactor;

    public ForgotPasswordPresenterImpl(PasswordInteractor interactor) {
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
                .subscribe(msg -> {
                    hideProgressBar();

                    if (isViewAttached()) {
                        view.showLoginScreen(msg);
                    }
                }, error -> {
                    hideProgressBar();
                    showMessage(error.getMessage());
                });
    }
}