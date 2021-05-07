package com.base.app.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.base.app.R;

public abstract class BaseFragment extends Fragment implements BaseView {

    private ProgressDialog progressDialog;

    @Override
    public void close() {
        if (getFragmentManager() != null) {
            getFragmentManager().popBackStackImmediate();
        }
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
            progressDialog.setCancelable(true);
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
    public void showMessage(@Nullable String msg) {
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            close();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void fragmentTransition(Fragment fragment, String tag) {
        if (!fragment.isVisible() && getFragmentManager() != null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.layout_main, fragment, tag)
//                  .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    }
}