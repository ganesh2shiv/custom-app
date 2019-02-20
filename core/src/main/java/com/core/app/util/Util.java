package com.core.app.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.core.app.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.textfield.TextInputEditText;
import com.jakewharton.processphoenix.ProcessPhoenix;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import timber.log.Timber;

public class Util {

    private Util() {
    }

    public static void showSoftKeyboard(final Context context, final EditText editText) {
        editText.requestFocus();
        new Handler().postDelayed(() -> {
            final InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (manager != null) {
                manager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 0);
    }

    public static void hideSoftKeyboard(final Context context, final EditText editText) {
        editText.clearFocus();
        final InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    public static boolean isGooglePlayServicesAvailable(AppCompatActivity activity, int requestCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(activity.getApplicationContext());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(activity, resultCode, requestCode)
                        .show();
            } else {
                Timber.i("Google play services are not available!");
                AlertUtil.showToast(activity.getApplicationContext(), activity.getString(R.string.fcm_not_supported_msg));
                activity.finish();
            }
            return false;
        }
        return true;
    }

    public static boolean handleUrl(Context context, String url) {
        if (url.startsWith("tel:")) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(url));
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
                return true;
            }
        } else if (url.startsWith("mailto:")) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse(url));
            intent.putExtra(Intent.EXTRA_EMAIL, Uri.parse(url));
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
                return true;
            }
        } else if (url.startsWith("http")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
                return true;
            }
        }
        return false;
    }

    public static boolean isPermissionGranted(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isFormEmpty(List<TextInputEditText> fields) {
        for (int i = 0; i < fields.size(); i++) {
            EditText editText = fields.get(i);
            if (!TextUtils.isEmpty(editText.getText())) {
                return false;
            }
        }
        return true;
    }

    public static void showRelaunchApplicationDialog(Context context) {
        AlertUtil.showActionAlertDialog(context, context.getString(R.string.app_restart_msg),
                context.getString(R.string.btn_no), context.getString(R.string.btn_yes), (dialog, which) -> {
                    dialog.cancel();
                    ProcessPhoenix.triggerRebirth(context);
                });
    }
}