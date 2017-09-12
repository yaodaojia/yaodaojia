package com.example.yaodaojia.yaodaojia.control.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.model.http.bean.OrderBean;

import java.util.List;

/**
 * Created by yaodaojia on 2017/8/24.
 */

public class OrderItemAdapter extends BaseAdapter {
    Context context;
    List<OrderBean> list;
    int position;
    public OrderItemAdapter(Context context, List<OrderBean> list,int position) {
        this.context = context;
        this.list = list;
        this.position=position;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        //判断当前视图是否为空
        if (convertView == null) {
            //实例化 holder 对象
            holder=new ViewHolder();
            //设置视图
            convertView=View.inflate(context, R.layout.myorder, null);

            /**
             * 为holder类中的属性设置id
             */
            holder.all_price = convertView.findViewById(R.id.order_all_price);
            holder.price = convertView.findViewById(R.id.order_price);
            holder.content = convertView.findViewById(R.id.order_content);
            holder.name = convertView.findViewById(R.id.order_name);
            holder.num = convertView.findViewById(R.id.order_num);
            holder.image_url = convertView.findViewById(R.id.order_iv);
            holder.canel = convertView.findViewById(R.id.canle);
            holder.fukuan = convertView.findViewById(R.id.fukuan);
            holder.deal_close = convertView.findViewById(R.id.my_order_deal_close);
            holder.title= convertView.findViewById(R.id.my_order_title);

            /**
             * 判断类别显示和隐藏
             */
            switch (position) {
                case 0:
                    holder.canel.setVisibility(View.GONE);
                    holder.fukuan.setVisibility(View.VISIBLE);
                    holder.fukuan.setText("删除订单");
                    holder.fukuan.setTextColor(Color.parseColor("#000f0f"));
                    holder.deal_close.setTextColor(Color.parseColor("#ff4200"));
                    break;
                case 1:
                    holder.canel.setVisibility(View.VISIBLE);
                    holder.fukuan.setVisibility(View.VISIBLE);
                    holder.title.setText("2017/08/24");
                    holder.deal_close.setText("待付款");
                    break;
                case 2:
                    holder.canel.setVisibility(View.GONE);
                    holder.fukuan.setVisibility(View.GONE);
                    holder.deal_close.setText("待发货");
                    break;
                case 3:
                    holder.canel.setVisibility(View.VISIBLE);
                    holder.fukuan.setVisibility(View.VISIBLE);
                    holder.fukuan.setText("查看订单");
                    holder.title.setText("2017/08/24");
                    holder.deal_close.setText("已发货");
                    break;
            }
            //设置Tag
            convertView.setTag(holder);
        } else {
            //获取Tag并给holder设置
            holder = (ViewHolder) convertView.getTag();
        }
        holder.price.setText(list.get(i).getPrice());
        holder.all_price.setText(list.get(i).getAll_price());
        holder.content.setText(list.get(i).getContent());
        holder.name.setText(list.get(i).getName());
        Glide.with(context).load(list.get(i).getImage_url()).into(holder.image_url);
        holder.num.setText("X"+list.get(i).getNum());
        return convertView;

    }
    class ViewHolder {
        TextView price, all_price, name, content,num,title,deal_close;
        ImageView image_url;
        Button canel,fukuan;
    }
}
