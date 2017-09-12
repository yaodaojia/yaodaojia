package com.example.yaodaojia.yaodaojia.control.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.model.http.bean.MyClassBean;

import java.util.List;

/**
 * Created by yaodaojia on 2017/8/18.
 */

public class MyAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<MyClassBean.DataBean.StyleBean> style;
    private int selectedPosition = -1;

    public MyAdapter(Context context, List<MyClassBean.DataBean.StyleBean> style) {
        this.context = context;
        this.style = style;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return style.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return style.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.mylist_item, null);
            holder = new ViewHolder();
            holder.textView = convertView.findViewById(R.id.textview);
            holder.imageView =convertView.findViewById(R.id.imageview);
            holder.layout = convertView.findViewById(R.id.colorlayout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 设置选中效果
        if (selectedPosition == position) {
            holder.textView.setTextColor(Color.parseColor("#FE5F27"));
            holder.layout.setBackgroundColor(Color.WHITE);
            holder.imageView.setImageResource(R.mipmap.shutiao);

        } else {
            holder.textView.setTextColor(Color.parseColor("#999999"));
            holder.layout.setBackgroundColor(Color.parseColor("#EFEFEF"));
            holder.imageView.setImageResource(R.mipmap.unshutiao);

        }
        holder.textView.setText(style.get(position).cat_name);
        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public LinearLayout layout;
    }
    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

}
