package com.example.yaodaojia.yaodaojia.model.http.http;

import java.io.File;
import java.util.Map;

import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public interface IHTTP {
    void GET(String url, Map<String, String> map, MyCallBack callback);
    void GETS(String url, Map<String, String> map, MyCallBack callback);
    void GETCookie(String url, Map<String, String> map, MyCallBack callback);
    void POST(String url, Map<String, String> map, MyCallBack callback);
    void Filed(String url, Map<String, RequestBody> map, File file, String filekey, MyCallBack callBack);
}
