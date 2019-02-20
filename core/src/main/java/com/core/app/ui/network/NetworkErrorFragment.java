package com.core.app.ui.network;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.core.app.R;
import com.core.app.ui.base.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NetworkErrorFragment extends BaseFragment {

    public static NetworkErrorFragment newInstance() {
        return new NetworkErrorFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_network_error, container, false);
    }
}