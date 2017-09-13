package com.example.yaodaojia.yaodaojia.control.activity.shop_car;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.model.http.bean.CarData;

import java.util.List;
/**
 * Created by yaodaojia on 2017/9/12.
 */

public class MyConfirmationAdapter extends BaseAdapter{
    private Context mContext;
    private List<CarData> mData;
    public MyConfirmationAdapter(Context context, List<CarData> data) {
        this.mContext=context;
        this.mData=data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder =null;
        //判断当前视图是否为空
        if (convertView == null) {
            // 按当前所需的样式，确定new的布局
            convertView = convertView.inflate(mContext,R.layout.confirmation_item,
                    null);
            holder = new ViewHolder();
            holder.image_url = convertView.findViewById(R.id.shopcart_confir_goods_img);
            holder.name = convertView.findViewById(R.id.shopcart_confir_goods_name);
            holder.gongxiao = convertView.findViewById(R.id.shopcart_confir_goods_gongxiao);
            holder.money = convertView.findViewById(R.id.shopcart_confir_goods_money);
            holder.count = convertView.findViewById(R.id.shopcart_confir_goods_count);
            convertView.setTag(holder);
        } else {
            holder =(ViewHolder) convertView.getTag();
        }
        holder.count.setText(mData.get(i).getGoodNum());
        holder.gongxiao.setText(mData.get(i).getGoodEffect());
        Glide.with(mContext).load(mData.get(i).getGoodImage()).into(holder.image_url);
        holder.money.setText(mData.get(i).getGoodPrice()+"");
        holder.name.setText(mData.get(i).getGoodName());
        return convertView;
    }
    class ViewHolder{
        TextView name,gongxiao,money,count;
        ImageView image_url;
    }
}
