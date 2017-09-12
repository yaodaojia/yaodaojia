package com.example.yaodaojia.yaodaojia.util;

import android.os.CountDownTimer;
import android.widget.Button;

/**
 * Created by yaodaojia on 2017/8/28.
 */

public class MyCountDownTime extends CountDownTimer{
    private Button btn;
    private String message;
    public MyCountDownTime(long millisInFuture, long countDownInterval,
                           Button btn, String message) {
        super(millisInFuture, countDownInterval);
        this.btn=btn;
        this.message =message;
        // TODO Auto-generated constructor stub
    }
    @Override
    public void onFinish() {
        // TODO Auto-generated method stub
        btn.setEnabled(true);
        btn.setText(message);

    }
    @Override
    public void onTick(long arg0) {
        // TODO Auto-generated method stub
        btn.setEnabled(false);
        btn.setText(arg0 / 1000 + "s后重试");
    }
}
