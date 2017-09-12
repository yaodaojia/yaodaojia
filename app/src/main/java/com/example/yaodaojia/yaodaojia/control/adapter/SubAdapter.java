package com.example.yaodaojia.yaodaojia.control.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.model.http.bean.MyClassBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaodaojia on 2017/8/18.
 */

public class SubAdapter extends BaseAdapter {
    Context context;
    List<MyClassBean.DataBean.GoodsBean> goods = new ArrayList<>();

    public SubAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return goods.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return goods.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView =convertView.inflate(context,R.layout.sublist_item, null);
            viewHolder = new ViewHolder();
            viewHolder.goods_name = (TextView) convertView
                    .findViewById(R.id.goods_name);
            viewHolder.goods_price = convertView.findViewById(R.id.good_price);
            viewHolder.goods_image = convertView.findViewById(R.id.goods_iv);
            viewHolder.goods_specification = convertView.findViewById(R.id.goods_specification);
            viewHolder.goods_effect = convertView.findViewById(R.id.goods_effect);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.goods_name.setText(goods.get(position).g_name);
        viewHolder.goods_name.setTextColor(Color.BLACK);
        viewHolder.goods_price.setText("￥"+goods.get(position).shop_price);
        viewHolder.goods_effect.setText("功效："+goods.get(position).g_symptom);
        viewHolder.goods_specification.setText("规格："+goods.get(position).g_size);
        Glide.with(context)
                .load(goods.get(position).img_url)
                .placeholder(R.mipmap.weijiazai)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(viewHolder.goods_image);
        return convertView;
    }
    public void setItem(List<MyClassBean.DataBean.GoodsBean> item) {
        goods.addAll(item);
        notifyDataSetChanged();
    }
    public void clearItems(){
        goods.clear();
    }

    public static class ViewHolder {
        public TextView goods_name;
        public TextView goods_price;
        public TextView goods_effect;
        public TextView goods_specification;
        public ImageView goods_image;
    }
}
