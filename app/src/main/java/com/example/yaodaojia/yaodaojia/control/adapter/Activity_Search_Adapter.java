package com.example.yaodaojia.yaodaojia.control.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yaodaojia.yaodaojia.App;
import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.control.activity.home.Goods_Detail_Activity;
import com.example.yaodaojia.yaodaojia.model.http.bean.Activity_Search_Bean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * /**
 * 项目名称: 城市通
 * 类描述:
 * 创建人: XI
 * 创建时间: 2017/8/17 0017 20:44
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class Activity_Search_Adapter extends RecyclerView.Adapter<Activity_Search_Adapter.Activity_Search_ViewHolder> {
    private Context mContxet;
    private List<Activity_Search_Bean.DataBean> mList;

    public Activity_Search_Adapter(Context mContxet, List<Activity_Search_Bean.DataBean> mList) {
        this.mContxet = mContxet;
        this.mList = mList;
    }
    public void getData(List<Activity_Search_Bean.DataBean> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public Activity_Search_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContxet).inflate(R.layout.home_fragment_goods_item,null);
        Activity_Search_ViewHolder holder = new Activity_Search_ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(Activity_Search_ViewHolder holder, int position) {
        final Activity_Search_Bean.DataBean goods = mList.get(position);
        Picasso.with(App.activity).load(goods.getG_img()+"").into(holder.mImg);
        holder.mName.setText(goods.getG_name()+"  "+goods.getG_size());
        holder.mPrice.setText("¥"+goods.getReal_price());
//        Log.d("Home_Fragment_Goods_Ada", goods.getOriginal_img());
        holder.mLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(mContxet, Goods_Detail_Activity.class);
                in.putExtra("img",goods.getG_img());
                in.putExtra("name",goods.getG_name());
                in.putExtra("price",goods.getReal_price());
                in.putExtra("goods_id",goods.getG_id());
                mContxet.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.isEmpty()?0:mList.size();
    }

    class Activity_Search_ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImg;
        private TextView mName,mPrice;
        private LinearLayout mLin;
        public Activity_Search_ViewHolder(View v) {
            super(v);
            mImg = (ImageView) v.findViewById(R.id.home_fragment_goods_img);
            mName = (TextView) v.findViewById(R.id.home_fragment_goods_namr);
            mPrice = (TextView) v.findViewById(R.id.home_fragment_goods_price);
            mLin = v.findViewById(R.id.home_fragemnt_goods_item);
        }
    }
}
