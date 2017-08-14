package com.example.yaodaojia.yaodaojia.control.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaodaojia.yaodaojia.App;
import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by axi on 2017/8/9.
 */

public class Home_Fragment extends BaseFragment {

    @BindView(R.id.home_location)
    TextView homeLocation;
    @BindView(R.id.home_edit)
    EditText homeEdit;
    @BindView(R.id.home_msg_img)
    ImageView homeMsgImg;
    @BindView(R.id.home_viewpager)
    ViewPager homeViewpager;
    @BindView(R.id.home_OnLine)
    TextView homeOnLine;
    @BindView(R.id.home_Family)
    TextView homeFamily;
    @BindView(R.id.home_Essentical)
    TextView homeEssentical;
    @BindView(R.id.home_Medical)
    TextView homeMedical;
    @BindView(R.id.home_health)
    TextView homeHealth;
    @BindView(R.id.home_recycler)
    RecyclerView homeRecycler;
    Unbinder unbinder;
    private List<View> mPagerList = new ArrayList<>();
    private static final int START = 0;
    public static final int STOP = 1;
    private int currentItem = 10000000;
    private Handler mHand = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case START:
                    homeViewpager.setCurrentItem(homeViewpager.getCurrentItem()+1);
                    mHand.sendEmptyMessageDelayed(START,2000);
                    break;
                case STOP:
                    mHand.removeMessages(STOP);
                    break;
            }
        }
    };

    @Override
    public int getLayout() {
        return R.layout.home_fragment;
    }

    @Override
    public void initView(View view) {

         homeViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
             @Override
             public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                 Log.d("Home_Fragment", "当页面开始滚动时");
             }

             @Override
             public void onPageSelected(int position) {
                 Log.d("Home_Fragment", "当开始选择下一个页面时");
             }

             @Override
             public void onPageScrollStateChanged(int state) {
                 Log.d("Home_Fragment", "当页面滚动状态发生了变化时");
             }
         });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        View v1 = LayoutInflater.from(App.activity).inflate(R.layout.home_view_one,null);
        View v2 = LayoutInflater.from(App.activity).inflate(R.layout.home_view_two,null);
        View v3 = LayoutInflater.from(App.activity).inflate(R.layout.home_view_three,null);
        mPagerList.add(v1);
        mPagerList.add(v2);
        mPagerList.add(v3);
        homeViewpager.setAdapter(new ViewPagerAdapter(mPagerList));
        homeViewpager.setCurrentItem(currentItem++);
        mHand.sendEmptyMessageDelayed(START,2000);
        //通过监听onTouch事件，设置一个标签isLoop;手指按下时isLoop = false,手指抬起后isLoop = true;
        homeViewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Toast.makeText(getContext(), "Down", Toast.LENGTH_SHORT).show();
                        boolean isLoop = false;
                        break;
                    case MotionEvent.ACTION_UP:
                        Toast.makeText(getContext(), "Up", Toast.LENGTH_SHORT).show();
                        isLoop = true;
                        break;
                }
                return false;
            }
        });
    }


    @OnClick({R.id.home_location, R.id.home_msg_img, R.id.home_OnLine, R.id.home_Family, R.id.home_Essentical, R.id.home_Medical, R.id.home_health})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_location:
                break;
            case R.id.home_msg_img:
                break;
            case R.id.home_OnLine:
                break;
            case R.id.home_Family:
                break;
            case R.id.home_Essentical:
                break;
            case R.id.home_Medical:
                break;
            case R.id.home_health:
                break;
        }
    }
    class ViewPagerAdapter extends PagerAdapter{
        private List<View> mList ;

        public ViewPagerAdapter(List<View> mList) {
            this.mList = mList;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if(container!=null){
                container.removeView(mList.get(position%mList.size()));
            }
            container.addView(mList.get(position%mList.size()));
            return mList.get(position%mList.size());
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

}
