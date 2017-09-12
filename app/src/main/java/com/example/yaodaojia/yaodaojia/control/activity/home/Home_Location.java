package com.example.yaodaojia.yaodaojia.control.activity.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.base.BaseActivity;
import com.example.yaodaojia.yaodaojia.control.activity.mine.LoginActivity;
import com.example.yaodaojia.yaodaojia.control.listener.RecyclerViewClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by axi on 2017/8/15.
 */

public class Home_Location extends BaseActivity implements RecyclerViewClickListener.OnItemClickListener,PoiSearch.OnPoiSearchListener,AdapterView.OnItemClickListener {

    @BindView(R.id.home_location_new)
    TextView homeLocationNew;
    @BindView(R.id.home_location_add)
    TextView homeLocationAdd;
    @BindView(R.id.home_location_address_now)
    TextView homeLocationAddressNow;
    @BindView(R.id.home_location_listView)
    ListView mLvResult;
    @BindView(R.id.home_location_cancel)
    ImageView homeLocationCancel;
    // 控件相关
    private EditText mEtKeyword;
    private PoiSearch.Query query;
    private PoiSearch search;
    private ArrayList<PoiItem> items;
    private List<String> strs;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mShared,mShare;
    private PoiItem item;
    private Intent in;

    @Override
    public int getLayout() {
        return R.layout.home_location;
    }

    @Override
    public void initView() {
        in = getIntent();
        mShare = getSharedPreferences("login",MODE_PRIVATE);
        mShared = getSharedPreferences("location",MODE_PRIVATE);
        mEditor = mShared.edit();
        homeLocationAddressNow.setText(mShared.getString("location_address",""));
        mEtKeyword = (EditText) findViewById(R.id.et_keyword);
    }

    @Override
    public void initData() {
        mLvResult.setOnItemClickListener(this);
    }

    @Override
    public void initListener() {
        mEtKeyword.setOnKeyListener(new View.OnKeyListener() {
                                            @Override
                                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                                //输入完后按键盘上的搜索键【回车键改为了搜索键】
                                                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                                                    // 先隐藏键盘
                                                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                                            .hideSoftInputFromWindow(Home_Location.this.getCurrentFocus()
                                                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                                    //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断

                                                        //1.获得用户输入数据
                                                        String keyword = mEtKeyword.getText().toString();
                                                        //2.判断用户是否输入为空
                                                        if (keyword.trim().length() == 0) {
                                                            Toast.makeText(Home_Location.this, "请输入查询条件", Toast.LENGTH_LONG).show();
                                                        } else {
                                                            //3.不为空进行搜索
                                                            search(keyword);
//                                                            searchLocation(keyword);
                                                        }

                                                }

                                                return false;
                                            }

                                        }
        );
    }




    @OnClick({R.id.home_location_cancel, R.id.home_location_address_now})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_location_cancel:
                finish();
                break;
            case R.id.home_location_address_now:
                if(mShare.getString("token","").isEmpty()) {
                    Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(Home_Location.this, LoginActivity.class);
                    startActivity(in);
                }else {
                    Intent ins = new Intent(Home_Location.this, NewAddressActivity.class);
                    mEditor.putString("location_add_name", mShared.getString("location_address", ""));
                    mEditor.putFloat("latitude", (float) in.getDoubleExtra("lat",0));
                    mEditor.putFloat("longitude", (float) in.getDoubleExtra("lng",0));
                    mEditor.putString("province", in.getStringExtra("province"));
                    mEditor.putString("city", in.getStringExtra("city"));
                    mEditor.putString("distric", in.getStringExtra("dis"));
                    mEditor.commit();
                    startActivity(ins);

                }
                break;
        }
    }

    private void search(String keyword) {
        // 初始化查询条件
        query = new PoiSearch.Query(keyword, null, "北京");
        query.setPageSize(10);
        query.setPageNum(1);

        // 查询兴趣点
        search = new PoiSearch(this, query);
        // 异步搜索
        search.searchPOIAsyn();
        search.setOnPoiSearchListener(this);
    }


    @Override
    public void onPoiSearched(PoiResult poiResult, int rCode) {
        strs = new ArrayList<String>();
        items = poiResult.getPois();
        if (items != null && items.size() > 0) {
            item = null;
            for (int i = 0, count = items.size(); i < count; i++) {
                item = items.get(i);
                strs.add(item.getTitle());
            }
            // 给ListView赋值，显示结果
            ArrayAdapter<String> array = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, strs);
            mLvResult.setAdapter(array);
            Log.d("Home_Location", "item:" + item);
            mEditor.putFloat("latitude", (float) item.getLatLonPoint().getLatitude());
            mEditor.putFloat("longitude", (float) item.getLatLonPoint().getLongitude());
            mEditor.putString("province", item.getProvinceName());
            mEditor.putString("city", item.getCityName());
            mEditor.putString("distric", item.getAdName());
            mEditor.commit();

        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
//        if(mShare.getString("token","").isEmpty()) {
//            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
//        }else {
//            homeLocationAddressNow.setText(poiItem.getTitle() + "");
//
//            Intent in = new Intent(Home_Location.this, NewAddressActivity.class);
//            startActivity(in);
//        }
    }

    @Override
    public void onItemClick(View view, int position) {
//        if(mShare.getString("token","").isEmpty()) {
//            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
//        }else {
//            homeLocationAddressNow.setText(items.get(position).getTitle() + "");
//            Intent in = new Intent(Home_Location.this, NewAddressActivity.class);
//            startActivity(in);
//        }
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("Home_Location", mShare.getString("token", ""));
        homeLocationAddressNow.setText(items.get(i).getTitle()+"");
        if(mShare.getString("token","").isEmpty()) {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
        }else {
            Intent in = new Intent(Home_Location.this, NewAddressActivity.class);
            mEditor.putString("location_add_name", items.get(i).getTitle()+"");
            mEditor.commit();
            startActivity(in);
        }
    }
}
