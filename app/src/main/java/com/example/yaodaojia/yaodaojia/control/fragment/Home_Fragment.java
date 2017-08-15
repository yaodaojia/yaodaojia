package com.example.yaodaojia.yaodaojia.control.fragment;

import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.example.yaodaojia.yaodaojia.App;
import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.base.BaseFragment;
import com.example.yaodaojia.yaodaojia.control.activity.home.Home_Location;
import com.example.yaodaojia.yaodaojia.control.activity.home.Home_Search;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by axi on 2017/8/9.
 */

public class Home_Fragment extends BaseFragment implements LocationSource, AMapLocationListener {

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
    @BindView(R.id.home_tehui)
    TextView homeTehui;
    @BindView(R.id.home_time_hui)
    TextView homeTimeHui;
    @BindView(R.id.home_time_hour)
    TextView homeTimeHour;
    @BindView(R.id.home_time_min)
    TextView homeTimeMin;
    @BindView(R.id.home_time_second)
    TextView homeTimeSecond;
    @BindView(R.id.home_tehui_time)
    LinearLayout homeTehuiTime;
    @BindView(R.id.home_tehui_img)
    ImageView homeTehuiImg;
    @BindView(R.id.home_jingxuan)
    TextView homeJingxuan;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.home_jingxuan_img)
    ImageView homeJingxuanImg;
    @BindView(R.id.home_bibei)
    TextView homeBibei;
    @BindView(R.id.home_bibei_img)
    ImageView homeBibeiImg;
    @BindView(R.id.home_persional)
    TextView homePersional;
    @BindView(R.id.home_persional_img)
    ImageView homePersionalImg;
    @BindView(R.id.mapView)
    MapView mapView;
    //    示地图需要的变量
    private AMap aMap;//地图对象


    //定位需要的声明
    private AMapLocationClient mLocationClient = null;//定位发起端
    private AMapLocationClientOption mLocationOption = null;//定位参数
    private OnLocationChangedListener mListener = null;//定位监听器

    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    private List<View> mPagerList = new ArrayList<>();
    private static final int START = 0;
    public static final int STOP = 1;
    private int currentItem = 10000000;
    private Handler mHand = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case START:
                    homeViewpager.setCurrentItem(homeViewpager.getCurrentItem() + 1);
                    mHand.sendEmptyMessageDelayed(START, 2000);
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

        mapView = view.findViewById(R.id.mapView);
        //获取地图对象
        aMap = mapView.getMap();


        //设置显示定位按钮 并且可以点击
//        UiSettings settings = aMap.getUiSettings();
        //设置定位监听
        aMap.setLocationSource(this);
        // 是否显示定位按钮
//        settings.setMyLocationButtonEnabled(true);
        // 是否可触发定位并显示定位层
        aMap.setMyLocationEnabled(true);


//        //定位的小图标 默认是蓝点 这里自定义一团火，其实就是一张图片
//        MyLocationStyle myLocationStyle = new MyLocationStyle();
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_round));
//        myLocationStyle.radiusFillColor(android.R.color.transparent);
//        myLocationStyle.strokeColor(android.R.color.transparent);
//        aMap.setMyLocationStyle(myLocationStyle);

        //开始定位
        initLoc();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        initLunBo();
        initLocation();

    }

    private void initLocation() {

    }

    private void initLunBo() {
        View v1 = LayoutInflater.from(App.activity).inflate(R.layout.home_view_one, null);
        View v2 = LayoutInflater.from(App.activity).inflate(R.layout.home_view_two, null);
        View v3 = LayoutInflater.from(App.activity).inflate(R.layout.home_view_three, null);
        mPagerList.add(v1);
        mPagerList.add(v2);
        mPagerList.add(v3);
        homeViewpager.setAdapter(new ViewPagerAdapter(mPagerList));
        homeViewpager.setCurrentItem(currentItem++);
        mHand.sendEmptyMessageDelayed(START, 2000);
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


    @OnClick({R.id.home_location, R.id.home_msg_img, R.id.home_OnLine, R.id.home_Family, R.id.home_Essentical, R.id.home_Medical, R.id.home_health, R.id.home_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_location:
                Intent ins = new Intent(getContext(), Home_Location.class);
                startActivity(ins);
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

            case R.id.home_edit:
                Intent in = new Intent(getContext(), Home_Search.class);
                startActivity(in);
                break;

        }
    }

    //定位
    private void initLoc() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }


    //定位回调函数
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {

        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                amapLocation.getCountry();//国家信息
                amapLocation.getProvince();//省信息
                amapLocation.getCity();//城市信息
                amapLocation.getDistrict();//城区信息
                amapLocation.getStreet();//街道信息
                amapLocation.getStreetNum();//街道门牌号信息
                amapLocation.getCityCode();//城市编码
                amapLocation.getAdCode();//地区编码

                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {
                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener.onLocationChanged(amapLocation);
                    //添加图钉
//                    aMap.addMarker(getMarkerOptions(amapLocation));
                    //获取定位信息
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity() + "" + amapLocation.getProvince() + "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
                    Toast.makeText(getContext(), buffer.toString(), Toast.LENGTH_LONG).show();
                    isFirstLoc = false;
                    homeLocation.setText(amapLocation.getCity() + "");
                }


            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());

                Toast.makeText(getContext(), "定位失败", Toast.LENGTH_LONG).show();
            }
        }
    }

    //自定义一个图钉，并且设置图标，当我们点击图钉时，显示设置的信息
    private MarkerOptions getMarkerOptions(AMapLocation amapLocation) {
//        //设置图钉选项
//        MarkerOptions options = new MarkerOptions();
//        //图标
//        options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
//        //位置
//        options.position(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
//        StringBuffer buffer = new StringBuffer();
//        buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity() + "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
//        //标题
//        options.title(buffer.toString());
//        //子标题
//        options.snippet("这里好火");
//        //设置多少帧刷新一次图片资源
//        options.period(60);

        return null;

    }

    //激活定位
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;

    }

    //停止定位
    @Override
    public void deactivate() {
        mListener = null;
    }


    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }





    class ViewPagerAdapter extends PagerAdapter {
        private List<View> mList;

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
            if (container != null) {
                container.removeView(mList.get(position % mList.size()));
            }
            container.addView(mList.get(position % mList.size()));
            return mList.get(position % mList.size());
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

}
