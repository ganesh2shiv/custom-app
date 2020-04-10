package com.core.app.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.core.app.R;
import com.jakewharton.processphoenix.ProcessPhoenix;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;
import java.util.Random;

import static android.content.Context.CLIPBOARD_SERVICE;

public class Util {

    private Util() {
    }

    public static byte[] convertHexToBytes(String hexString) {
        char[] hex = hexString.toCharArray();
        int length = hex.length / 2;
        byte[] rawData = new byte[length];
        for (int i = 0; i < length; i++) {
            int high = Character.digit(hex[i * 2], 16);
            int low = Character.digit(hex[i * 2 + 1], 16);
            int value = (high << 4) | low;
            if (value > 127) {
                value -= 256;
            }
            rawData[i] = (byte) value;
        }
        return rawData;
    }

    public static float getRandomFloat(float min, float max) {
        Random random = new Random();
        return random.nextFloat() * (max - min) + min;
    }

    public static String getDatetime() {
        return DateTimeFormatter.ofPattern("yyMMddHHmmss").format(LocalDateTime.now());
    }

    public static String getDatetime(String format) {
        return DateTimeFormatter.ofPattern(format).format(LocalDateTime.now());
    }

    public static void showSoftKeyboard(final View view) {
        if (view.requestFocus()) {
            final InputMethodManager manager =
                    (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (manager != null) {
                manager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        }
    }

    public static void hideSoftKeyboard(final View view) {
        view.clearFocus();
        final InputMethodManager manager =
                (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void copyToClipboard(Context context, String label, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, text);
        clipboard.setPrimaryClip(clip);
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

    public static boolean isFormEmpty(List<EditText> fields) {
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