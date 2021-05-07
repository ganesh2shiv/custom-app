package com.custom.app.ui.setting;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;

import com.base.app.ui.base.BaseActivity;
import com.custom.app.R;
import com.custom.app.databinding.ActivitySettingBinding;

import dagger.hilt.android.AndroidEntryPoint;
import kotlinx.coroutines.InternalCoroutinesApi;

@AndroidEntryPoint
@InternalCoroutinesApi
public class SettingActivity extends BaseActivity {

    private ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.includeAppbar.includeToolbar.toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_main, SettingFragment.newInstance())
                .commit();
    }
}