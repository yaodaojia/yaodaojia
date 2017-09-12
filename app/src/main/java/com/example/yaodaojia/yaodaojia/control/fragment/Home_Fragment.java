package com.example.yaodaojia.yaodaojia.control.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
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
import com.bumptech.glide.Glide;
import com.example.yaodaojia.yaodaojia.App;
import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.base.BaseFragment;
import com.example.yaodaojia.yaodaojia.control.activity.home.Home_Location;
import com.example.yaodaojia.yaodaojia.control.activity.home.Home_Search;
import com.example.yaodaojia.yaodaojia.control.adapter.Home_Fragment_Goods_Adapter;
import com.example.yaodaojia.yaodaojia.model.http.bean.Home_DaoHang;
import com.example.yaodaojia.yaodaojia.model.http.bean.Home_DaoHang_Bean;
import com.example.yaodaojia.yaodaojia.model.http.bean.Home_Fragment_Goods_Bean;
import com.example.yaodaojia.yaodaojia.model.http.bean.Home_LunBo;
import com.example.yaodaojia.yaodaojia.model.http.http.OkHttp;
import com.example.yaodaojia.yaodaojia.model.http.http.Parsing;
import com.example.yaodaojia.yaodaojia.view.MySmartRefreshLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Request;

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


    @BindView(R.id.home_recycler)
    RecyclerView homeRecycler;
    Unbinder unbinder;
    @BindView(R.id.mapView)
    MapView mapView;
    TextView homePersionalContent;
    @BindView(R.id.fragment_viewpager_circle_one)
    RadioButton fragmentViewpagerCircleOne;
    @BindView(R.id.fragment_viewpager_circle_two)
    RadioButton fragmentViewpagerCircleTwo;
    @BindView(R.id.fragment_viewpager_circle_three)
    RadioButton fragmentViewpagerCircleThree;
    @BindView(R.id.fragment_viewpager_circle_four)
    RadioButton fragmentViewpagerCircleFour;
    @BindView(R.id.home_registration)
    TextView homeRegistration;
    @BindView(R.id.home_OnLine)
    TextView homeOnLine;
    @BindView(R.id.home_Urgent)
    TextView homeUrgent;
    @BindView(R.id.home_Prescription)
    TextView homePrescription;
    @BindView(R.id.home_family)
    TextView homeFamily;
    @BindView(R.id.home_child_title)
    TextView homeChildTitle;
    @BindView(R.id.home_child_content)
    TextView homeChildContent;
    @BindView(R.id.home_child_img)
    ImageView homeChildImg;
    @BindView(R.id.home_mother_title)
    TextView homeMotherTitle;
    @BindView(R.id.home_mother_content)
    TextView homeMotherContent;
    @BindView(R.id.home_mother_img)
    ImageView homeMotherImg;
    @BindView(R.id.home_father_title)
    TextView homeFatherTitle;
    @BindView(R.id.home_father_content)
    TextView homeFatherContent;
    @BindView(R.id.home_father_img)
    ImageView homeFatherImg;
    @BindView(R.id.home_old_title)
    TextView homeOldTitle;
    @BindView(R.id.home_old_content)
    TextView homeOldContent;
    @BindView(R.id.home_old_img)
    ImageView homeOldImg;
    @BindView(R.id.home_office_title)
    TextView homeOfficeTitle;
    @BindView(R.id.home_office_content)
    TextView homeOfficeContent;
    @BindView(R.id.home_office_img)
    ImageView homeOfficeImg;
    @BindView(R.id.home_driver_title)
    TextView homeDriverTitle;
    @BindView(R.id.home_driver_content)
    TextView homeDriverContent;
    @BindView(R.id.home_driver_img)
    ImageView homeDriverImg;
    @BindView(R.id.home_fragment_goods_more)
    ImageView homeFragmentGoodsMore;
    @BindView(R.id.home_fragment_smart)
    MySmartRefreshLayout homeFragmentSmart;
    Unbinder unbinder1;
    //    示地图需要的变量
    private AMap aMap;//地图对象
    private RadioButton mOne, mTwo, mThree, mFour;
    private List<Home_DaoHang.ListBean> mDaoHangList = new ArrayList<>();
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
                    //homeViewpager.setCurrentItem(homeViewpager.getCurrentItem() + 1);
                    mHand.sendEmptyMessageDelayed(START, 2000);
                    break;
                case STOP:
                    mHand.removeMessages(STOP);
                    break;
            }
        }
    };

    private List<Home_LunBo.DataBean> mLunBoList = new ArrayList<>();
    private boolean boo;
    private Parsing par;
    private int page = 1;
    private int limit=6;
    private List<Home_Fragment_Goods_Bean.DataBean> mGoodsList = new ArrayList<>();
    private Home_Fragment_Goods_Adapter goodsAdapter;
    private StringBuffer buffer;
    private static final String[] PERMISSIONS_CONTACT = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
    private static final int REQUEST_CONTACTS = 1000;
    private SharedPreferences mShared;
    private SharedPreferences.Editor mEditor;
    private String province;
    private String city;
    private String dis;
    private double lng;
    private double lat;
    private BroadcastReceiver broadcastReceiver;

    @Override
    public int getLayout() {
        return R.layout.home_fragment;
    }

    @Override
    public void initView(View view) {
        mShared = getActivity().getSharedPreferences("location", Context.MODE_PRIVATE);
        mEditor = mShared.edit();

        mOne = view.findViewById(R.id.fragment_viewpager_circle_one);
        mTwo = view.findViewById(R.id.fragment_viewpager_circle_two);
        mThree = view.findViewById(R.id.fragment_viewpager_circle_three);
        mFour = view.findViewById(R.id.fragment_viewpager_circle_four);
        homeViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.d("Home_Fragment", "当页面开始滚动时");
                if (position % mPagerList.size() == 0) {
                    mOne.setChecked(true);
                } else if (position % mPagerList.size() == 1) {
                    mTwo.setChecked(true);
                } else {
                    mThree.setChecked(true);
                }
            }

            @Override
            public void onPageSelected(int position) {
//                Log.d("Home_Fragment", "当开始选择下一个页面时");
                currentItem = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.d("Home_Fragment", "当页面滚动状态发生了变化时");

            }
        });

        mapView = (MapView) view.findViewById(R.id.mapView);
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
        if (Build.VERSION.SDK_INT >= 23) {
            showSetPermission();
        } else {
            initLoc();
        }
        initDaoHang();
        GridLayoutManager grid = new GridLayoutManager(getContext(), 3);
        homeRecycler.setLayoutManager(grid);


        homeFragmentSmart.setOnRefreshListener(new MySmartRefreshLayout.onRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        initRecycler();
                        homeFragmentSmart.stopRefresh();
                    }
                }).start();

            }

            @Override
            public void onLoadMore() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        page++;
                        initRecycler();
                        homeFragmentSmart.stopLoadMore();
                    }
                }).start();

            }
        });
    }

    private void showSetPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestSetPermissions();
        } else {
            initLoc();
        }
    }

    private void requestSetPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_PHONE_STATE)) {
            Snackbar.make(homeLocation, "permission_contacts_rationale",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_CONTACT, REQUEST_CONTACTS);
                        }
                    })
                    .show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_CONTACT, REQUEST_CONTACTS);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CONTACTS) {
//            if (PermissionUtil.verifyPermissions(grantResults)) {
//                initLoc();
//            } else {
//                Toast.makeText(getActivity(),"授权不通过",Toast.LENGTH_SHORT).show();
//            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void setListViewHeight(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void initRecycler() {
        OkHttp.getAsync("http://api.googlezh.com//v1/index/recommend"+"?page="+page+"&limit="+limit, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Log.d("Home_Fragment", "goods" + result);
                Gson gson = new Gson();
                Home_Fragment_Goods_Bean home_fragment_goods_bean = gson.fromJson(result, Home_Fragment_Goods_Bean.class);
                mGoodsList.addAll(home_fragment_goods_bean.getData());
                if (goodsAdapter == null) {
                    goodsAdapter = new Home_Fragment_Goods_Adapter(getContext(), mGoodsList);
                    homeRecycler.setAdapter(goodsAdapter);
                } else {
                    Log.e("TAG", "集合个数----" + mGoodsList.size());
                    goodsAdapter.getData(mGoodsList);
//                    homeRecycler.setAdapter(goodsAdapter);
                }
            }
        });
    }

    private void initDaoHang() {
        OkHttp.getAsync("http://api.googlezh.com/v1/index/menu", new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Log.d("Home_Fragment", "导航" + result);
                Gson gson = new Gson();
                Home_DaoHang_Bean home_daoHang_bean = gson.fromJson(result, Home_DaoHang_Bean.class);
                if (home_daoHang_bean.isSuccess()) {
                    //第一个
                    homeChildTitle.setText(home_daoHang_bean.getData().get(0).getName() + "");
                    if (home_daoHang_bean.getData().get(0).getContent().isEmpty()) {
                        homeChildContent.setVisibility(View.GONE);
                    } else {
                        homeChildContent.setVisibility(View.VISIBLE);
                        homeChildContent.setText(home_daoHang_bean.getData().get(0).getContent());
                    }
                    Glide.with(getActivity()).load(home_daoHang_bean.getData().get(0).getImage()).into(homeChildImg);

                    //第二个
                    homeMotherTitle.setText(home_daoHang_bean.getData().get(1).getName() + "");
                    if (home_daoHang_bean.getData().get(1).getContent().isEmpty()) {
                        homeMotherContent.setVisibility(View.GONE);
                    } else {
                        homeMotherContent.setVisibility(View.VISIBLE);
                        homeMotherContent.setText(home_daoHang_bean.getData().get(1).getContent());
                    }
                    Glide.with(getActivity()).load(home_daoHang_bean.getData().get(1).getImage()).into(homeMotherImg);


                    //第三个
                    homeFatherTitle.setText(home_daoHang_bean.getData().get(2).getName() + "");
                    if (home_daoHang_bean.getData().get(2).getContent().isEmpty()) {
                        homeFatherContent.setVisibility(View.GONE);
                    } else {
                        homeFatherContent.setVisibility(View.VISIBLE);
                        homeFatherContent.setText(home_daoHang_bean.getData().get(2).getContent());
                    }
                    Glide.with(getActivity()).load(home_daoHang_bean.getData().get(2).getImage()).into(homeFatherImg);


                    //第四个
                    homeOldTitle.setText(home_daoHang_bean.getData().get(3).getName() + "");
                    if (home_daoHang_bean.getData().get(3).getContent().isEmpty()) {
                        homeOldContent.setVisibility(View.GONE);
                    } else {
                        homeOldContent.setVisibility(View.VISIBLE);
                        homeOldContent.setText(home_daoHang_bean.getData().get(3).getContent());
                    }
                    Glide.with(getActivity()).load(home_daoHang_bean.getData().get(3).getImage()).into(homeOldImg);


                    //第五个
                    homeOfficeTitle.setText(home_daoHang_bean.getData().get(4).getName() + "");
                    if (home_daoHang_bean.getData().get(4).getContent().isEmpty()) {
                        homeOfficeContent.setVisibility(View.GONE);
                    } else {
                        homeOfficeContent.setVisibility(View.VISIBLE);
                        homeOfficeContent.setText(home_daoHang_bean.getData().get(4).getContent());
                    }
                    Glide.with(getActivity()).load(home_daoHang_bean.getData().get(4).getImage()).into(homeOfficeImg);


                    //第六个
                    homeDriverTitle.setText(home_daoHang_bean.getData().get(5).getName() + "");
                    if (home_daoHang_bean.getData().get(5).getContent().isEmpty()) {
                        homeDriverContent.setVisibility(View.GONE);
                    } else {
                        homeDriverContent.setVisibility(View.VISIBLE);
                        homeDriverContent.setText(home_daoHang_bean.getData().get(5).getContent());
                    }
                    Glide.with(getActivity()).load(home_daoHang_bean.getData().get(5).getImage()).into(homeDriverImg);

                } else {
                    Toast.makeText(getContext(), "home_daoHang_bean.getData():" + home_daoHang_bean.getData(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        initNet();
        homeViewpager.setCurrentItem(currentItem++);
        mHand.sendEmptyMessageDelayed(START, 2000);
//        initLunBo();

        initRecycler();
    }

    private void initNet() {
        OkHttp.getAsync("http://api.googlezh.com/v1/index/banner", new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
                Home_LunBo lun = gson.fromJson(result, Home_LunBo.class);

                mLunBoList = lun.getData();

                for (int i = 0; i < mLunBoList.size(); i++) {
                    View v = LayoutInflater.from(getContext()).inflate(R.layout.home_view_one, null);
                    ImageView img = v.findViewById(R.id.home_view_img);
                    Glide.with(getActivity()).load(mLunBoList.get(i).getImage()).into(img);
                    mPagerList.add(v);
                }
                homeViewpager.setAdapter(new ViewPagerAdapter(mPagerList));
                Log.d("Home_Fragment", "mLunBoList:" + mLunBoList);
                Log.d("Home_Fragment", "mPagerList:" + mPagerList);

            }
        });

    }


    private void initLunBo() {
        View v1 = LayoutInflater.from(App.activity).inflate(R.layout.home_view_one, null);
//        View v2 = LayoutInflater.from(App.activity).inflate(R.layout.home_view_two, null);
//        View v3 = LayoutInflater.from(App.activity).inflate(R.layout.home_view_three, null);
        mPagerList.add(v1);
//        mPagerList.add(v2);
//        mPagerList.add(v3);
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


    @OnClick({R.id.home_location, R.id.home_msg_img, R.id.home_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_location:
                if (buffer.toString().isEmpty()) {
                    Toast.makeText(getContext(), "请给予定位权限，并进行定位", Toast.LENGTH_SHORT).show();
                } else {
                    Intent ins = new Intent(getContext(), Home_Location.class);
//                ins.putExtra("Address", buffer.toString());
                    mEditor.putString("location_address", buffer.toString());
                    mEditor.commit();
                    ins.putExtra("lng", lng);
                    ins.putExtra("lat", lat);
                    ins.putExtra("city", city);
                    ins.putExtra("province", province);
                    ins.putExtra("dis", dis);
                    startActivity(ins);
                }
                break;
            case R.id.home_msg_img:
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
        mLocationOption.setOnceLocation(true);
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
                    buffer = new StringBuffer();
                    buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity() + "" + amapLocation.getProvince() + "" + amapLocation.getDistrict() + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
                    isFirstLoc = false;
                    province = amapLocation.getProvince();
                    city = amapLocation.getCity();
                    dis = amapLocation.getDistrict();
                    lng = amapLocation.getLongitude();
                    lat = amapLocation.getLatitude();
                    homeLocation.setText(amapLocation.getCity() + "");
                    Log.d("Home_Fragment", "buffer:" + buffer);
                    Log.d("Home_Fragment", "dsjvvovonvo" + amapLocation.getLocationDetail());

                    mListener = null;
                    if (mLocationClient != null) {
                        mLocationClient.stopLocation();
                        mLocationClient.onDestroy();
                    }
                    mLocationClient = null;
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


    //激活定位
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
//        if (mLocationClient == null) {
//            mLocationClient = new AMapLocationClient(getContext());
//            mLocationClient.setLocationListener(this);
//            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
////下面这句就是设置只定位一次的代码，默认是1秒钟定位一次，此方法在AMAPLocationActivity这个类里面
//            mLocationOption.setOnceLocation(true);
//            mLocationClient.setLocationOption(mLocationOption);
//            mLocationClient.startLocation();
//        }
    }

    //停止定位
    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
        mLocationOption = null;
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
        deactivate();
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
        // mapView.onDestroy();
        Log.d("Home_Fragment", "onDestroy");
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
        if (broadcastReceiver != null) {
            getActivity().unregisterReceiver(broadcastReceiver);
        }
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
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("Home_Fragment", "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Home_Fragment", "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("Home_Fragment", "onCreateView");
        unbinder1 = ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("Home_Fragment", "onActivityCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Home_Fragment", "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Home_Fragment", "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Home_Fragment", "onDestroyView");
        mapView.onDestroy();
        Log.d("Home_Fragment", "onDestroy");
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
        unbinder1.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("Home_Fragment", "onDetach");
    }
    private boolean NetWorkStatus() {
        boolean netSataus = false;
        ConnectivityManager cwjManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        cwjManager.getActiveNetworkInfo();

        if (cwjManager.getActiveNetworkInfo() != null) {
            netSataus = cwjManager.getActiveNetworkInfo().isAvailable();
        }

        if (!netSataus){
            AlertDialog.Builder b = new AlertDialog.Builder(getActivity()).setTitle("没有可用的网络").setMessage("是否对网络进行设置？");

            b.setPositiveButton("是", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Intent wifiSettingsIntent = new Intent("android.settings.WIFI_SETTINGS");
                    startActivity(wifiSettingsIntent);
                }
            }).setNeutralButton("否", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.cancel();
                }
            }).show();
        }
        return netSataus;
    }
}
