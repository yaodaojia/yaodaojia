package com.example.yaodaojia.yaodaojia.model.http.http;

import android.support.annotation.Nullable;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public interface RetrofitInter {

    @GET
    Call<ResponseBody> parsingGets(@Url String url, @QueryMap Map<String, String> map);
    @GET
    Call<ResponseBody> parsingGet(@Header("Cookie") String cookie, @Url String url, @QueryMap Map<String, String> map);
    @FormUrlEncoded
    @POST
    Call<ResponseBody> parsingPost(@Url String url, @Nullable @FieldMap Map<String, String> map);
    @Multipart
    @POST
    Call<ResponseBody> Filed(@Header("Cookie") String cookie, @Url String url, @PartMap Map<String, RequestBody> params, @Part MultipartBody.Part file);
}


//内存缓存+sdcard缓存
 //dada/data/packageName/files
//data/data/packageName/cache
//mnt/sdcard/packageName/cache
