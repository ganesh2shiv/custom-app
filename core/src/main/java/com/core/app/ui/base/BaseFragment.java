package com.core.app.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.core.app.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment implements BaseView {

    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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

    protected void fragmentTransition(Fragment fragment, String tag) {
        if (!fragment.isVisible() && getFragmentManager() != null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.layout_main, fragment, tag)
                    .addToBackStack(null)
                    .commit();
        }
    }
}