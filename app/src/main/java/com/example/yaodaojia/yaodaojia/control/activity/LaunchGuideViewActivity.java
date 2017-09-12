package com.example.yaodaojia.yaodaojia.control.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * /**
 * 项目名称: 药到家
 * 类描述:
 * 创建人: XI
 * 创建时间: 2017/8/28 0028 11:31
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class LaunchGuideViewActivity extends BaseActivity {

    @BindView(R.id.launch_guide_vp)
    ViewPager launchGuideVp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;
    private boolean isFirst;
    private LinearLayout mLin ;
    @Override
    public int getLayout() {
        return R.layout.launch_guide;
    }

    @Override
    public void initView() {
        initVp();
    }

    private void initVp() {
        SharedPreferences pref = getSharedPreferences("first", Activity.MODE_PRIVATE);
        isFirst = pref.getBoolean("status", true);
        if (!isFirst){
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
            finish();
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        final LinearLayout guideFour = (LinearLayout) inflater.inflate(R.layout.launch_guide_three, null);
        mLin = guideFour.findViewById(R.id.launch_three);
        mLin.setVisibility(View.VISIBLE);
        guideFour.findViewById(R.id.launch_three_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator upY = ObjectAnimator.ofFloat(guideFour.findViewById(R.id.launch_three_up),
                            "translationY",0f,-1500f);
                ObjectAnimator downY = ObjectAnimator.ofFloat(guideFour.findViewById(R.id.launch_three_down),
                        "translationY",0,500);
                AnimatorSet set = new AnimatorSet();
                set.play(upY).with(downY);
                set.setDuration(2000);
                set.start();
                set.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        Intent in = new Intent(LaunchGuideViewActivity.this, MainActivity.class);
                        startActivity(in);
                        finish();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });

            }
        });
        views = new ArrayList<View>();
        // 初始化引导图片列表
        views.add(inflater.inflate(R.layout.launch_guide_one, null));
        views.add(inflater.inflate(R.layout.launch_guide_two, null));
        views.add(guideFour);
        // 初始化Adapter
        vpAdapter = new ViewPagerAdapter(views, this);

        launchGuideVp.setAdapter(vpAdapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        // 绑定回调
        launchGuideVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    class ViewPagerAdapter extends PagerAdapter {

        // 界面列表
        private List<View> views;
        private AppCompatActivity activity;


        public ViewPagerAdapter(List<View> views, AppCompatActivity activity) {
            this.views = views;
            this.activity = activity;
        }

        //加载viewpager的每个item
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position), 0);
            return views.get(position);
        }

        //删除ViewPager的item
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // super.destroyItem(container, position, object);
            container.removeView(views.get(position));
        }

        // 获得当前界面数
        @Override
        public int getCount() {
            if (views != null) {
                return views.size();
            }
            return 0;
        }

        //官方推荐这么写，没研究。。。。
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
