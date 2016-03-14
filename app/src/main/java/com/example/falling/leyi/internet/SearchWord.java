package com.example.falling.leyi.internet;

/**
 * Created by falling on 2015/11/4.
 */


import com.example.falling.leyi.dao.WordInfo;
import com.example.falling.leyi.internet.Analysis.JsonA;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by xuzywozz on 2015/10/20.
 */
public class SearchWord {

    public static final String urlPre = "http://fanyi.youdao.com/openapi.do" +
            "?keyfrom=falling" +
            "&key=1083815289" +
            "&type=data" +
            "&doctype=json" +
            "&version=1.1" +
            "&q=";

    public static WordInfo request(String param) {
        WordInfo word = null;
        try {
            HttpClient client = new DefaultHttpClient();
            param = urlPre + param;
            param = param.replace(" ","%20");
            HttpGet get = new HttpGet(param);

            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(response.getEntity());

                word= JsonA.JsonAnalytical(result);


                return word;
            }
        } catch (Exception e) {

        }
        return null;

    }
}

