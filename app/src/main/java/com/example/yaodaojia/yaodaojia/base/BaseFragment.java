package com.example.yaodaojia.yaodaojia.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by axi on 2017/8/8.
 */

public abstract class BaseFragment extends Fragment {
    private Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(getLayout(),container,false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this,view);
        initView(view);
        initListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
    //找布局
    public abstract int getLayout();
    //初始化控件
    public abstract void initView(View view);
    //事件监听
    public abstract void initListener();
    //加载资源
    public abstract void initData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
    }
}

