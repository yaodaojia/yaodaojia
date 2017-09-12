package com.example.yaodaojia.yaodaojia.control.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.control.adapter.DiscountListviewAdapter;
import com.example.yaodaojia.yaodaojia.model.http.bean.DiscountCouponBean;

/**
 * Created by yaodaojia on 2017/8/22.
 */

public class HaveFragment extends Fragment {
    public static String KEY1 = "arg1";
    public static String KEY2 = "arg2";
    private View view = null;
    private ListView listview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        String name = arguments.getString(KEY1);
        int position = arguments.getInt(KEY2);
        view=View.inflate(getActivity(), R.layout.discount_item,null);
        listview=view.findViewById(R.id.undiscount_lv);
        listview.setAdapter(new DiscountListviewAdapter(DiscountCouponBean.getList(),getActivity(),position));
        return view;
    }
    public static Fragment getFragment(String url, int i) {
        /**
         * 实例化
         *  赋值
         *  设置bundle
         */
        HaveFragment fragment = new HaveFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY1, url);
        bundle.putInt(KEY2, i);
        fragment.setArguments(bundle);
        return fragment;
    }
}
