package com.example.yaodaojia.yaodaojia.control.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.base.BaseActivity;
import com.example.yaodaojia.yaodaojia.control.fragment.Artical_Fragment;
import com.example.yaodaojia.yaodaojia.control.fragment.Home_Fragment;
import com.example.yaodaojia.yaodaojia.control.fragment.Mine_Fragment;
import com.example.yaodaojia.yaodaojia.control.fragment.Order_Fragment;
import com.example.yaodaojia.yaodaojia.control.fragment.ShoppingCart_Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {


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
                tra = man.beginTransaction();
                tra.replace(R.id.main_frame, new ShoppingCart_Fragment());
                tra.commit();
                break;
            case R.id.main_order:
                tra = man.beginTransaction();
                tra.replace(R.id.main_frame, new Order_Fragment());
                tra.commit();
                break;
            case R.id.main_mine:
                tra = man.beginTransaction();
                tra.replace(R.id.main_frame, new Mine_Fragment());
                tra.commit();
                break;
        }
    }



    @OnClick(R.id.main_artical)
    public void onViewClicked() {
        tra = man.beginTransaction();
        tra.replace(R.id.main_frame, new Artical_Fragment());
        tra.commit();
    }
}
