package com.example.yaodaojia.yaodaojia.control.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.control.adapter.OrderItemAdapter;
import com.example.yaodaojia.yaodaojia.model.http.bean.OrderBean;

/**
 * Created by yaodaojia on 2017/8/24.
 * 我的订单
 */

public class MyOrderFragment extends Fragment implements View.OnClickListener{
    public static String MKEY = "arg1";
    public static String FKEY = "arg2";
    private ListView listview;
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        Bundle arguments = getArguments();
        String name = arguments.getString(MKEY);
        int position = arguments.getInt(FKEY);
        view = View.inflate(getActivity(), R.layout.orrder_item, null);
        listview = view.findViewById(R.id.order_list);
        LinearLayout order_empty=view.findViewById(R.id.order_empty);
        listview.setEmptyView(order_empty);
        listview.setAdapter(new OrderItemAdapter(getActivity(), OrderBean.getList(),position));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    public static Fragment getFragment(String url, int i) {
        /**
         * 实例化
         *  赋值
         *  设置bundle
         */
        MyOrderFragment fragment =new MyOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MKEY, url);
        bundle.putInt(FKEY, i);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){


        }
    }
}
