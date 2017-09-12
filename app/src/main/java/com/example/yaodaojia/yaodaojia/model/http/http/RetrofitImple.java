package com.example.yaodaojia.yaodaojia.model.http.http;

import android.content.SharedPreferences;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public class RetrofitImple implements IHTTP{
    private static RetrofitImple imple;
    private RetrofitInter inter;
    private final Retrofit re;
    private static SharedPreferences share;
    protected RetrofitImple(){
        re = new Retrofit.Builder().baseUrl("http://www.baidu.com/").build();
        inter =  re.create(RetrofitInter.class);
    }
    public synchronized static RetrofitImple getInstance(){
         if(imple == null){
              imple = new RetrofitImple();
         }
        return imple;
    }
   @Override
public void GET(String url, Map<String, String> map, MyCallBack callback) {
    Call<ResponseBody> call = inter.parsingGets(url, map);
    initCall(callback,call);
}
    public void GETCookie(String url, Map<String, String> map, MyCallBack callback) {
////        SharedPreferences sha = App.base.getSharedPreferences("data",Context.MODE_PRIVATE);
//        Call<ResponseBody> call = inter.parsingGet(sha.getString("cookie",""),url, map);
//        initCalls(callback,call);
    }
    @Override
    public void GETS(String url, Map<String, String> map, MyCallBack callback) {
        Call<ResponseBody> call = inter.parsingGets(url, map);
        initCalls(callback,call);
    }

    @Override
    public void POST(String url, Map<String, String> map, MyCallBack callback) {
        Call<ResponseBody> call = inter.parsingPost(url, map);
        initCalls(callback,call);
    }

    @Override
    public void Filed(String url, Map<String, RequestBody> map, File file, String filekey, MyCallBack callback) {
//        RequestBody fileRequest = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//
//        MultipartBody.Part part = MultipartBody.Part.createFormData(filekey, file.getName(), fileRequest);
//        SharedPreferences sha = App.base.getSharedPreferences("data",Context.MODE_PRIVATE);
//        Call<ResponseBody> call = inter.Filed(sha.getString("cookie",""),url, map,part);
//        initCalls(callback,call);
    }

    private void initCall(final MyCallBack callback, Call<ResponseBody> call){
//        call.enqueue(new retrofit2.Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if(response.isSuccessful()){
//                    saveCookie(response);
//                    try {
//                        callback.onSuccess(response.body().string());
//                    } catch (IOException e) {
//                        callback.onError(e.getMessage());
//                    }
//                }else{
//                    try {
//                        callback.onError(response.body().string());
//                    } catch (IOException e) {
//                        callback.onError(e.getMessage());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    callback.onError(t.getMessage());
//            }
//        });
    }
    private void initCalls(final MyCallBack callback, Call<ResponseBody> call){
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
             if(response!=null&&callback!=null){
                if(response.isSuccessful()){
                    try {
                        callback.onSuccess(response.body().string());
                    } catch (IOException e) {
                        callback.onError(e.getMessage());
                    }
                }else{
                    try {
                        callback.onError(response.body().string());
                    } catch (IOException e) {
                        callback.onError(e.getMessage());
                    }
                }
            }
        }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
//    private static void saveCookie(Response<ResponseBody> response) {
//        String cookie = "";
//        Headers head = response.headers();
//        Set<String> names = head.names();
//        for (String key : names) {
//            String value = head.get(key);
//            if (key.contains("Set-Cookie")) {
//                cookie += value + ";";
//                share = App.base.getSharedPreferences("data", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = share.edit();
//                editor.putString("cookie", cookie);
//                Log.i("cookie________", cookie);
//                editor.commit();
//            }
//
//        }
//    }


}
