package com.example.yaodaojia.yaodaojia.control.activity.home;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.base.BaseActivity;
import com.example.yaodaojia.yaodaojia.control.adapter.Home_Search_Hot_Adapter;
import com.example.yaodaojia.yaodaojia.model.http.bean.Home_Search_Hot_Recycler_Bean;
import com.example.yaodaojia.yaodaojia.model.http.http.OkHttp;
import com.example.yaodaojia.yaodaojia.view.MyEditTexts;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;

import static com.example.yaodaojia.yaodaojia.R.id.home_search_cancel;

/**
 * Created by axi on 2017/8/15.
 */

public class Home_Search extends BaseActivity {

    @BindView(R.id.home_search_text)
    TextView homeSearchText;
    @BindView(R.id.home_search_hot_recycler)
    RecyclerView homeSearchHotRecycler;
    @BindView(R.id.home_search_edit)
    MyEditTexts homeSearchEdit;
    @BindView(home_search_cancel)
    ImageView homeSearchCancel;
    private List<Home_Search_Hot_Recycler_Bean.DataBean> mHotList = new ArrayList<>();
    private Home_Search_Hot_Adapter mHotAdapter;

    @Override
    public int getLayout() {
        return R.layout.home_search;
    }

    @Override
    public void initView() {
        GridLayoutManager grid = new GridLayoutManager(Home_Search.this, 5);
        homeSearchHotRecycler.setLayoutManager(grid);

    }

    private void initHot() {
        OkHttp.getAsync("http://api.googlezh.com/v1/index/wordhot", new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Log.d("Home_Search", result);
                Gson gson = new Gson();
                Home_Search_Hot_Recycler_Bean home_search_hot_recycler_bean = gson.fromJson(result, Home_Search_Hot_Recycler_Bean.class);
                mHotList.addAll(home_search_hot_recycler_bean.getData());
                if (mHotAdapter == null) {
                    mHotAdapter = new Home_Search_Hot_Adapter(Home_Search.this, mHotList);
                    homeSearchHotRecycler.setAdapter(mHotAdapter);

                } else {
                    mHotAdapter.getData(mHotList);
                }
                mHotAdapter.setOnItemClickListener(new Home_Search_Hot_Adapter.MyItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String keyword = mHotList.get(position).getKeyword();
                        search(keyword);
                    }
                });
            }
        });

    }

    @Override
    public void initData() {
        initHot();

    }

    @Override
    public void initListener() {

        homeSearchEdit.setOnKeyListener(new View.OnKeyListener() {
                                            @Override
                                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                                //输入完后按键盘上的搜索键【回车键改为了搜索键】


                                                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                                                    // 先隐藏键盘
                                                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                                            .hideSoftInputFromWindow(Home_Search.this.getCurrentFocus()
                                                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                                    //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
                                                    if (!homeSearchEdit.getText().toString().isEmpty()) {

                                                        search(homeSearchEdit.getText().toString() + "");
                                                    } else {
                                                        Toast.makeText(Home_Search.this, "输入内容不能为空", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                return false;
                                            }

                                        }
        );
    }

    private void search(String name) {
        Intent in = new Intent(Home_Search.this, Activity_Search.class);
        in.putExtra("name", name);
        startActivity(in);
        finish();
    }


    @OnClick({R.id.home_search_text,R.id.home_search_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.home_search_text:
                if (homeSearchEdit.getText().toString().isEmpty()) {
                    Toast.makeText(this, "输入内容不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    search(homeSearchEdit.getText().toString() + "");
                }
                break;
            case R.id.home_search_cancel:
                finish();
                break;
        }

    }



}
