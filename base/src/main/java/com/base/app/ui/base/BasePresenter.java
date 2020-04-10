package com.base.app.ui.base;

import com.core.app.util.RxUtils;

import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<V extends BaseView> {

    private V view;
    protected Disposable disposable;

    public void setView(V view) {
        this.view = view;
    }

    public V getView() {
        return view;
    }

    protected void close() {
        if (isViewAttached()) {
            view.close();
        }
    }

    protected void showEmptyView() {
        if (isViewAttached()) {
            view.showEmptyView();
        }
    }

    protected void showProgressBar() {
        if (isViewAttached()) {
            view.showProgressBar();
        }
    }

    protected void hideProgressBar() {
        if (isViewAttached()) {
            view.hideProgressBar();
        }
    }

    protected void showProgressDialog() {
        if (isViewAttached()) {
            view.showProgressDialog();
        }
    }

    protected void hideProgressDialog() {
        if (isViewAttached()) {
            view.hideProgressDialog();
        }
    }

    protected void showMessage(String msg) {
        if (isViewAttached()) {
            view.showMessage(msg);
        }
    }

    protected boolean isViewAttached() {
        return view != null;
    }

    public void destroy() {
        view = null;
        RxUtils.dispose(disposable);
    }
}