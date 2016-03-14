package com.example.falling.leyi.ui;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.falling.leyi.R;
import com.example.falling.leyi.dbmanager.sqlglo;

/**
 * Created by falling on 2015/11/23.
 */
public class Glossary extends Fragment {
    private ListView listView = null;
    private ArrayAdapter<String> mArrayAdapter = null;
    private View view;
    private String clickId;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
            view = inflater.inflate(R.layout.glossary, container, false);

            updateView();

            listView.setOnItemClickListener(new MyListener());
            listView.setOnItemLongClickListener(new MyLongListener());

            return view;
        }

    public void updateView(){
        listView = (ListView) view.findViewById(R.id.glossary_listview);
        mArrayAdapter= new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, sqlglo.getListdata());
        listView.setAdapter(mArrayAdapter);
    }


    private class MyListener implements View.OnClickListener, AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String clickId =(String) listView.getItemAtPosition(position);
            clickId=clickId.replaceAll("\n\n\t\t","");
            Intent i = new Intent(getActivity(), Glossary_list.class);
            i.putExtra("listName",clickId);
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
            clickId =(String) listView.getItemAtPosition(position);
            clickId=clickId.replaceAll("\n\n\t\t","");

            AlertDialog.Builder  alertbBuilder= new AlertDialog.Builder(getActivity());

            alertbBuilder.setTitle("是否删除 " + clickId + " ?")
                    .setIcon(android.R.drawable.ic_menu_info_details)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //确定后要执行的语句
                            if (!clickId.equals("默认")) {
                                sqlglo.deletegloList(clickId);
                                Toast.makeText(getActivity(), "删除成功",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "删除失败，不能删除“默认”生词本",
                                        Toast.LENGTH_SHORT).show();
                            }
                            updateView();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .create();
            alertbBuilder.show();

            return true;
        }
    }
}






