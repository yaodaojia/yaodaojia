package com.example.yaodaojia.yaodaojia.view.swipelist;

import android.widget.BaseAdapter;

/**
 * Created by yaodaojia on 2017/9/7.
 */

public abstract class BaseSwipListAdapter extends BaseAdapter {
    public boolean getSwipEnableByPosition(int position){
        return true;
    }
}
