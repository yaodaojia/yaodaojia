package com.example.yaodaojia.yaodaojia.control.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.yaodaojia.yaodaojia.control.fragment.MyOrderFragment;

import java.util.List;

/**
 * Created by yaodaojia on 2017/8/24.
 */

public class MyOrderAdapter extends FragmentPagerAdapter{
    private List<String> mtitles;
    public MyOrderAdapter(FragmentManager fm, List<String> mtitles) {
        super(fm);
        this.mtitles=mtitles;
    }

    @Override
    public Fragment getItem(int position) {
        return MyOrderFragment.getFragment(mtitles.get(position),position);

    }

    @Override
    public int getCount() {
        return mtitles.size();
    }
    public CharSequence getPageTitle(int position) {
        return mtitles.get(position);
    }
}
