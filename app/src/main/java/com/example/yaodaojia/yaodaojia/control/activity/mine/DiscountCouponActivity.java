package com.example.yaodaojia.yaodaojia.control.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.control.adapter.DiscountpageAdapter;

import java.util.ArrayList;
import java.util.List;
/*
* 优惠券
* */
public class DiscountCouponActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager vp;
    private TabLayout tablayou;
    private List<String> mtitles = new ArrayList<>();
    private ImageView back;
    private List<Fragment> flist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_coupon);
        initView();
        initData();
    }
    private void initData() {
        mtitles.add("未使用(3)");
        mtitles.add("已使用(10)");
        mtitles.add("已过期(7)");
        vp.setAdapter(new DiscountpageAdapter(getSupportFragmentManager(), mtitles));
        tablayou.setupWithViewPager(vp);
        tablayou.setTabMode(TabLayout.MODE_FIXED);

    }

    private void initView() {
        tablayou = (TabLayout) findViewById(R.id.tab_layout);
        vp = (ViewPager) findViewById(R.id.discount_vp);
        back = (ImageView) findViewById(R.id.discount_back);
        back.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.discount_back:
                DiscountCouponActivity.this.finish();
                break;
            default:
                break;
        }
    }
}
