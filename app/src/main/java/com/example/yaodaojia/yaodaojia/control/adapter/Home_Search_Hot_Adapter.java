package com.example.yaodaojia.yaodaojia.control.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.model.http.bean.Home_Search_Hot_Recycler_Bean;

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


public class Home_Search_Hot_Adapter extends RecyclerView.Adapter<Home_Search_Hot_Adapter.Home_Search_Hot_ViewHolder> {
    private Context mContxet;
    private List<Home_Search_Hot_Recycler_Bean.DataBean> mList;
    private MyItemClickListener mItemClickListener;
    private LayoutInflater inflater;

    public Home_Search_Hot_Adapter(Context mContxet, List<Home_Search_Hot_Recycler_Bean.DataBean> mList) {
        this.mContxet = mContxet;
        this.mList = mList;
        this.inflater = LayoutInflater.from(mContxet);
    }
    public void getData(List<Home_Search_Hot_Recycler_Bean.DataBean> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public Home_Search_Hot_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.home_search_hot_item,null);
        Home_Search_Hot_ViewHolder holder = new Home_Search_Hot_ViewHolder(v,mItemClickListener);
        return holder;
    }
    /**
     * 设置Item点击监听
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener){
        this.mItemClickListener = listener;
    }
    @Override
    public void onBindViewHolder(Home_Search_Hot_ViewHolder holder, int position) {
        Home_Search_Hot_Recycler_Bean.DataBean goods = mList.get(position);
        if(goods.getKeyword().equals("")){
        }else {
            holder.mName.setText(goods.getKeyword());
        }
        Log.d("Home_Search_Hot_Adapter", goods.getKeyword());

    }

    @Override
    public int getItemCount() {
        return mList.isEmpty()?0:mList.size();
    }

    class Home_Search_Hot_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mName;
        private MyItemClickListener myItemClickListener;
        public Home_Search_Hot_ViewHolder(View v,MyItemClickListener myItemClickListener) {
            super(v);
            mName = (TextView) v.findViewById(R.id.home_search_item);
            this.myItemClickListener = myItemClickListener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(myItemClickListener != null){
                myItemClickListener.onItemClick(v,getPosition());
            }
        }
    }
    public interface MyItemClickListener {
        public void onItemClick(View view,int postion);
    }
}
