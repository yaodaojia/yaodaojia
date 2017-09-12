package com.example.yaodaojia.yaodaojia.control.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.yaodaojia.yaodaojia.control.fragment.HaveFragment;

import java.util.List;

/**
 * Created by yaodaojia on 2017/8/22.
 */

public class DiscountpageAdapter extends FragmentPagerAdapter {
    private List<String> mtiles;
    public DiscountpageAdapter(FragmentManager fm, List<String> mtiles) {
        super(fm);
        this.mtiles = mtiles;
    }

    @Override
    public int getCount() {
        return mtiles.size();
    }

    @Override
    public Fragment getItem(int position) {
        return HaveFragment.getFragment(mtiles.get(position), position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mtiles.get(position);
    }
}
