package com.example.yaodaojia.yaodaojia.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.yaodaojia.yaodaojia.App;
import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.control.activity.MainActivity;

import butterknife.ButterKnife;

/**
 * Created by axi on 2017/8/8.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.activity = this;
//        getLocation();
        setContentView(getLayout());
        ButterKnife.bind(this);
        initView();
        initListener();

    }

    private void getLocation() {
        PackageManager pm = getPackageManager();
        boolean flag = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.RECORD_AUDIO", "packageName"));
        if (flag) {
            //有这个权限，做相应处理
            Intent in = new Intent(this, MainActivity.class);
            startActivity(in);
        }else {              //没有权限
            View v = LayoutInflater.from(this).inflate(R.layout.location_dialog,null);
            final Dialog dia = new AlertDialog.Builder(this).setView(v).create();
            dia.show();
            TextView mCancel = v.findViewById(R.id.location_cancel);
            TextView mSure =  v.findViewById(R.id.location_sure);
            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dia.dismiss();
                }
            });
            mSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dia.dismiss();
                    //开始定位
                }
            });

        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
