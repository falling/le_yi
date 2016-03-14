package com.example.falling.leyi.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.falling.leyi.R;
import com.example.falling.leyi.dao.WordInfo;
import com.example.falling.leyi.dbmanager.sqldic;
import com.example.falling.leyi.dbmanager.sqlglo;
import com.example.falling.leyi.internet.Internet;
import com.example.falling.leyi.internet.SearchWord;
import com.example.falling.leyi.swipeback.BaseActivity;
import com.example.falling.leyi.swipeback.SwipeBackLayout;

/**
 * Created by falling on 2015/11/4.
 */
public class Search  extends BaseActivity {
    private Button btn ;
    private EditText word;
    private TextView tv ;
    private TextView add;
    private String WordToSearch;
    private GridLayout gridLayout;
    private TextView psE;
    private TextView psA;

    private String result;
    private WordInfo WordtoInsert;
    private SwipeBackLayout mSwipeBackLayout;
    String[] listValues;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle("查单词");

        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);


        btn = (Button) findViewById(R.id.Search_button);
        word = (EditText) findViewById(R.id.WordText);
        add = (TextView) findViewById(R.id.textView_add);
        gridLayout=(GridLayout) findViewById(R.id.Grid);
        psE =(TextView)findViewById(R.id.Main_psE);
        psA =(TextView)findViewById(R.id.Main_psA);

        gridLayout.setVisibility(View.INVISIBLE);
        add.setVisibility(View.INVISIBLE);

        add.setOnClickListener(new MyListener());
        btn.setOnClickListener(new MyListener());

        String SearchWord =  getIntent().getStringExtra("searchWord");
        if(SearchWord!=null){
            MyTask mTask = new MyTask();
            mTask.execute(SearchWord);
            word.setText(SearchWord);
        }

    }


    private class MyListener implements View.OnClickListener{
            @Override
            public void onClick(View view) {
                if(view.getId()==btn.getId()) {
                    WordToSearch = word.getText().toString();
                    if (WordToSearch.equals("")) {
                        Snackbar.make(view, "请输入内容", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();


                    }else if(!Internet.isNetworkConnected(Search.this)){
                        Snackbar.make(view, "网络未连接，当前为本地查词", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        WordtoInsert= sqldic.SerchByWord(WordToSearch);
                        tv = (TextView) findViewById(R.id.textView_explain);
                        tv.setText(WordtoInsert.toStringFromDB());

                        psA.setText("");
                        psE.setText("");
                        add.setVisibility(View.VISIBLE);
                        gridLayout.setVisibility(View.INVISIBLE);

                    }else {
                        MyTask mTask = new MyTask();
                        mTask.execute(WordToSearch);
                    }
                }
                else if(view.getId()==add.getId()){
                    listValues = sqlglo.getgloList();
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("选择生词本")
                            .setIcon(android.R.drawable.ic_menu_info_details)
                            .setSingleChoiceItems(
                                    listValues, -1,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            sqlglo.insert(WordtoInsert,listValues[which]);
                                            dialog.dismiss();
                                        }
                                    })
                            .setNegativeButton("取消", null).show();
                }
        }
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
            if(result!=null) {
                WordtoInsert = result;
                tv = (TextView) findViewById(R.id.textView_explain);
                tv.setText(result.toString());
                psA.setText(result.getUs_phonetic());
                psE.setText(result.getUk_phonetic());
                add.setVisibility(View.VISIBLE);
                gridLayout.setVisibility(View.VISIBLE);
            }
        }
        //onCancelled方法用于在取消执行中的任务时更改UI
        @Override
        protected void onCancelled() {

        }
    }



}