package com.example.falling.leyi.dao;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by falling on 2015/11/10.
 */
public class WordInfo{

    private String word = null;//单词
    private String translation = null;//单词
    private String uk_phonetic = null;
    private String us_phonetic = null;
    private String explains = null;
    private String value = null;
    private String key = null;

    public WordInfo()
    {
        this.word = "";
        this.uk_phonetic = "";
        this.us_phonetic = "";
        this.explains = "";
        this.value = "";
        this.key = "";
        this.translation="";

    }


    public ArrayList<String> getvalueList()
    {
        ArrayList<String> list = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new StringReader(this.value));
        String str = null;
        try
        {
            while ((str = br.readLine()) != null)
            {
                list.add(str);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> getkeyList()
    {
        ArrayList<String> list = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new StringReader(this.key));
        String str = null;
        try
        {
            while ((str = br.readLine()) != null)
            {
                list.add(str);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getUk_phonetic() {
        return uk_phonetic;
    }

    public void setUk_phoneti(String uk_phoneti) {
        this.uk_phonetic = uk_phoneti;
    }

    public String getUs_phonetic() {
        return us_phonetic;
    }

    public void setUs_phonetic(String us_phonetic) {
        this.us_phonetic = us_phonetic;
    }

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String toString(){
       String message="";
        message = word;
        message += "\t" + translation;
        message += "\n\t解释" + explains;
        message += "\n网络释义：\n";

       for(int i =0;i<this.getkeyList().size();i++){
           message += this.getkeyList().get(i)+"\n";
           message += "   "+this.getvalueList().get(i)+"\n";
           message +="\n\n";
       }

       return message;
   }

    public String toStringFromDB(){
        String result="";
        result=this.word + "\n" ;

        result+= this.explains+"\n\n";


        return result;
    }
}
