package com.example.falling.leyi.dbmanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.falling.leyi.dao.WordInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by falling on 2015/11/8.
 */
public class sqlglo {
    private static String DIC = "/data/data/com.example.falling.leyi/databases/dic.db";

    public static List<String> getdata() {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DIC, null);
        Cursor cursor = db.rawQuery("select * from glossary_item order by _id desc limit 0,100 ", null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String word = cursor.getString(1);
            String explain = cursor.getString(2);

            String re = id +"\n " + word +"\n " +explain;
            list.add(re);

        }
        cursor.close();
        db.close();
        return list;
    }

    public static List<String> getdataByName(String listName,int value) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DIC, null);


        //不知道什么鬼原因， order by ? desc 这种写法不会排序返回！！！！！！坑了我俩小时！！！！！！！！！！！！！！！！！！！！！！！！！！！
        String sql_word_desc = "select * from glossary_item a, glossary b where a.listId=b.id and b.name = ?  order by word desc";

        String sql_word_asc = "select * from glossary_item a, glossary b where a.listId=b.id and b.name = ?  order by word asc";

        String sql_time_desc = "select * from glossary_item a, glossary b where a.listId=b.id and b.name = ?  order by a.id desc";

        String sql_time_asc = "select * from glossary_item a, glossary b where a.listId=b.id and b.name = ?  order by a.id asc";

        Cursor cursor;

        if(value==0) {
            cursor = db.rawQuery(sql_word_asc, new String[]{listName});
        }else if(value==1){
            cursor = db.rawQuery(sql_word_desc, new String[]{ listName});
        }else if(value==2){
            cursor = db.rawQuery(sql_time_asc, new String[]{ listName});
        }else{
            cursor = db.rawQuery(sql_time_desc, new String[]{ listName});
        }

        int id = 0;
        while (cursor.moveToNext()) {
            id++;
            String word = cursor.getString(1);
            String explain = cursor.getString(2);

            String re = id +"\n" + word +"\n" +explain;
            list.add(re);

        }
        cursor.close();
        db.close();
        return list;
    }

    public static WordInfo SerchByid(int id){
        WordInfo word= new WordInfo();
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DIC, null);
        Cursor cursor = db.rawQuery("select * from glossary_item where _id=?", new String[]{String.valueOf(id)});
        if(cursor.moveToNext()){
            word.setWord(cursor.getString(1));
            word.setExplains(cursor.getString(2));
        }
        cursor.close();
        db.close();
        return word;
    }

    public static void insert(WordInfo word,String which){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DIC, null);
        int gloId = 0;
        Cursor cursor = db.rawQuery("select id from glossary where name = ?", new String[]{which});
        if(cursor.moveToNext()){
            gloId=cursor.getInt(0);
        }

        db.execSQL("insert into glossary_item (word,explain,listId)values(?,?,?)", new Object[]{word.getWord(), word.getExplains(),String.valueOf(gloId)});
        db.close();
    }

    public static void deleteWord(String word) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DIC, null);
        db.execSQL("delete from glossary_item where word = ?", new Object[]{word});
        db.close();
    }


    //生词本
    public static List<String> getListdata() {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DIC, null);
        Cursor cursor = db.rawQuery("select * from glossary order by id desc limit 0,100 ", null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);


            String re = "\n\n\t\t" + name + "\n\n\t\t";
            list.add(re);

        }
        cursor.close();
        db.close();
        return list;
    }

    public static String[] getgloList() {
        String[] re;
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DIC, null);
        Cursor cursor = db.rawQuery("select * from glossary order by id desc limit 0,100 ", null);
        re = new String[cursor.getCount()];
        int i =0;
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            re[i++] = name;
        }
        cursor.close();
        db.close();
        return re;
    }

    public static boolean insertglo(String value) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DIC, null);
        int count =0;
        int count1 =0;
        Cursor cursor =  db.rawQuery("select count(*) from glossary", null);
        if(cursor.moveToNext()){
            count = cursor.getInt(0);
        }
        db.execSQL("insert into  glossary (name) values(?)", new String[]{value});
        cursor =  db.rawQuery("select count(*) from glossary", null);
        if(cursor.moveToNext()){
            count1 = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count!=count1;
    }

    public static void deletegloList(String value){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DIC, null);
        db.execSQL("delete from glossary where name =  ?", new String[]{value});
        db.close();

    }
}
