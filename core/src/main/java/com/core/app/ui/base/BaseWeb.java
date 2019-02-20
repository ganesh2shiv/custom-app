package com.core.app.ui.base;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.core.app.R;
import com.core.app.R2;
import com.core.app.util.AlertUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.core.app.util.Constants.URL;

public abstract class BaseWeb extends BaseFragment {

    private Unbinder unbinder;

    @BindView(R2.id.web_view)
    WebView webView;

    @BindView(R2.id.progress_bar)
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_webview, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        webView.setWebViewClient(new CustomWebViewClient(progressBar));
        if (getArguments() != null) {
            webView.loadUrl(getArguments().getString(URL));
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }

    private class CustomWebViewClient extends WebViewClient {

        private ProgressBar progressBar;

        CustomWebViewClient(ProgressBar progressBar) {
            this.progressBar = progressBar;
        }

        @Override
        public void onPageStarted(WebView webView, String url, Bitmap favicon) {
            super.onPageStarted(webView, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView webView, String url) {
            super.onPageFinished(webView, url);
            progressBar.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onReceivedError(WebView webView, int errorCode, String desc, String failingUrl) {
            progressBar.setVisibility(View.GONE);
            webView.setVisibility(View.INVISIBLE);

            AlertUtil.showSnackBar(webView, desc);
        }
    }
}