package com.example.yaodaojia.yaodaojia.control.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.model.http.bean.ArticalBean;

import java.util.List;

/**
 * Created by yaodaojia on 2017/8/23.
 */

public class ArticalAdapter extends RecyclerView.Adapter<ArticalAdapter.MyViewHolder>{
    private Context context;
    private List<ArticalBean.DataBean> list;
    public ArticalAdapter(Context context, List<ArticalBean.DataBean> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.artical, parent,
                false));
        return holder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(list.get(position).title);
        holder.time.setText(list.get(position).add_time);

    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title,time;
        public MyViewHolder(View view) {
            super(view);
            title=view.findViewById(R.id.artical_title);
            time=view.findViewById(R.id.artical_time);
        }
    }
}
