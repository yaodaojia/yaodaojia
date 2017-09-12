package com.example.yaodaojia.yaodaojia.model.http.bean;

import com.google.gson.Gson;

/**
 * Created by yaodaojia on 2017/8/28.
 */

public class YanZhengBean {
    /**
     * success : false
     * message : 请输入手机号码
     */

    public boolean success;
    public String message;

    public static YanZhengBean objectFromData(String str) {

        return new Gson().fromJson(str, YanZhengBean.class);
    }
}
