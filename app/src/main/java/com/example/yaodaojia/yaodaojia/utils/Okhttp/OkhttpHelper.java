package com.example.yaodaojia.yaodaojia.utils.Okhttp;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by asus on 2016/10/17.
 */

public class OkhttpHelper {
    private static OkHttpClient okHttpClient;
    private Gson gson;
    private Handler handler;

    private OkhttpHelper() {
        //进行实例化
        okHttpClient=new OkHttpClient();
        okHttpClient.newBuilder().connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
        .build();
        gson=new Gson();
        handler=new Handler(Looper.getMainLooper());


    }

    ;

   public static OkhttpHelper getInstance() {
        return new OkhttpHelper();
    }

    //get请求方法
    public void get(String url, BaseCallBack callBack) {

        Request request = buildRequset(url, null, HttpMethodType.GET);
        doRequest(request, callBack);
    }

    //get请求方法
    public void post(String url, Map<String, String> params, BaseCallBack callBack) {
        Request request = buildRequset(url, params, HttpMethodType.GET);
        doRequest(request, callBack);
    }

    //请求方法
    public void doRequest(Request request, final BaseCallBack callBack) {
        callBack.onRequsetBefore(request);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {
                    //得到解析的字符串
                    String resultStr = response.body().string();
                    if (callBack.mType == String.class) {
                        callBack.onSuccess(response, resultStr);
                        callbackSuccess(callBack,response, resultStr);
                    } else {
                        try {
                            Object object = gson.fromJson(resultStr, callBack.mType);
                            callbackSuccess(callBack,response,object);

                        } catch (JsonIOException e) {
                            callBack.onError(response, response.code(), e);
                        }

                    }

                } else {
                    callBack.onError(response, response.code(), null);
                }
            }
        });
    }

    private Request buildRequset(String url, Map<String, String> params, HttpMethodType methodType) {

        Request.Builder builder = new Request.Builder();
        builder.url(url);
        //判断方法类型
        if (methodType == HttpMethodType.GET) {
            builder.get();
        } else if (methodType == HttpMethodType.POST) {
            RequestBody body = bulidFormData(params);
            builder.post(body);
        }
        return builder.build();
    }

    private RequestBody bulidFormData(Map<String, String> params) {
        FormBody.Builder bulider = new FormBody.Builder();
        if (params != null) {

            for (Map.Entry<String, String> entry : params.entrySet()) {
                bulider.add(entry.getKey(), entry.getValue());
            }
        }
        return bulider.build();
    }
    public void callbackSuccess(final BaseCallBack callback, final Response response, final Object object){
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response,object);
            }
        });
    }

    enum HttpMethodType {
        GET,
        POST
    }
}
