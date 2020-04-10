package com.custom.app.ui.setting;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.base.app.ui.base.BaseActivity;
import com.custom.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

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