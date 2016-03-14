package com.example.falling.leyi.ui;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.falling.leyi.R;
import com.example.falling.leyi.dao.EverydayInfo;
import com.example.falling.leyi.internet.EverydaySen;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by falling on 2015/11/16.
 */
public class Everyday   extends Fragment {

    private TextView content;
    private TextView dateline;
    private TextView note;
    private TextView translation;
    private ImageView picture;
    private  Bitmap bmImg;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.everyday, container, false);

        content = (TextView) view.findViewById(R.id.content);
        dateline = (TextView) view.findViewById(R.id.dateline);
        note = (TextView) view.findViewById(R.id.note);
        translation = (TextView) view.findViewById(R.id.translation);
        picture = (ImageView) view.findViewById(R.id.picture);

        return view;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyTask mTask = new MyTask();
        mTask.execute();
    }

    private class MyTask extends AsyncTask<String, Integer, EverydayInfo> {
        //onPreExecute方法用于在执行后台任务前做一些UI操作
        @Override
        protected void onPreExecute() {
        }

        //doInBackground方法内部执行后台任务,不可在此方法内修改UI
        @Override
        protected EverydayInfo doInBackground(String... params) {
            EverydayInfo e = EverydaySen.request();
            try {
                URL myFileUrl = new URL( e.getPicture());
                HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bmImg = BitmapFactory.decodeStream(is);
                is.close();
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            return e;
        }

        //onProgressUpdate方法用于更新进度信息
        @Override
        protected void onProgressUpdate(Integer... progresses) {
        }

        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
        @Override
        protected void onPostExecute(EverydayInfo result) {
            if(result!=null) {
                dateline.setText(result.getDateline());
                content.setText(result.getContent());
                note.setText(result.getNote());
                translation.setText(result.getTranslation());

                picture.setImageBitmap(bmImg);
            }
        }
        //onCancelled方法用于在取消执行中的任务时更改UI
        @Override
        protected void onCancelled() {

        }
    }


}
