package com.example.yaodaojia.yaodaojia.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.yaodaojia.yaodaojia.App;

import butterknife.ButterKnife;

/**
 * Created by axi on 2017/8/8.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.activity = this;
        setContentView(getLayout());
        ButterKnife.bind(this);
        initView();
        initListener();

    }
    //找布局
    public abstract int getLayout();
    //找ID
    public abstract void initView();
    //加载数据
    public abstract void initData();
    //监听
    public abstract void initListener();

    @Override
    protected void onResume() {
        super.onResume();
        App.activity = this;
        initData();
    }
}
