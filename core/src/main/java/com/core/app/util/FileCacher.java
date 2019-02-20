package com.core.app.util;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class FileCacher<T> {

    private Context context;

    public FileCacher(Context context) {
        this.context = context;
    }

    public void writeCache(String fileName, String filePath) throws IOException {
        Timber.d("Writing cache...");
        if (hasCache(fileName)) {
            Timber.d("Clearing existing cache...");
            clearCache(fileName);
        }

        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        fos.write(FileUtil.convertFileToByteArray(filePath));
        fos.flush();
        fos.close();
    }

    public void writeCache(String fileName, Bitmap bitmap) throws IOException {
        Timber.d("Writing cache...");
        if (hasCache(fileName)) {
            Timber.d("Clearing existing cache...");
            clearCache(fileName);
        }

        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fos);
        fos.flush();
        fos.close();
    }

    public void appendOrWriteCache(String fileName, T object) throws IOException {
        FileOutputStream fos;
        ObjectOutputStream oos;
        if (hasCache(fileName)) {
            fos = context.openFileOutput(fileName, Context.MODE_APPEND);
            oos = new ObjectOutputStream(fos) {
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            };
        } else {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
        }
        oos.writeObject(object);
        oos.flush();
        oos.close();

        fos.close();
    }

    public T readCache(String fileName) throws IOException {
        FileInputStream fis = context.openFileInput(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object object = null;
        try {
            object = ois.readObject();
        } catch (ClassNotFoundException e) {
            Timber.e(e);
        }
        fis.close();
        return (T) object;
    }

    public List<T> getAllCaches(String fileName) throws IOException {
        FileInputStream fis = context.openFileInput(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object object = null;
        List<T> cacheList = new ArrayList<T>();
        try {
            while ((object = ois.readObject()) != null) {
                cacheList.add((T) object);
            }
        } catch (ClassNotFoundException e) {
            Timber.e(e);
        } catch (EOFException e) {
            Timber.e(e);
            fis.close();
        }

        return cacheList;
    }

    public boolean clearCache(String fileName) throws IOException {
        boolean success = context.deleteFile(fileName);
        if (success) {
            return true;
        }
        return false;
    }

    public String getFilePath(String fileName) {
        return context.getFileStreamPath(fileName).getPath();
    }

    public boolean hasCache(String fileName) {
        File file = context.getFileStreamPath(fileName);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    public long getSize(String fileName) {
        long size = -1;
        File file = context.getFileStreamPath(fileName);

        if (file.exists()) {
            size = file.length();
        }

        return size;
    }
}