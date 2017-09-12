package com.example.yaodaojia.yaodaojia.model.http.http;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public class ParsingImple implements Parsing {
    @Override
    public void get(String url,Map<String, String> map, MyCallBack callback) {

        ParsingFactory.initParsing().GET( url,map, callback);
    }

    @Override
    public void gets(String url, Map<String, String> map, MyCallBack callback) {
        ParsingFactory.initParsing().GETS( url,map, callback);
    }

    @Override
    public void getCookie(String url, Map<String, String> map, MyCallBack callback) {
        ParsingFactory.initParsing().GETCookie( url,map, callback);
    }

    @Override
    public void post(String url,Map<String, String> map, MyCallBack callback) {
        ParsingFactory.initParsing().POST( url,map, callback);

    }
}
