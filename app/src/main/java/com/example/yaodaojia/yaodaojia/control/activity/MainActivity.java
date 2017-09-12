package com.example.yaodaojia.yaodaojia.control.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Process;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.base.BaseActivity;
import com.example.yaodaojia.yaodaojia.control.activity.mine.LoginActivity;
import com.example.yaodaojia.yaodaojia.control.fragment.Artical_Fragment;
import com.example.yaodaojia.yaodaojia.control.fragment.Home_Fragment;
import com.example.yaodaojia.yaodaojia.control.fragment.Mine_Fragment;
import com.example.yaodaojia.yaodaojia.control.fragment.Order_Fragment;
import com.example.yaodaojia.yaodaojia.control.fragment.ShoppingCart_Fragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.main_frame)
    FrameLayout mainFrame;
    @BindView(R.id.main_home)
    RadioButton mainHome;
    @BindView(R.id.main_shoppingcart)
    RadioButton mainShoppingcart;
    @BindView(R.id.main_order)
    RadioButton mainOrder;
    @BindView(R.id.main_mine)
    RadioButton mainMine;
    @BindView(R.id.Main_group)
    RadioGroup MainGroup;
    @BindView(R.id.main_artical)
    RadioButton mainArtical;
    private FragmentManager man;
    private FragmentTransaction tra;
    private long firstTime;
    private SharedPreferences mShared, mShare;
    private SharedPreferences.Editor mEditor;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mShare = getSharedPreferences("login", MODE_PRIVATE);
        mShared = getSharedPreferences("first", MODE_PRIVATE);
        mEditor = mShared.edit();
        mEditor.putBoolean("status", false);
        mEditor.commit();
    }

    @Override
    public void initData() {
        man = getSupportFragmentManager();
        tra = man.beginTransaction();
        tra.replace(R.id.main_frame, new Home_Fragment());
        tra.commit();
    }

    @Override
    public void initListener() {

    }


    @OnClick({R.id.main_home, R.id.main_shoppingcart, R.id.main_order, R.id.main_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_home:
                tra = man.beginTransaction();
                tra.replace(R.id.main_frame, new Home_Fragment());
                tra.commit();
                break;
            case R.id.main_shoppingcart:
                if (mShare.getString("token", "").isEmpty()) {
                    Intent inm = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(inm);
                } else {
                    tra = man.beginTransaction();
                    tra.replace(R.id.main_frame, new ShoppingCart_Fragment());
                    tra.commit();
                }
                break;
            case R.id.main_order:

                tra = man.beginTransaction();
                tra.replace(R.id.main_frame, new Order_Fragment());
                tra.commit();
//                FragmentBuilder.getInstance(MainActivity.this).add(R.id.main_frame,Mine_Fragment.class);


                break;
            case R.id.main_mine:
                if (mShare.getString("token", "").isEmpty()) {
                    Intent inm = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(inm);
                } else {
                    tra = man.beginTransaction();
                    tra.replace(R.id.main_frame, new Mine_Fragment());
                    tra.commit();
//                FragmentBuilder.getInstance(MainActivity.this).add(R.id.main_frame,Mine_Fragment.class);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    @OnClick(R.id.main_artical)
    public void onViewClicked() {
        tra = man.beginTransaction();
        tra.replace(R.id.main_frame, new Artical_Fragment());
        tra.commit();
    }

    //双击退出
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d("=========", "横屏");
        } else {
            Log.d("=========", "竖屏");
        }
    }
}
