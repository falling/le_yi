package com.example.falling.leyi.dbmanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.falling.leyi.dao.WordInfo;

import java.util.ArrayList;

/**
 * Created by falling on 2015/11/7.
 */
public class sqldic {

    private static String DIC = "/data/data/com.example.falling.leyi/databases/dic.db";

    public static ArrayList<String> getdata(int startI) {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DIC, null);
        Cursor cursor = db.rawQuery("select * from dicInfo limit ?,100", new String[]{String.valueOf(startI)});
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String word = cursor.getString(1);
            String explain = cursor.getString(2);

            String re = "\n"+id +"\n\t " + word +"\n\t " +explain;
            list.add(re);

        }
        cursor.close();
        db.close();
        return list;
    }

    public static WordInfo SerchByid(int id){
        WordInfo word= new WordInfo();
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DIC, null);
        Cursor cursor = db.rawQuery("select * from dicInfo where sid=?", new String[]{String.valueOf(id)});
        if(cursor.moveToNext()){
            word.setWord(cursor.getString(1));
            word.setExplains(cursor.getString(2));
        }
        cursor.close();
        db.close();
        return word;
    }


    public static WordInfo SerchByWord(String Word){
        WordInfo word= new WordInfo();
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DIC, null);
        Cursor cursor = db.rawQuery("select * from dicInfo where word=?", new String[]{Word});
        if(cursor.moveToNext()){
            word.setWord(cursor.getString(1));
            word.setExplains(cursor.getString(2));
        }
        cursor.close();
        db.close();
        return word;
    }
}
