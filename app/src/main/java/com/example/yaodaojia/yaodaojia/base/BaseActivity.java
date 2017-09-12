package com.example.yaodaojia.yaodaojia.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.control.activity.MainActivity;

import butterknife.ButterKnife;

import static com.example.yaodaojia.yaodaojia.App.activity;

/**
 * Created by axi on 2017/8/8.
 */

public abstract class BaseActivity extends AppCompatActivity{
    boolean netSataus = false;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
//        getLocation();
        setContentView(getLayout());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = this.getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(this.getResources().getColor(R.color.white));
//        }
        ButterKnife.bind(this);
        //透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initView();
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,getLayout());
        //判断当前SDK版本号，如果是4.4以上，就是支持沉浸式状态栏的
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        // 如果在设置完成后需要再次进行操作，可以重写操作代码，在这里不再重写
        initData();
        initListener();
    }

    private void getLocation() {
        PackageManager pm = getPackageManager();
        boolean flag = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.ACCESS_COARSE_LOCATION", "packageName"));
        if (flag) {
            //有这个权限，做相应处理
            Intent in = new Intent(this, MainActivity.class);
            startActivity(in);
        } else {              //没有权限
            View v = LayoutInflater.from(this).inflate(R.layout.location_dialog, null);
            final Dialog dia = new AlertDialog.Builder(this).setView(v).create();
            dia.show();
            TextView mCancel = (TextView) v.findViewById(R.id.location_cancel);
            TextView mSure = (TextView) v.findViewById(R.id.location_sure);
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
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        Log.v("hdwoicow"," == onConfigurationChanged");
    }
    @Override
    protected void onResume() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
        activity = this;

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
