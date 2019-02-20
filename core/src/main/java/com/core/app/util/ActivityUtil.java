package com.core.app.util;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

public class ActivityUtil {

    private ActivityUtil() {
    }

    public static void startActivity(AppCompatActivity activity, Class clazz, boolean finish) {
        startActivity(activity, new Intent(activity, clazz), finish);
    }

    public static void startActivity(AppCompatActivity activity, Intent intent, boolean finish) {
        activity.startActivity(intent);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        if (finish) {
            activity.finish();
        }
    }

    public static void startActivity(AppCompatActivity activity, Class clazz,
                                     ActivityOptionsCompat options, boolean finish) {
        startActivity(activity, new Intent(activity, clazz), options, finish);
    }

    public static void startActivity(AppCompatActivity activity, Intent intent,
                                     ActivityOptionsCompat options, boolean finish) {
        activity.startActivity(intent, options.toBundle());
        if (finish) {
            activity.finish();
        }
    }

    public static void startActivityResult(AppCompatActivity activity, Class clazz,
                                           int requestCode, boolean finish) {
        startActivityResult(activity, new Intent(activity, clazz), requestCode, finish);
    }

    public static void startActivityResult(AppCompatActivity activity, Intent intent,
                                           int requestCode, boolean finish) {
        activity.startActivityForResult(intent, requestCode);
        if (finish) {
            activity.finish();
        }
    }
}