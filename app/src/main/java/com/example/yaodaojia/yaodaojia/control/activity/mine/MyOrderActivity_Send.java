package com.example.yaodaojia.yaodaojia.control.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.control.adapter.MyOrderAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity_Send extends AppCompatActivity implements View.OnClickListener{
    private ViewPager vp;
    private TabLayout tablayou;
    private List<String> mtitles = new ArrayList<>();
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        initView();
        initData();
    }
    private void initData() {
        mtitles.add("全部");
        mtitles.add("待付款");
        mtitles.add("待发货");
        mtitles.add("已发货");
        vp.setAdapter(new MyOrderAdapter(getSupportFragmentManager(), mtitles));
        tablayou.setupWithViewPager(vp);
        tablayou.setTabMode(TabLayout.MODE_FIXED);
        tablayou.getTabAt(2).select();
    }

    private void initView() {
        tablayou = (TabLayout) findViewById(R.id.My_order_tab_layout);
        vp = (ViewPager) findViewById(R.id.My_Order_vp);
        back = (ImageView) findViewById(R.id.my_order_back);
        back.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_order_back:
                MyOrderActivity_Send.this.finish();
                break;
            default:
                break;
        }
    }
}
