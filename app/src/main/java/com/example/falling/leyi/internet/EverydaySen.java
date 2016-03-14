package com.example.falling.leyi.internet;

import com.example.falling.leyi.dao.EverydayInfo;
import com.example.falling.leyi.internet.Analysis.Sentence;
import com.example.falling.leyi.internet.Analysis.XMLParser;

import org.xml.sax.InputSource;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by falling on 2015/11/16.
 */
public class EverydaySen {
    public static final String urlP = "http://open.iciba.com/dsapi/?file=xml";

    public static InputStream getInputStreamByUrl() {
        InputStream tempInput = null;
        URL url = null;
        HttpURLConnection connection = null;
        //设置超时时间
        try {
            url = new URL(urlP);
            connection = (HttpURLConnection) url.openConnection();     //别忘了强制类型转换
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(10000);
            tempInput = connection.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempInput;
    }

    public static EverydayInfo request() {
        EverydayInfo everydaySen = null;
        try {
            XMLParser xmlParser = new XMLParser();
            Sentence Senxml = new Sentence();
            xmlParser.parseYouDaoXml(Senxml, new InputSource(getInputStreamByUrl()));
            everydaySen = Senxml.getSen();
            return everydaySen;
        } catch (Exception e) {

        }
        return null;

    }

}
