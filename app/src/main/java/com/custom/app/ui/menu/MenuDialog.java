package com.custom.app.ui.menu;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.custom.app.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MenuDialog extends BottomSheetDialogFragment {

    private Unbinder unbinder;
    private Callback callback;

    @OnClick(R.id.tv_settings)
    void settings() {
        dismiss();

        if (callback != null) {
            callback.showSettingScreen();
        }
    }

    @OnClick(R.id.tv_logout)
    void logout() {
        dismiss();

        if (callback != null) {
            callback.showLogoutDialog();
        }
    }

    public static MenuDialog newInstance() {
        return new MenuDialog();
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (Callback) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_menu, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        callback = null;
        super.onDetach();
    }

    public interface Callback {

        void showSettingScreen();

        void showLogoutDialog();

    }
}