package com.example.falling.leyi.dbmanager;

import android.content.Context;

import com.example.falling.leyi.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by falling on 2015/10/30.
 */
public class DBManager {

    private final String DATABASE_PATH = "/data/data/com.example.falling.leyi/databases/";
    private final String DATABASE_FILENAME = "dic.db";
    private String rootDirectory = "/data/data/com.example.falling.leyi/databases/";

    public  void  createDatabase(Context context) throws IOException {
        try {
            String databaseFilename = DATABASE_PATH + DATABASE_FILENAME;
            File dir = new File(rootDirectory);
            // 如果目录不存在，创建这个目录
            if (!dir.exists())
                dir.mkdir();

            //目录中不存在 .db文件，则从res\raw目录中复制这个文件到该目录
            if (!(new File(databaseFilename)).exists()) {
                // 获得封装.db文件的InputStream对象
                InputStream is = context.getResources().openRawResource(R.raw.dic);
                FileOutputStream fos = new FileOutputStream(databaseFilename);
                byte[] buffer = new byte[7168];
                int count = 0;
                // 开始复制.db文件
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
        } catch (Exception e) {
        }
    }
}
