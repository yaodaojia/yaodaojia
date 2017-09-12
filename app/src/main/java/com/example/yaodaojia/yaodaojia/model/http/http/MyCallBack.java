package com.example.yaodaojia.yaodaojia.model.http.http;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2017/5/9 0009.
 */

public interface MyCallBack {
    void onSuccess(String strSuccess) throws UnsupportedEncodingException;
    void onError(String strError);
}
