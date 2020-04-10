package com.base.app.ui.base;

import android.content.Context;

public interface BaseView {

    void close();

    Context context();

    void showEmptyView();

    void showProgressBar();

    void hideProgressBar();

    void showProgressDialog();

    void hideProgressDialog();

    void showMessage(String msg);

}