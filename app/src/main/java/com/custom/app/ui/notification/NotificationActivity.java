package com.custom.app.ui.notification;

import android.os.Bundle;
import android.view.View;

import com.core.app.ui.base.BaseActivity;
import com.core.app.ui.custom.EmptyRecyclerView;
import com.core.app.util.AlertUtil;
import com.custom.app.CustomApp;
import com.custom.app.R;
import com.custom.app.data.model.notification.NotificationItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends BaseActivity implements NotificationView {

    private NotificationController controller;
    private List<NotificationItem> notifications = new ArrayList<>();

    @Inject
    NotificationPresenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;

    @BindView(R.id.emptyView)
    View emptyView;

    @BindView(R.id.recyclerView)
    EmptyRecyclerView recyclerView;

    @BindView(R.id.fab_clear)
    FloatingActionButton fabClear;

    @Override
    @OnClick(R.id.fab_clear)
    public void clearNotifications() {
        AlertUtil.showActionAlertDialog(context(), getString(R.string.clear_notification_msg),
                getString(R.string.btn_no), getString(R.string.btn_yes), (dialog, which) -> presenter.clearNotifications());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        ((CustomApp) getApplication()).getHomeComponent().inject(this);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            toolbar.setTitle(getString(R.string.title_notifications));
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(context());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(context(), DividerItemDecoration.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);

        controller = new NotificationController(this);
        recyclerView.setAdapter(controller.getAdapter());

        swipeLayout.setOnRefreshListener(() -> presenter.fetchNotifications());

        presenter.setView(this);
        presenter.fetchNotifications();
    }

    @Override
    public void showEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        fabClear.hide();
    }

    @Override
    public void showProgressBar() {
        swipeLayout.setRefreshing(true);
        emptyView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        fabClear.hide();
        notifications.clear();
    }

    @Override
    public void hideProgressBar() {
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(String msg) {
        AlertUtil.showSnackBar(swipeLayout, msg);
    }

    @Override
    public void showList(List<NotificationItem> newNotifications) {
        notifications.addAll(newNotifications);
        controller.setList(notifications);
        emptyView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        fabClear.show();
    }

    @Override
    public void onItemClicked(NotificationItem notification) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.destroy();
    }
}