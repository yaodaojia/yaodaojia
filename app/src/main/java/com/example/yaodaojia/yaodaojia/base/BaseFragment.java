package com.example.yaodaojia.yaodaojia.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by axi on 2017/8/8.
 */

public abstract class BaseFragment extends Fragment {
    private Unbinder unbinder;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(getLayout(),container,false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //透明状态栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //透明导航栏
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        unbinder = ButterKnife.bind(this,view);
        initView(getView());
        initListener();
        Log.d("BaseFragment", "onViewCreated");
    }


    @Override
    public void onResume() {
        super.onResume();
        initData();
        Log.d("BaseFragment", "onResume");
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
        getLayout();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            onHidden();
        }else {
            onShow();
        }
    }

    public void onHidden(){

    }
    public void onShow(){

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

