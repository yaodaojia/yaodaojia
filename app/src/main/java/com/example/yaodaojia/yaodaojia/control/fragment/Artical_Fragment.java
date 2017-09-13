package com.example.yaodaojia.yaodaojia.control.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.control.activity.mine.ArticalDetailsActivity;
import com.example.yaodaojia.yaodaojia.control.adapter.ArticalAdapter;
import com.example.yaodaojia.yaodaojia.control.listener.RecyclerViewClickListener;
import com.example.yaodaojia.yaodaojia.model.http.bean.ArticalBean;
import com.example.yaodaojia.yaodaojia.model.http.http.OkHttp;
import com.example.yaodaojia.yaodaojia.util.Utils_Host;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * Created by axi on 2017/8/9.
 */
public class Artical_Fragment extends Fragment {
    private RecyclerView artical_recy;
    private String path = Utils_Host.host + "v1/article/alist";
    private RefreshLayout refresh;
    private ArticalAdapter adapter;
    int page = 1;
    int limit = 6;
    private List<ArticalBean.DataBean> listAll = new ArrayList<>();
    private List<ArticalBean.DataBean> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.artical_fragment, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        setListener();
    }

    private void setListener() {
        artical_recy.addOnItemTouchListener(new RecyclerViewClickListener(getActivity(), new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ArticalDetailsActivity.class);
                intent.putExtra("url", list.get(position).url);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }

    public void initView() {
        artical_recy = getView().findViewById(R.id.artical_recy);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        artical_recy.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        refresh = getView().findViewById(R.id.artical_smartRefreshlayout);
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                // list.clear();
                page++;
                initData();
                adapter.notifyDataSetChanged();
                refreshlayout.finishRefresh(2000);
            }
        });
        refresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
                limit++;
                listAll.addAll(list);
                adapter = new ArticalAdapter(getActivity(), listAll);
                artical_recy.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        //设置 Footer 为 球脉冲
        refresh.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
    }

    private void initData() {
        OkHttp.getAsync(path + "?page=" + page + "&limit=" + limit, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override

            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
                ArticalBean bean = gson.fromJson(result, ArticalBean.class);
                list = bean.data;
                if (adapter == null) {
                    adapter = new ArticalAdapter(getActivity(), list);
                    artical_recy.setAdapter(adapter);
                }
            }
        });
    }
}
