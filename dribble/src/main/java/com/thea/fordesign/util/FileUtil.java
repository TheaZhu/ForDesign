package com.thea.fordesign.util;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * @author Thea (theazhu0321@gmail.com)
 */
public class FileUtil {
    public static final String TAG = FileUtil.class.getSimpleName();
    public static final String BASE_PATH = Environment.getExternalStorageDirectory() +
            "/fordesign/";
    public static final String DRIBBBLE_IMAGE_DIRECTORY = BASE_PATH + "images/";

    public static File createNewFile(String name) {
        File dir1 = new File(BASE_PATH);
        if (dir1.exists() && !dir1.isDirectory())
            dir1.delete();
        if (!dir1.exists())
            dir1.mkdir();

        File dir2 = new File(DRIBBBLE_IMAGE_DIRECTORY);
        if (dir2.exists() && !dir2.isDirectory())
            dir2.delete();
        if (!dir2.exists())
            dir2.mkdir();

        File file = new File(DRIBBBLE_IMAGE_DIRECTORY + name);
        if (file.exists())
            file.delete();

        return file;
    }

    public static boolean saveFileToStore(ResponseBody body, String name) {
        try {
            File file = createNewFile(name);
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;
                    LogUtil.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
