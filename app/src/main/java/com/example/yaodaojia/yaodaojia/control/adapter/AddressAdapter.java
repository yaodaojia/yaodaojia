package com.example.yaodaojia.yaodaojia.control.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.model.http.bean.AddressBean;

import java.util.List;

/**
 * Created by yaodaojia on 2017/8/29.
 */

public class AddressAdapter extends BaseAdapter {
    private Context mcontext;
    private List<AddressBean.DataBean> mlist;
    private LayoutInflater inflater;


    public AddressAdapter(Context context, List<AddressBean.DataBean> list) {
        this.mcontext = context;
        this.mlist = list;
        this.inflater = LayoutInflater.from(context);
    }

    public void getData(List<AddressBean.DataBean> mlist) {
        this.mlist = mlist;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int i) {
        return mlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setItem(List<AddressBean.DataBean> item) {
        mlist.addAll(item);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder1 holder = null;

        //判断当前视图是否为空
        if (convertView == null) {
            // 按当前所需的样式，确定new的布局
            convertView = convertView.inflate(mcontext,R.layout.address_lv_item,
                    null);
            holder = new ViewHolder1();
            holder.image_url = convertView.findViewById(R.id.address_iv_head);
            holder.name = convertView.findViewById(R.id.address_tv_name);
            holder.phone = convertView.findViewById(R.id.address_tv_phone);
            holder.address = convertView.findViewById(R.id.address_tv);
            convertView.setTag(holder);

        } else {

                    holder = (ViewHolder1) convertView.getTag();

            }



//        Glide.with(mcontext).load(mlist.get(i).getImage_url()).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.image_url) {
//            @Override
//            protected void setResource(Bitmap resource) {
//                RoundedBitmapDrawable ciDrawable = RoundedBitmapDrawableFactory.create(mcontext.getResources(), resource);
//                ciDrawable.setCircular(true);
//                holder.image_url.setImageDrawable(ciDrawable);
//            }
//        });

        // Glide.with(mcontext).load(mlist.get(i).getImage_url()).into(holder.image_url);

                holder.name.setText(mlist.get(i).getConsignee());
                holder.address.setText(mlist.get(i).getProvince_name() + mlist.get(i).getCity_name() + mlist.get(i).getDistrict_name());
                holder.phone.setText(mlist.get(i).getMobile());


        return convertView;
    }


    class ViewHolder1 {
        TextView name, phone, address;
        ImageView image_url;
    }




}
