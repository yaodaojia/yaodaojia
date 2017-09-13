package com.example.yaodaojia.yaodaojia.control.activity.home;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.base.BaseActivity;
import com.example.yaodaojia.yaodaojia.control.activity.mine.LoginActivity;
import com.example.yaodaojia.yaodaojia.control.activity.shop_car.Shopcart_order_confirmation;
import com.example.yaodaojia.yaodaojia.model.http.bean.Goods_Detail_Bean;
import com.example.yaodaojia.yaodaojia.model.http.http.OkHttp;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * /**
 * 项目名称: 药到家
 * 类描述:
 * 创建人: XI
 * 创建时间: 2017/8/28 0028 18:34
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class Goods_Detail_Activity extends BaseActivity {

    @BindView(R.id.goods_img)
    ImageView goodsImg;
    @BindView(R.id.goods_name)
    TextView goodsName;
    @BindView(R.id.goods_money)
    TextView goodsMoney;
    @BindView(R.id.goods_count)
    TextView goodsCount;
    @BindView(R.id.goods_body)
    TextView goodsBody;
    @BindView(R.id.goods_instructions_name)
    TextView goodsInstructionsName;
    @BindView(R.id.goods_instructions_composition)
    TextView goodsInstructionsComposition;
    @BindView(R.id.goods_instructions_attending)
    TextView goodsInstructionsAttending;
    @BindView(R.id.goods_instructions_specification)
    TextView goodsInstructionsSpecification;
    @BindView(R.id.goods_instructions_usage)
    TextView goodsInstructionsUsage;
    @BindView(R.id.goods_instructions_adverse)
    TextView goodsInstructionsAdverse;
    @BindView(R.id.goods_instructions_taboo)
    TextView goodsInstructionsTaboo;
    @BindView(R.id.goods_instructions_matters)
    TextView goodsInstructionsMatters;
    @BindView(R.id.goods_detail_back)
    ImageView goodsDetailBack;
    @BindView(R.id.tv_goods_buy)
    TextView tvGoodsBuy;
    @BindView(R.id.goods_tv_Now)
    TextView goodsTvNow;
    private Intent in;
    private int num = 1;
    private SharedPreferences msp;
    private Goods_Detail_Bean goods_detail_bean;

    @Override
    public int getLayout() {
        return R.layout.goods_detail;
    }

    @Override
    public void initView() {
        msp = this.getSharedPreferences("login", Goods_Detail_Activity.MODE_PRIVATE);
        msp.edit();
        in = getIntent();
        goodsName.setText(in.getStringExtra("name"));
        Glide.with(this).load(in.getStringExtra("img")).into(goodsImg);
        goodsMoney.setText("¥ " + in.getIntExtra("price", 0));

    }

    private void initNet() {
        OkHttp.getAsync("http://api.googlezh.com//v1/goods/detail"+"?goods_id="+String.valueOf(in.getIntExtra("goods_id", 0)), new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Log.d("Goods_Detail_Activity", result);
                Gson gson = new Gson();
                goods_detail_bean = gson.fromJson(result, Goods_Detail_Bean.class);

                if (goods_detail_bean.getData().getG_name().isEmpty()) {
                    goodsInstructionsName.setText("暂无信息");
                } else {
                    goodsInstructionsName.setText(goods_detail_bean.getData().getG_name());
                }
                if (goods_detail_bean.getData().getAdver().isEmpty()) {
                    goodsInstructionsAdverse.setText("暂无信息");
                } else {
                    goodsInstructionsAdverse.setText(goods_detail_bean.getData().getAdver());
                }
                if (goods_detail_bean.getData().getG_symptom().isEmpty()) {
                    goodsInstructionsAttending.setText("暂无信息");
                } else {
                    goodsInstructionsAttending.setText(goods_detail_bean.getData().getG_symptom());
                }
                if (goods_detail_bean.getData().getG_component().isEmpty()) {
                    goodsInstructionsComposition.setText("暂无信息");
                } else {
                    goodsInstructionsComposition.setText(goods_detail_bean.getData().getG_component());
                }
                if (goods_detail_bean.getData().getNote().isEmpty()) {
                    goodsInstructionsMatters.setText("暂无信息");
                } else {
                    goodsInstructionsMatters.setText(goods_detail_bean.getData().getNote());
                }
                if (goods_detail_bean.getData().getG_size().isEmpty()) {
                    goodsInstructionsSpecification.setText("暂无信息");
                } else {
                    goodsInstructionsSpecification.setText(goods_detail_bean.getData().getG_size());
                }
                if (goods_detail_bean.getData().getG_usage().isEmpty()) {
                    goodsInstructionsUsage.setText("暂无信息");
                } else {
                    goodsInstructionsUsage.setText(goods_detail_bean.getData().getG_usage());
                }
                if (String.valueOf(goods_detail_bean.getData().getContra()).equals("null")) {
                    goodsInstructionsTaboo.setText("暂无信息");
                } else {
                    goodsInstructionsTaboo.setText(String.valueOf(goods_detail_bean.getData().getContra()));
                }

            }


        });
    }

    @Override
    public void initData() {
        initNet();
    }

    @Override
    public void initListener() {

    }


    @OnClick({R.id.goods_detail_back, R.id.tv_goods_buy, R.id.goods_tv_Now})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.goods_detail_back:
                Goods_Detail_Activity.this.finish();
                break;
            case R.id.tv_goods_buy://加入购物车
                if(msp.getString("token", "").isEmpty()){
                    Intent inm = new Intent(Goods_Detail_Activity.this, LoginActivity.class);
                    startActivity(inm);
                }else {
                    showDioLog();
                }
                break;
            case R.id.goods_tv_Now:
                if(msp.getString("token", "").isEmpty()){
                    Intent inm = new Intent(Goods_Detail_Activity.this, LoginActivity.class);
                    startActivity(inm);
                }else {
                    Intent in = new Intent(Goods_Detail_Activity.this,Shopcart_order_confirmation.class);
                    in.putExtra("goods_name",goods_detail_bean.getData().getG_name());
                    in.putExtra("goods_img",goods_detail_bean.getData().getImg());
                    in.putExtra("goods_price",goods_detail_bean.getData().getShop_price()+"");
                    in.putExtra("goods_num",goods_detail_bean.getData().getG_size());
                    in.putExtra("goods_symptom",goods_detail_bean.getData().getG_symptom());
                    startActivity(in);
                }
                break;
        }
     }

    public void showDioLog() {
        View view = this.getLayoutInflater().inflate(
                R.layout.addcar_diolog, null);
        final Dialog dialog = new Dialog(this, R.style.Theme_Design_BottomSheetDialog);
        dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = this.getWindowManager().getDefaultDisplay().getHeight();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.onWindowAttributesChanged(wl);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        final TextView tv_num = view.findViewById(R.id.goods_tv_num);
        TextView tv_name = view.findViewById(R.id.goods_tv_name);
        TextView tv_price = view.findViewById(R.id.goods_tv_price);
        ImageView tv_jia = view.findViewById(R.id.goods_tv_jia);
        ImageView tv_jian = view.findViewById(R.id.goods_tv_jian);
        ImageView iv_image = view.findViewById(R.id.goods_iv_img);
        tv_name.setText(in.getStringExtra("name"));
        tv_price.setText("¥ " + in.getIntExtra("price", 0));
        Glide.with(this).load(in.getStringExtra("img")).into(iv_image);
        Button bt_buy = view.findViewById(R.id.bt_buy);
        bt_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initBuy();
                Toast.makeText(Goods_Detail_Activity.this, "添加成功", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        tv_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num++;
                tv_num.setText(num + "");
            }
        });
        tv_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (num > 1) {
                    num--;
                    tv_num.setText(num + "");
                }
            }
        });
    }

    private void initBuy() {
        Map<String, String> map = new HashMap();
        map.put("token", msp.getString("token", ""));
        Log.i("TAG", msp.getString("token", ""));
        map.put("goods_id", String.valueOf(in.getIntExtra("goods_id", 0)));
        map.put("goods_name", in.getStringExtra("name"));
        map.put("market_price", String.valueOf(in.getIntExtra("price", 0)));
        map.put("goods_number", String.valueOf(num));
        map.put("goods_img", in.getStringExtra("img"));
        map.put("goods_content", in.getStringExtra("content"));

        OkHttp.postAsync("http://api.googlezh.com/v1/car/insert", map, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
            }
        });
    }
}
