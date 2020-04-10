package com.core.app.util;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.core.app.R;
import com.vansuita.pickimage.bean.PickResult;

import java.io.File;

import timber.log.Timber;

public class PicUtil {

    public static Uri parseResult(Context context, PickResult result, boolean checkSize, int maxSize) {
        if (result != null) {
            if (!TextUtils.isEmpty(result.getPath())) {

                Uri uri = Uri.parse(result.getPath());

                Timber.d("Uri: %s", uri);

                File file = new File(uri.toString());

                if (file.exists()) {
                    long fileSizeInBytes = file.length();
                    long fileSizeInMB = fileSizeInBytes / 1048576;

                    Timber.d("File size: " + fileSizeInBytes / 1024 + " KB");

                    if (checkSize && fileSizeInMB >= maxSize) {
                        AlertUtil.showToast(context, context.getString(R.string.file_upload_size_error));
                        return null;
                    }

                    return uri;
                } else {
                    Timber.d("File doesn't exist!");
                    AlertUtil.showToast(context, "File doesn't exist!");
                }
            } else {
                if (result.getError() != null) {
                    AlertUtil.showToast(context, result.getError().getMessage());
                } else {
                    AlertUtil.showToast(context, context.getString(R.string.empty_filepath_msg));
                }
            }
        } else {
            AlertUtil.showToast(context, context.getString(R.string.unknown_error_msg));
        }
        return null;
    }
}