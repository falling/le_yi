package com.example.falling.leyi.ui;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.falling.leyi.R;
import com.example.falling.leyi.dao.WordInfo;
import com.example.falling.leyi.dbmanager.sqldic;
import com.example.falling.leyi.dbmanager.sqlglo;
import com.example.falling.leyi.internet.SearchWord;

/**
 * Created by falling on 2015/11/2.
 */
public class MainPage  extends Fragment {

    private int id;
    private TextView wordview ;
    private TextView previous;
    private TextView next;
    private TextView collect;
    private View v;
    private WordInfo word;
    private GridLayout gridLayout;
    private TextView psE;
    private TextView psA;

    private TextView EG;
    String[] listValues;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        v =inflater.inflate(R.layout.main_page, container, false);
        return v;
    }
    public void onStart(){
        super.onStart();
        wordview = (TextView) v.findViewById(R.id.main_page_word);
        previous = (TextView) v.findViewById(R.id.previousOne);
        next= (TextView) v.findViewById(R.id.nextOne);
        collect =(TextView) v.findViewById(R.id.collection);
        EG = (TextView) v.findViewById(R.id.main_page_EG);

        gridLayout=(GridLayout) v.findViewById(R.id.Main_Grid);
        psE =(TextView)v.findViewById(R.id.Main_psE);
        psA =(TextView)v.findViewById(R.id.Main_psA);

        gridLayout.setVisibility(View.INVISIBLE);
        gridLayout.bringToFront();

        next.setOnClickListener(new MyListener());

        previous.setOnClickListener(new MyListener());

        collect.setOnClickListener(new MyListener());


        //单词进度
        SharedPreferences progress = getActivity().getSharedPreferences("progress", 0);
        id=progress.getInt("pro",1);
        word = sqldic.SerchByid(id);
        wordview.setText(word.toStringFromDB());
        new MyTask().execute(word.getWord());
    }
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    private class MyTask extends AsyncTask<String, Integer, WordInfo> {
        //onPreExecute方法用于在执行后台任务前做一些UI操作
        @Override
        protected void onPreExecute() {
        }

        //doInBackground方法内部执行后台任务,不可在此方法内修改UI
        @Override
        protected WordInfo doInBackground(String... params) {
            return SearchWord.request(params[0]);
        }

        //onProgressUpdate方法用于更新进度信息
        @Override
        protected void onProgressUpdate(Integer... progresses) {
        }

        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
        @Override
        protected void onPostExecute(WordInfo result) {

            if(result==null){
                EG.setText("");
                gridLayout.setVisibility(View.INVISIBLE);
            }
            else {
                psA.setText(result.getUs_phonetic());
                psE.setText(result.getUk_phonetic());
                gridLayout.setVisibility(View.VISIBLE);
                EG.setText(result.toString());
            }

        }
        //onCancelled方法用于在取消执行中的任务时更改UI
        @Override
        protected void onCancelled() {

        }
    }

    class MyListener implements View.OnClickListener {

        public void onClick(View v) {
            // TODO Auto-generated method stub

            if(v.getId() == R.id.nextOne) {

                WordInfo word_t = sqldic.SerchByid(++id);
                if(word_t.getWord().equals("")){
                    Snackbar.make(v, "没有下一个单词", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    id--;
                }else{
                    word = word_t;
                    wordview.setText(word.toStringFromDB());
                    SharedPreferences progress = getActivity().getSharedPreferences("progress", 0);
                    SharedPreferences.Editor editor = progress.edit();
                    editor.putInt("pro", id);
                    editor.commit();

                    MyTask mTask = new MyTask();
                    mTask.execute(word.getWord());
                }
            } else if(v.getId() == R.id.previousOne) {


                WordInfo word_t = sqldic.SerchByid(--id);
                if(word_t.getWord().equals("")){
                    Snackbar.make(v, "没有上一个单词", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    id++;
                }else{
                    word = word_t;
                    wordview.setText(word.toStringFromDB());
                    SharedPreferences progress = getActivity().getSharedPreferences("progress", 0);
                    SharedPreferences.Editor editor = progress.edit();
                    editor.putInt("pro", id);
                    editor.commit();


                    MyTask mTask = new MyTask();
                    mTask.execute(word.getWord());
                }
            }else if(v.getId() == R.id.collection){
//                sqlglo.insert(word);
//                Snackbar.make(v, "已添加", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                listValues = sqlglo.getgloList();
                new AlertDialog.Builder(getActivity())
                        .setTitle("选择生词本")
                        .setIcon(android.R.drawable.ic_menu_info_details)
                        .setSingleChoiceItems(
                                listValues, -1,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        sqlglo.insert(word,listValues[which]);
                                        dialog.dismiss();
                                    }
                                })
                        .setNegativeButton("取消", null).show();
            }
        }
    }
}
