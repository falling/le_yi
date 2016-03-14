package com.example.falling.leyi.internet.Analysis;

import com.example.falling.leyi.dao.WordInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by falling on 2015/10/23.
 */
public class JsonA {
    public static WordInfo JsonAnalytical(String result) {
        WordInfo re= new WordInfo();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray("[" + result + "]");


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject != null) {
                    // 要翻译的内容
                    String query = jsonObject.getString("query");
                    re.setWord(query);
                    // 翻译内容
                    String translation = jsonObject
                            .getString("translation");
                    re.setTranslation(translation);
                    // 有道词典-基本词典
                    if (jsonObject.has("basic")) {
                        JSONObject basic = jsonObject
                                .getJSONObject("basic");
                        if (basic.has("uk-phonetic")) {
                            String phonetic = basic.getString("uk-phonetic");
                            re.setUk_phoneti(phonetic);
                        }
                        if (basic.has("us-phonetic")) {
                            String phonetic = basic.getString("us-phonetic");
                            re.setUs_phonetic(phonetic);
                        }
                        if (basic.has("explains")) {
                            String explains = basic.getString("explains");
                            re.setExplains(explains);
                        }
                    }
                    // 有道词典-网络释义
                    if (jsonObject.has("web")) {
                        String web = jsonObject.getString("web");
                        JSONArray webString = new JSONArray("[" + web + "]");
                        JSONArray webArray = webString.getJSONArray(0);
                        int count = 0;
                        while (!webArray.isNull(count)) {

                            if (webArray.getJSONObject(count).has("key")) {
                                String key = webArray.getJSONObject(count)
                                        .getString("key");

                                String k = re.getKey();
                                k+=	"<" + (count+1) + ">"+key+"\n";
                                re.setKey(k);

                            }
                            if (webArray.getJSONObject(count).has("value")) {
                                String value = webArray
                                        .getJSONObject(count).getString(
                                                "value");

                                String v = re.getValue();
                                v+=  value+"\n";
                                re.setValue(v);
                            }
                            count++;
                        }
                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return re;
    }
}