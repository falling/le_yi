package com.example.falling.leyi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.falling.leyi.R;
import com.example.falling.leyi.dbmanager.sqlglo;
import com.example.falling.leyi.slideCutListView.SlideCutListView;
import com.example.falling.leyi.slideCutListView.SlideCutListView.RemoveListener;
import com.example.falling.leyi.swipeback.BaseActivity;
import com.example.falling.leyi.swipeback.SwipeBackLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by falling on 2015/11/8.
 */
public class Glossary_list extends BaseActivity implements RemoveListener {
    private SlideCutListView slideCutListView = null;
    private List<String> list = new ArrayList<>();
    private ArrayAdapter<String> mArrayAdapter = null;
    private  List<String> sourse;
    private SwipeBackLayout mSwipeBackLayout;
    private FloatingActionButton fab;
    private int count=0;
    private String ListName;
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.glossary_listview);

        slideCutListView = (SlideCutListView) findViewById(R.id.slideCutListView);
        slideCutListView.setRemoveListener(this);


        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

        ListName =  getIntent().getStringExtra("listName");

        loadListView(ListName,count);

        slideCutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String[] wordInfo = sourse.get(position).split("\n");

                Intent i = new Intent(view.getContext(), Search.class);
                i.putExtra("searchWord",wordInfo[1]);
                startActivity(i);
            }
        });


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(getDrawable(R.drawable.sort_abc));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count=(count+1)%4;
                if(count==0) {
                    fab.setImageDrawable(getDrawable(R.drawable.sort_abc));
                } else if(count == 1){
                    fab.setImageDrawable(getDrawable(R.drawable.sort_zyx));
                }else if(count ==2){
                    fab.setImageDrawable(getDrawable(R.drawable.sort_time1));
                }else if(count == 3){
                    fab.setImageDrawable(getDrawable(R.drawable.sort_time2));
                }
                loadListView(ListName,count);
            }
        });
    }

    private void loadListView(String listName,int value) {
        list.clear();
        sourse = sqlglo.getdataByName(listName,value);
        mArrayAdapter= new ArrayAdapter<>(this, R.layout.glossary_list_item,R.id.list_item, sourse);
        slideCutListView.setAdapter(mArrayAdapter);
    }

    @Override
    public void removeItem(SlideCutListView.RemoveDirection direction, int position) {
        String result = mArrayAdapter.getItem(position);
        String[] re = result.split("\n");
        mArrayAdapter.remove(mArrayAdapter.getItem(position));
        sqlglo.deleteWord(re[1]);
    }
}
