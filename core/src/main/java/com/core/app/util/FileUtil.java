package com.core.app.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import id.zelory.compressor.Compressor;
import timber.log.Timber;

public class FileUtil {

    private FileUtil() {
    }

    public static String convertFileToBase64EncodedString(String filePath) {
        try {
            return Base64.encodeToString(convertFileToByteArray(filePath), Base64.DEFAULT);
        } catch (Exception e) {
            Timber.e(e);
        }
        return "";
    }

    public static byte[] convertFileToByteArray(String filePath) {
        File file = new File(filePath.replace("file:", ""));
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            try {
                for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                    bos.write(buf, 0, readNum);
                }
            } catch (IOException e) {
                Timber.e(e);
            }
            byte[] byteArray = bos.toByteArray();
            return byteArray;
        } catch (Exception e) {
            Timber.e(e);
            return new byte[]{};
        }
    }

    public static String getFileNameWithoutExt(String filePath) {
        String fileNameWithExt = new File(filePath).getName();
        int pos = fileNameWithExt.lastIndexOf(".");
        return pos > 0 ? fileNameWithExt.substring(0, pos) : fileNameWithExt;
    }

    public static String getFileExt(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1);
    }

    public static String getFileSizeInMb(String filePath) {
        long fileSizeInBytes = new File(filePath).length();
        long fileSizeInKB = fileSizeInBytes / 1024;
        long fileSizeInMB = fileSizeInKB / 1024;
        return String.valueOf(fileSizeInMB);
    }

    public static Uri compressFile(Context context, File actualFile, int quality) throws IOException {
        Timber.d("Compressing file...");

        File compressedFile = new Compressor(context)
                .setQuality(quality)
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                .compressToFile(actualFile);

        return Uri.parse(compressedFile.getPath());
    }

    public static void storeFileInCache(String fileName, String filePath, FileCacher<File> fileCacher) {
        Timber.d("Storing file in cache...");
        try {
            fileCacher.writeCache(fileName, filePath);
        } catch (IOException e) {
            Timber.e(e);
        }
    }

    public static void storeBitmapInCache(String fileName, Bitmap bitmap, FileCacher<File> fileCacher) {
        Timber.d("Storing file in cache...");
        try {
            fileCacher.writeCache(fileName, bitmap);
        } catch (IOException e) {
            Timber.e(e);
        }
    }

    public static void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    public static void copy(InputStream in, File dst) throws IOException {
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        out.close();
    }

    public static File createTempFile(Context context, String fileName) {
        File cacheDir = context.getCacheDir();
        if (!cacheDir.exists()) {
            cacheDir.mkdir();
        }
        return new File(cacheDir, fileName);
    }
}