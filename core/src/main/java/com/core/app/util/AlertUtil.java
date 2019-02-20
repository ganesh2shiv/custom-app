package com.core.app.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.core.app.R;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

public class AlertUtil {

    private AlertUtil() {
    }

    public static void showToast(Context context, CharSequence msg) {
//        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        showCustomToast(context, msg, Toast.LENGTH_LONG);
    }

    public static void showToast(Context context, CharSequence msg, int duration) {
//        Toast.makeText(context, msg, duration).show();
        showCustomToast(context, msg, duration);
    }

    public static void showCustomToast(Context context, CharSequence msg, int duration) {
        Toast toast = Toast.makeText(context, msg, duration);
        View rootView = toast.getView();

        int color = ContextCompat.getColor(context, R.color.dark_grey);
        rootView.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);

        TextView textView = rootView.findViewById(android.R.id.message);
        textView.setTextColor(ContextCompat.getColor(context, R.color.white));

        toast.show();
    }

    public static void showSnackBar(View view, CharSequence msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

    public static void showSnackBar(View view, CharSequence msg, int duration) {
        Snackbar.make(view, msg, duration).show();
    }

    public static void showIndefiniteSnackBar(View view, CharSequence msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE).show();
    }

    public static void showCustomSnackBar(View view, CharSequence msg, @IdRes int anchorViewId, int duration) {
        Snackbar snackbar = Snackbar.make(view, msg, duration);
        snackbar.setAnchorView(anchorViewId);
        View rootView = snackbar.getView();
        TextView textView = rootView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setMaxLines(3);
        snackbar.show();
    }

    public static void showActionSnackBar(View view, CharSequence msg, int duration, CharSequence action,
                                          View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(view, msg, duration);
        View rootView = snackbar.getView();
        Button button = rootView.findViewById(com.google.android.material.R.id.snackbar_action);
        button.setTextColor(ContextCompat.getColor(view.getContext(), R.color.white));

        snackbar.setAction(action, listener).show();
    }

    public static void showAlertDialog(Context context, CharSequence title, CharSequence msg, CharSequence actionName) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.CustomDialog);
        alert.setTitle(title);
        alert.setMessage(msg);
        alert.setCancelable(true);
        alert.setPositiveButton(actionName, (dialog, which) -> dialog.dismiss());
        alert.show();
    }

    public static void showActionAlertDialog(Context context, CharSequence msg,
                                             CharSequence negativeAction, CharSequence positiveAction,
                                             DialogInterface.OnClickListener listener) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.CustomDialog);
        alert.setMessage(msg);
        alert.setCancelable(true);
        alert.setPositiveButton(positiveAction, listener);
        alert.setNegativeButton(negativeAction, (dialog, which) -> dialog.dismiss());
        alert.show();
    }

    public static void showActionAlertDialog(Context context, CharSequence title, CharSequence msg,
                                             CharSequence negativeAction, CharSequence positiveAction,
                                             OnClickListener listener) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.CustomDialog);
        alert.setTitle(title);
        alert.setMessage(msg);
        alert.setCancelable(true);
        alert.setPositiveButton(positiveAction, listener);
        alert.setNegativeButton(negativeAction, (dialog, which) -> dialog.dismiss());
        alert.show();
    }

    public static void showActionViewDialog(Context context, View view,
                                            CharSequence negativeAction, CharSequence positiveAction,
                                            OnClickListener listener) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.CustomDialog);
        alert.setView(view);
        alert.setCancelable(true);
        alert.setPositiveButton(positiveAction, listener);
        alert.setNegativeButton(negativeAction, (dialog, which) -> dialog.dismiss());
        alert.show();
    }
}