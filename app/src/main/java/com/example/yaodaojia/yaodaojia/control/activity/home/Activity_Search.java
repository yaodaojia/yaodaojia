package com.example.yaodaojia.yaodaojia.control.activity.home;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.base.BaseActivity;
import com.example.yaodaojia.yaodaojia.control.adapter.Activity_Search_Adapter;
import com.example.yaodaojia.yaodaojia.model.http.bean.Activity_Search_Bean;
import com.example.yaodaojia.yaodaojia.model.http.http.OkHttp;
import com.example.yaodaojia.yaodaojia.view.MyEditTexts;
import com.example.yaodaojia.yaodaojia.view.MySmartRefreshLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * /**
 * 项目名称:
 * 类描述:
 * 创建人: XI
 * 创建时间: 2017/8/18 0018 14:28
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class Activity_Search extends BaseActivity {

    @BindView(R.id.activity_search_cancel)
    ImageView activitySearchCancel;
    @BindView(R.id.activity_search_edit)
    MyEditTexts activitySearchEdit;
    @BindView(R.id.activity_search_recycler)
    RecyclerView activitySearchRecycler;
    @BindView(R.id.activity_search_smart)
    MySmartRefreshLayout activitySearchSmart;
    private String name;
    private List<Activity_Search_Bean.DataBean> mSearchList = new ArrayList<>();
    private int page = 1;
    private Activity_Search_Adapter mSearchAdapter;
    private int limit = 12;

    @Override
    public int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        Intent in = getIntent();
        name = in.getStringExtra("name");
        GridLayoutManager grid = new GridLayoutManager(Activity_Search.this, 3);
        activitySearchRecycler.setLayoutManager(grid);
        activitySearchSmart.setOnRefreshListener(new MySmartRefreshLayout.onRefreshListener() {
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
                        initSearch();
                        activitySearchSmart.stopRefresh();
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
                        initSearch();
                        activitySearchSmart.stopLoadMore();
                    }
                }).start();

            }
        });
    }

    @Override
    public void initData() {
        activitySearchEdit.setText(name);
        initSearch();
    }


    private void initSearch() {
        String strUTF8 = null;
        try {
            strUTF8 = URLDecoder.decode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OkHttp.getAsync("http://api.googlezh.com/v1/index/search" + "?page=" + page + "&limit=" + limit + "&keyword=" + strUTF8, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Log.d("Activity_Search", result);
                Gson gson = new Gson();
                Activity_Search_Bean activity_search_bean = gson.fromJson(result, Activity_Search_Bean.class);
                mSearchList.addAll(activity_search_bean.getData());
                if (mSearchAdapter == null) {
                    mSearchAdapter = new Activity_Search_Adapter(Activity_Search.this, mSearchList);
                    activitySearchRecycler.setAdapter(mSearchAdapter);
                } else {
                    mSearchAdapter.getData(mSearchList);
                }
            }
        });
    }

    @Override
    public void initListener() {
//
//        activitySearchEdit.setOnKeyListener(new View.OnKeyListener() {
//                                                @Override
//                                                public boolean onKey(View v, int keyCode, KeyEvent event) {
//                                                    //输入完后按键盘上的搜索键【回车键改为了搜索键】
//
//
//                                                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
//                                                        // 先隐藏键盘
//                                                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
//                                                                .hideSoftInputFromWindow(Activity_Search.this.getCurrentFocus()
//                                                                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                                                        //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
//                                                        if (!activitySearchEdit.getText().toString().isEmpty()) {
//
//                                                        } else {
//                                                            Toast.makeText(Activity_Search.this, "输入内容不能为空", Toast.LENGTH_SHORT).show();
//                                                        }
//                                                    }
//
//                                                    return false;
//                                                }
//
//                                            }
//        );
    }


    @OnClick(R.id.activity_search_cancel)
    public void onViewClicked() {
        finish();
    }

}
