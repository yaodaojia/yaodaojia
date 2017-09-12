package com.example.yaodaojia.yaodaojia.control.activity;

import android.content.Intent;
import android.os.Handler;
import android.view.WindowManager;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.base.BaseActivity;

/**
 * /**
 * 项目名称:药到家
 * 类描述:
 * 创建人: XI
 * 创建时间: 2017/8/28 0028 10:27
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class SplashActivity extends BaseActivity {

    @Override
    public int getLayout() {
        return R.layout.splash;
    }

    @Override
    public void initView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(in);
                finish();
            }
        },2000);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
