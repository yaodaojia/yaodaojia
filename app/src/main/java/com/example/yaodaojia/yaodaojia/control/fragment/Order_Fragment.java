package com.example.yaodaojia.yaodaojia.control.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.control.activity.home.Goods_Detail_Activity;
import com.example.yaodaojia.yaodaojia.control.activity.home.Home_Search;
import com.example.yaodaojia.yaodaojia.control.adapter.MyAdapter;
import com.example.yaodaojia.yaodaojia.control.adapter.SubAdapter;
import com.example.yaodaojia.yaodaojia.model.http.bean.MyClassBean;
import com.example.yaodaojia.yaodaojia.model.http.http.MyCallBack;
import com.example.yaodaojia.yaodaojia.model.http.http.OkHttp;
import com.example.yaodaojia.yaodaojia.model.http.http.Parsing;
import com.example.yaodaojia.yaodaojia.model.http.http.ParsingImple;
import com.example.yaodaojia.yaodaojia.util.Utils_Host;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by axi on 2017/8/9.
 */

public class Order_Fragment extends Fragment implements View.OnClickListener {
    private ListView listView;
    private ListView subListView;
    private MyAdapter myAdapter;
    private SubAdapter subAdapter;
    private String path = Utils_Host.host + "v1/goodsstyle/style";
    private List<MyClassBean.DataBean.GoodsBean> goods;
    private MyClassBean myClassBean;
    private int id;
    private int page = 1;
    private EditText order_edite;
    private List<MyClassBean.DataBean.GoodsBean> goodAll = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.order_fragment, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        OkHttp.getAsync(path, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
                MyClassBean classBean = gson.fromJson(result, MyClassBean.class);
                List<MyClassBean.DataBean.StyleBean> style = classBean.data.style;
                myAdapter = new MyAdapter(getActivity(), style);
                listView.setAdapter(myAdapter);
                selectDefult();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                myAdapter.setSelectedPosition(position);
                myAdapter.notifyDataSetInvalidated();
                MyClassBean.DataBean.StyleBean item = (MyClassBean.DataBean.StyleBean) myAdapter.getItem(position);
                id = item.cat_id;
                page = 1;
                subAdapter.clearItems();
                initSubAdapter(id);
            }
        });

    }

    private void initSubAdapter(int id) {
        Parsing par = new ParsingImple();
        Map<String, String> map = new HashMap<>();
        map.put("cat_id", String.valueOf(id));
        map.put("page", String.valueOf(page));
        par.gets(path, map, new MyCallBack() {
            @Override
            public void onSuccess(String strSuccess) throws UnsupportedEncodingException {
                Log.i("result", strSuccess);
                Gson gson = new Gson();
                myClassBean = gson.fromJson(strSuccess, MyClassBean.class);
                goods = myClassBean.data.goods;
                if (subAdapter == null) {
                    subAdapter = new SubAdapter(getActivity());
                    subListView.setAdapter(subAdapter);
                    subAdapter.setItem(goods);
                    subListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Context context = getActivity().getApplication();
                            Intent intent = new Intent(context, Goods_Detail_Activity.class);
                            MyClassBean.DataBean.GoodsBean item = (MyClassBean.DataBean.GoodsBean) subAdapter.getItem(i);
                            intent.putExtra("goods_id", item.g_id);
                            intent.putExtra("name", item.g_name);
                            intent.putExtra("img", item.img_url);
                            intent.putExtra("price", item.shop_price);
                            intent.putExtra("content", item.g_symptom);
                            startActivity(intent);
                        }
                    });
                } else
                    subAdapter.setItem(goods);

            }

            @Override
            public void onError(String strError) {

            }
        });
    }

    private void init() {
        listView = getView().findViewById(R.id.listView);
        subListView = getView().findViewById(R.id.subListView);
        order_edite = getView().findViewById(R.id.order_edit);
        order_edite.setOnClickListener(this);
        RefreshLayout refreshLayout = getView().findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                subAdapter.notifyDataSetChanged();
                refreshlayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                initSubAdapter(id);
                subAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadmore(2000);
            }
        });
        //设置 Header 为 Material风格
        //refreshLayout.setRefreshHeader(new MaterialHeader(getActivity()).setShowBezierWave(true));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
    }

    private void selectDefult() {
        myAdapter.setSelectedPosition(0);
        MyClassBean.DataBean.StyleBean item = (MyClassBean.DataBean.StyleBean) myAdapter.getItem(0);
        int cat_id = item.cat_id;
        myAdapter.notifyDataSetInvalidated();
        initSubAdapter(cat_id);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_edit:
                startActivity(new Intent(getActivity(), Home_Search.class));
                break;
        }
    }
}
