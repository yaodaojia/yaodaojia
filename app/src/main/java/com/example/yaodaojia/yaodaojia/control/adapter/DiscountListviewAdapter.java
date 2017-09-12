package com.example.yaodaojia.yaodaojia.control.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.model.http.bean.DiscountCouponBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaodaojia on 2017/8/23.
 */

public class DiscountListviewAdapter extends BaseAdapter{
    List<DiscountCouponBean> list;
    private List<String> mlist=new ArrayList<>();
    Context context;
    int fposition;
    public DiscountListviewAdapter(List<DiscountCouponBean> list, Context context, int fposition) {
        this.list=list;
        this.context=context;
        this.fposition=fposition;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        //判断当前视图是否为空
        if (convertView == null) {
            //实例化 holder 对象
            holder = new ViewHolder();
            //设置视图
            convertView = View.inflate(context, R.layout.discount_chiled_item, null);

            /**
             * 为holder类中的属性设置id
             */
            holder.backgroud=convertView.findViewById(R.id.iv_background);
            holder.price =convertView.findViewById(R.id.item_discounts_price);
            holder.full = convertView.findViewById(R.id.item_discounts_full);
            holder.scope =convertView.findViewById(R.id.item_discounts_scope);
            holder.time = convertView.findViewById(R.id.item_discounts_time);
            holder.use = convertView.findViewById(R.id.item_discounts_use);
            holder.tag=convertView.findViewById(R.id.item_discounts_tag);
            /**
             * 判断类别  切换背景 和 颜色
             */
            switch (fposition) {
                case 0:
                    holder.backgroud.setImageResource(R.mipmap.weishiyong);
                    holder.price.setTextColor(context.getResources().getColor(R.color.colorDiscountsoOrange));
                    holder.full.setTextColor(context.getResources().getColor(R.color.colorDiscountsoOrange));
                    holder.use.setTextColor(context.getResources().getColor(R.color.colorDiscountsoOrange));
                    holder.tag.setTextColor(context.getResources().getColor(R.color.colorDiscountsoOrange));
                    break;
                case 1:
                    holder.backgroud.setImageResource(R.mipmap.yishiyong);
                    holder.price.setTextColor(context.getResources().getColor(R.color.colorDiscountsBule));
                    holder.full.setTextColor(context.getResources().getColor(R.color.colorDiscountsBule));
                    holder.use.setTextColor(context.getResources().getColor(R.color.colorDiscountsBule));
                    holder.tag.setTextColor(context.getResources().getColor(R.color.colorDiscountsBule));
                    break;
                case 2:
                    holder.backgroud.setImageResource(R.mipmap.yiguoqi);
                    holder.price.setTextColor(context.getResources().getColor(R.color.colorDiscountsTabCheck));
                    holder.full.setTextColor(context.getResources().getColor(R.color.colorDiscountsTabCheck));
                    holder.use.setTextColor(context.getResources().getColor(R.color.colorDiscountsTabCheck));
                    holder.tag.setTextColor(context.getResources().getColor(R.color.colorDiscountsTabCheck));
                    break;
            }
            //设置Tag
            convertView.setTag(holder);
        } else {
            //获取Tag并给holder设置
            holder = (ViewHolder) convertView.getTag();
        }

        /**
         *  开始赋值
         */

        holder.price.setText(list.get(position).getPrice());
        holder.full.setText(list.get(position).getFull());
        holder.scope.setText(list.get(position).getScope());
        holder.time.setText(list.get(position).getTime());
        mlist.add("未使用");
        mlist.add("已使用");
        mlist.add("已过期");
        for (int i=0;i<mlist.size();i++){
            holder.use.setText(mlist.get(fposition));
        }
        return convertView;
    }
    class ViewHolder {
        TextView price, full, scope, time,tag;
        Button use;
        ImageView backgroud;
    }
}
