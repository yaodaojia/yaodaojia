package com.example.yaodaojia.yaodaojia.control.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.control.activity.MainActivity;
import com.example.yaodaojia.yaodaojia.control.activity.shop_car.Shopcart_order_confirmation;
import com.example.yaodaojia.yaodaojia.model.http.bean.ShoppingCarBean;
import com.example.yaodaojia.yaodaojia.model.http.http.OkHttp;
import com.example.yaodaojia.yaodaojia.util.Utils_Host;
import com.google.gson.Gson;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

import static android.content.Context.MODE_PRIVATE;
import static com.example.yaodaojia.yaodaojia.R.id.shop_lv_view;

/**
 * Created by axi on 2017/8/9.
 */
public class ShoppingCart_Fragment extends Fragment implements View.OnClickListener {
    private String shop_bayall_text;
    private CheckBox checkall;
    private TextView allprice;
    private TextView shop_editbutton;
    private ListView shop_listview;
    private String path = Utils_Host.host+"v1/car/carlist";
    private String mpath = Utils_Host.host+"v1/car/update";
    private ShoppingCarBean shoppingCarBean;
    private boolean flag = false;
    private Button shop_bayall;
    private String json;
    private double sum = 0.00;
    private shop_lv_adapter adapter;
    private String shop_editbutton_text;
    private View view;
    private LinearLayout empty_view;
    private LinearLayout shop_content;
    private LinearLayout shop_bianji;
    private RelativeLayout rl_shop;
    private List<ShoppingCarBean.DataBean> carBean;
    private Button button;
    private boolean mType = false;
    private double m1;
    private boolean mflag;
    private ProgressBar progress;
    private  TextView jiazai;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.shopcar_frag_layout, null);
        empty_view = view.findViewById(R.id.empty_view);
        shop_content = view.findViewById(R.id.shop_content);
        shop_bianji = view.findViewById(R.id.shop_bianji);
        rl_shop = view.findViewById(R.id.rl_shop);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }
    private void initDelete(int position) {
        Map<String, String> map = new HashMap<>();
        map.put("cart_id", String.valueOf(carBean.get(position).cart_id));
        map.put("status", String.valueOf(1));
        OkHttp.postAsync(mpath, map, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {

            }
        });
    }

    private void initView() {
        allprice = getView().findViewById(R.id.allprice);
        checkall = getView().findViewById(R.id.checkall);
        shop_bayall = getView().findViewById(R.id.shop_buyall);
        shop_editbutton = getView().findViewById(R.id.shop_editbutton);
        shop_listview = getView().findViewById(shop_lv_view);
        button = getView().findViewById(R.id.button);
        progress = getView().findViewById(R.id.progress);
        jiazai=getView().findViewById(R.id.tv_jiazai);
        checkall.setOnClickListener(this);
        shop_editbutton.setOnClickListener(this);
        shop_bayall.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    public void initData() {
        Map<String, String> map = new HashMap<>();
        SharedPreferences msp = getActivity().getSharedPreferences("login", MODE_PRIVATE);
        map.put("token", msp.getString("token", ""));
        OkHttp.postAsync(path, map, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                json = result;
                showData(json);
            }
        });
    }

    //展示数据
    public void showData(String json) {
        Gson gson = new Gson();
        shoppingCarBean = gson.fromJson(json, ShoppingCarBean.class);
        carBean = shoppingCarBean.data;
        adapter = new shop_lv_adapter(getActivity(), carBean);
        shop_listview.setAdapter(adapter);
        if (carBean.size() != 0) {
            progress.setVisibility(View.GONE);
            rl_shop.setVisibility(View.VISIBLE);
            empty_view.setVisibility(View.GONE);
            shop_content.setVisibility(View.VISIBLE);
            jiazai.setVisibility(View.GONE);
        } else {
            progress.setVisibility(View.GONE);
            rl_shop.setVisibility(View.GONE);
            empty_view.setVisibility(View.VISIBLE);
            shop_content.setVisibility(View.GONE);
            jiazai.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkall:
                flag = checkall.isChecked();
                double f1 = 0;
                for (int i = 0; i < adapter.getSelect().size(); i++) {
                    adapter.getSelect().set(i, flag);
                }
                if (flag == true) {
                    for (int i = 0; i < carBean.size(); i++) {
                        if (i == 0) {
                            sum = 0.00;
                        }
                        sum = sum + adapter.list.get(i).goods_number * Double.parseDouble(carBean.get(i).market_price);
                        BigDecimal b1 = new BigDecimal(Double.toString(sum));
                        BigDecimal b2 = new BigDecimal(Double.toString(1));
                        f1 = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    }
                    allprice.setText("总计:￥" + f1);
                } else if (flag == false) {
                    sum = 0.00;
                    allprice.setText("总计:￥" + sum);
                }
                shop_listview.setAdapter(adapter);
                break;
            case R.id.shop_editbutton:
                shop_editbutton_text = shop_editbutton.getText().toString();
                shop_bayall_text = shop_bayall.getText().toString();
                if (shop_editbutton_text.equals("编辑")) {
                    shop_editbutton.setText("完成");
                    mflag = checkall.isChecked();
                    if (mflag == true) {
                        for (int i = 0; i < carBean.size(); i++) {
                            if (i == 0) {
                                sum = 0.00;
                            }
                            sum = sum + adapter.list.get(i).goods_number * Double.parseDouble(carBean.get(i).market_price);
                            BigDecimal b1 = new BigDecimal(Double.toString(sum));
                            BigDecimal b2 = new BigDecimal(Double.toString(1));
                            f1 = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            m1 = f1;
                        }
                        allprice.setText("总计:￥" + m1);
                    } else if (mflag == false) {
                        sum = 0.00;
                        allprice.setText("总计:￥" + sum);
                    }
                    mType = !mType;
                    adapter.setItem(mType);
                    adapter.notifyDataSetChanged();
                    allprice.setVisibility(View.GONE);
                    if (shop_bayall_text.equals("结算")) {
                        shop_bayall.setText("删除");
                    }
                } else if (shop_editbutton_text.equals("完成")) {
                    shop_editbutton.setText("编辑");
                    if (mflag == true) {
                        for (int i = 0; i < carBean.size(); i++) {
                            if (i == 0) {
                                sum = 0.00;
                            }
                            sum = sum + adapter.list.get(i).goods_number * Double.parseDouble(carBean.get(i).market_price);
                            BigDecimal b1 = new BigDecimal(Double.toString(sum));
                            BigDecimal b2 = new BigDecimal(Double.toString(1));
                            f1 = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            m1 = f1;
                        }
                        allprice.setText("总计:￥" + m1);
                    } else if (mflag == false) {
                        sum = 0.00;
                        allprice.setText("总计:￥" + sum);
                    }
                    mType = false;
                    adapter.setItem(mType);
                    allprice.setVisibility(View.VISIBLE);
                    if (shop_bayall_text.equals("删除")) {
                        shop_bayall.setText("结算");
                    }
                }
                break;
            case R.id.shop_buyall:
                shop_bayall_text = shop_bayall.getText().toString();
                if (shop_bayall_text.equals("删除")) {
                    for (int i = 0; i < carBean.size(); i++) {
                        sum = sum - adapter.list.get(i).goods_number * Double.parseDouble(carBean.get(i).market_price);
                        if (mType = true) {
                            adapter.setItem(mType);
                            initDelete(i);
                            carBean.remove(i);
                            i--;
                            adapter.notifyDataSetChanged();
                        }
                    }
                    if (carBean.size() == 0) {
                        rl_shop.setVisibility(View.GONE);
                        shop_content.setVisibility(View.GONE);
                        shop_bianji.setVisibility(View.GONE);
                        progress.setVisibility(View.GONE);
                    }
                    allprice.setText("总计:￥" + sum);
                } else if (shop_bayall_text.equals("结算")) {
                    Intent in = new Intent(getContext(), Shopcart_order_confirmation.class);
                    startActivity(in);
                }
                break;
            case R.id.button:
                startActivity(new Intent(getActivity(), MainActivity.class));
                break;
        }
    }

    // 适配器
    class shop_lv_adapter extends BaseAdapter {
        private Context context;
        public List<ShoppingCarBean.DataBean> list;
        private LinkedList<Boolean> linkedList = new LinkedList<>();
        private boolean type = false;
        public shop_lv_adapter(Context context, List<ShoppingCarBean.DataBean> list) {
            super();
            this.context = context;
            this.list = list;
            // 初始化
            for (int i = 0; i < list.size(); i++) {
                getSelect().add(false);
            }
        }

        public void setItem(boolean type) {
            this.type = type;
        }


        private List<Boolean> getSelect() {
            return linkedList;
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
        public View getView(final int position, View convertView, final ViewGroup parent) {
            final ViewHolder vh;
            if (convertView == null) {
                convertView = convertView.inflate(context, R.layout.shop_lv_item, null);
                vh = new ViewHolder();
                vh.shop_checkbox = convertView.findViewById(R.id.shop_checkbox);
                vh.shop_goodimg = convertView.findViewById(R.id.shop_goodimg);
                vh.shop_goodname = convertView.findViewById(R.id.shop_goodname);
                vh.shop_price = convertView.findViewById(R.id.shop_price);
                vh.shop_count = convertView.findViewById(R.id.shop_num);
                vh.shop_effect = convertView.findViewById(R.id.shop_effect);
                vh.layout_num = convertView.findViewById(R.id.layout_num);
                vh.cart_add = convertView.findViewById(R.id.cart_add);
                vh.cart_reduce = convertView.findViewById(R.id.cart_reduce);
                vh.cart_num = convertView.findViewById(R.id.cart_num);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            if (type == true) {
                vh.layout_num.setVisibility(View.VISIBLE);
                vh.shop_count.setVisibility(View.GONE);
            } else {
                vh.shop_count.setVisibility(View.VISIBLE);
                vh.layout_num.setVisibility(View.GONE);
            }
            vh.shop_goodname.setText(list.get(position).goods_name + "");
            Glide.with(getActivity()).load(list.get(position).goods_img + "").into(vh.shop_goodimg);
            vh.shop_count.setText("X" + list.get(position).goods_number);
            vh.shop_price.setText("￥" + list.get(position).market_price);
            vh.shop_checkbox.setChecked(linkedList.get(position));
            vh.shop_effect.setText("功效:" + list.get(position).goods_content);
            vh.cart_num.setText(list.get(position).goods_number + "");
            // 不能用onCheckChangedListner()复用的时候
            vh.shop_checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linkedList.set(position, !linkedList.get(position));
                    if (linkedList.contains(false)) {
                        checkall.setChecked(false);
                    } else {
                        checkall.setChecked(true);
                    }
                    if (vh.shop_checkbox.isChecked() == true) {
                        sum = sum + carBean.get(position).goods_number * Double.parseDouble(list.get(position).market_price);
                    } else if (vh.shop_checkbox.isChecked() == false) {
                        sum = sum - carBean.get(position).goods_number * Double.parseDouble(list.get(position).market_price);
                    }
                    allprice.setText("总计:￥" + sum);
                }
            });
            final ShoppingCarBean.DataBean dataBean = list.get(position);
            vh.cart_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int count = dataBean.goods_number;
                    count++;
                    dataBean.goods_number = count;
                    vh.cart_num.setText(count + "");
                }
            });
            vh.cart_reduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int count = dataBean.goods_number;
                    if (count > 1) {
                        count--;
                        dataBean.goods_number = count;
                        vh.cart_num.setText(count + "");
                    }
                }
            });
            notifyDataSetChanged();// 刷新
            return convertView;
        }

        public class ViewHolder {
            CheckBox shop_checkbox;
            ImageView shop_goodimg;
            TextView shop_goodname, shop_price, shop_count, shop_effect, cart_num;
            RelativeLayout layout_num;
            ImageView cart_reduce, cart_add;
        }
    }
}
