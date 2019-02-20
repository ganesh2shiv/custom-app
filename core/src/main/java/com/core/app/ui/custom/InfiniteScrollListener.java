package com.core.app.ui.custom;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class InfiniteScrollListener extends RecyclerView.OnScrollListener {

    private final int maxItemsPerRequest;
    private final LinearLayoutManager layoutManager;

    protected InfiniteScrollListener(int maxItemsPerRequest, LinearLayoutManager layoutManager) {
        checkIfPositive(maxItemsPerRequest, "maxItemsPerRequest <= 0");
        checkNotNull(layoutManager, "layoutManager == null");
        this.maxItemsPerRequest = maxItemsPerRequest;
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (canLoadMoreItems()) {
            onScrolledToEnd(layoutManager.findFirstVisibleItemPosition());
        }
    }

    protected void refreshView(RecyclerView view, RecyclerView.Adapter adapter, int position) {
        view.setAdapter(adapter);
        view.invalidate();
        view.scrollToPosition(position);
    }

    protected boolean canLoadMoreItems() {
        final int visibleItemsCount = layoutManager.getChildCount();
        final int totalItemsCount = layoutManager.getItemCount();
        final int pastVisibleItemsCount = layoutManager.findFirstVisibleItemPosition();
        final boolean lastItemShown = visibleItemsCount + pastVisibleItemsCount >= totalItemsCount;
        return lastItemShown && totalItemsCount >= maxItemsPerRequest;
    }

    public abstract void onScrolledToEnd(final int firstVisibleItemPosition);

    private void checkNotNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    private void checkIfPositive(int number, String message) {
        if (number <= 0) {
            throw new IllegalArgumentException(message);
        }
    }
}