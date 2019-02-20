package com.core.app.http;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.core.app.R;
import com.core.app.data.prefs.StringPreference;
import com.core.app.util.AlertUtil;
import com.core.app.util.Util;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.HttpUrl;
import timber.log.Timber;

public class ApiManager {

    @Inject
    @Named("baseUrl")
    StringPreference apiEndpointPref;

    ApiManager(StringPreference apiEndpointPref) {
        this.apiEndpointPref = apiEndpointPref;
    }

    public void setApiEndpoint(String apiEndpoint) {
        Timber.d("Setting network endpoint to %s", apiEndpoint);
        apiEndpointPref.set(apiEndpoint);
    }

    public ApiEndpoint getApiEndpoint() {
        return ApiEndpoint.from(apiEndpointPref.get());
    }

    public void showCustomEndpointDialog(Context context) {
        View customView = LayoutInflater.from(context).inflate(R.layout.layout_custom_endpoint, null);
        final TextInputEditText etUrl = customView.findViewById(R.id.et_url);
        etUrl.setText(getApiEndpoint().url);

        AlertUtil.showActionViewDialog(context, customView, context.getString(R.string.btn_cancel),
                context.getString(R.string.btn_save), (dialog, arg) -> {
                    String baseUrl = etUrl.getText().toString();
                    if (isValidBaseUrl(baseUrl)) {
                        dialog.dismiss();
                        setApiEndpoint(baseUrl);
                        Util.showRelaunchApplicationDialog(context);
                    }
                });
    }

    private boolean isValidBaseUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            HttpUrl httpUrl = HttpUrl.parse(url);
            if (httpUrl != null) {
                List<String> pathSegments = httpUrl.pathSegments();
                return "".equals(pathSegments.get(pathSegments.size() - 1));
            }
        }
        return false;
    }
}