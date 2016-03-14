package com.example.falling.leyi.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.falling.leyi.R;
import com.example.falling.leyi.dbmanager.sqldic;

import java.util.ArrayList;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout.BGARefreshLayoutDelegate;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;


/**
 * Created by falling on 2015/10/29.
 */
public class Thesaurus extends Fragment implements BGARefreshLayoutDelegate{
    private ListView listView = null;
    private ArrayAdapter<String> mArrayAdapter = null;
    private BGARefreshLayout mRefreshLayout;
    private View view;
    private int startI;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        view = inflater.inflate(R.layout.activity_thesaurus, container, false);
        listView = (ListView) view.findViewById(R.id.thesaurus_list);

        listView.setOnItemClickListener(new MyListener());
        listView.setOnItemLongClickListener(new MyLongListener());

        startI=0;

        mArrayAdapter= new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, sqldic.getdata(startI));
        listView.setAdapter(mArrayAdapter);
        initRefreshLayout();

        return view;
    }

    private void initRefreshLayout() {
        mRefreshLayout = (BGARefreshLayout) view.findViewById(R.id.rl_modulename_refresh);
        // 为BGARefreshLayout设置代理
        mRefreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(view.getContext(), true);
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mRefreshLayout.endRefreshing();

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {

        startI+=100;

        ArrayList<String> L =sqldic.getdata(startI);
        mRefreshLayout.endLoadingMore();

        for(int i =0;i<100;i++)
            mArrayAdapter.add(L.get(i));
        mArrayAdapter.notifyDataSetChanged();
        return false;
    }


    // 通过代码方式控制进入正在刷新状态。应用场景：某些应用在activity的onStart方法中调用，自动进入正在刷新状态获取最新数据
    public void beginRefreshing() {
        mRefreshLayout.beginRefreshing();
    }

    // 通过代码方式控制进入加载更多状态
    public void beginLoadingMore() {
        mRefreshLayout.beginLoadingMore();
    }


    private class MyListener implements View.OnClickListener, AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String wordInfo =(String) listView.getItemAtPosition(position);
            wordInfo=wordInfo.replaceAll("\n\n\t\t", "");
            String Word[] = wordInfo.split("\n");
            Word[2]=Word[2].replaceAll(" ", "");
            Word[2]=Word[2].replaceAll("\t","");
            Intent i = new Intent(view.getContext(), Search.class);
            i.putExtra("searchWord",Word[2]);
            startActivity(i);
        }

        @Override
        public void onClick(View v) {

        }
    }

    private class MyLongListener implements View.OnLongClickListener, AdapterView.OnItemLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            return false;
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            return true;
        }
    }
}
