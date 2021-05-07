package com.base.app.ui.base;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.base.app.databinding.FragmentWebviewBinding;
import com.core.app.util.AlertUtil;

import static com.core.app.util.Constants.URL;

public abstract class BaseWeb extends BaseFragment {

    private FragmentWebviewBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentWebviewBinding.inflate(inflater, container, false);

        binding.webView.setWebViewClient(new CustomWebViewClient(binding.progressBar));
        if (getArguments() != null) {
            binding.webView.loadUrl(getArguments().getString(URL));
        }

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
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