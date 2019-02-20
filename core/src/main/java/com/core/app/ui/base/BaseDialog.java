package com.core.app.ui.base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;

import com.core.app.CoreApplication;
import com.core.app.R;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;

public class BaseDialog extends DialogFragment implements BaseView {

    private ProgressDialog progressDialog;

    @Inject
    protected Context context;

    @Inject
    protected LocationRequest locationRequest;

    @Inject
    protected LocationSettingsRequest locationSettings;

    @Inject
    protected ReactiveLocationProvider locationProvider;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onAttach(Context context) {
        ((CoreApplication) getActivity().getApplication()).getCoreComponent().inject(this);
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        if (dialog.getWindow() != null) {
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        return dialog;
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void showEmptyView() {
    }

    @Override
    public void showProgressBar() {
    }

    @Override
    public void hideProgressBar() {
    }

    @Override
    public void showProgressDialog() {
        if (progressDialog != null) {
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
        } else {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getString(R.string.processing_msg));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showMessage(String msg) {
    }
}