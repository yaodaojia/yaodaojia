package com.example.yaodaojia.yaodaojia.control.activity.mine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.base.BaseActivity;
import com.example.yaodaojia.yaodaojia.model.http.bean.Trading_Record_Bean;
import com.example.yaodaojia.yaodaojia.model.http.http.OkHttp;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

public class TradingRecordActivity extends BaseActivity {

    @BindView(R.id.trading_record_list)
    ListView tradingRecordList;
    @BindView(R.id.trading_record_wu)
    TextView tradingRecordWu;
    private List<Trading_Record_Bean.DataBean> mList = new ArrayList<>();
    private SharedPreferences mShared;

    @Override
    public int getLayout() {
        return R.layout.activity_trading_record;
    }

    @Override
    public void initView() {
        mShared = getSharedPreferences("login", MODE_PRIVATE);
    }

    @Override
    public void initData() {
        OkHttp.getAsync("http://api.googlezh.com/v1/person/trans"+"?token="+mShared.getString("token", ""), new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Log.d("TradingRecordActivity", result);
                Gson gson = new Gson();
                Trading_Record_Bean bean = gson.fromJson(result, Trading_Record_Bean.class);
                if (bean.isSuccess()) {

                    tradingRecordList.setAdapter(new Trading_Record_Adapter());

                } else {
                    tradingRecordList.setVisibility(View.GONE);
                    tradingRecordWu.setVisibility(View.VISIBLE);
                    Toast.makeText(TradingRecordActivity.this, bean.getData(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    class Trading_Record_Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.isEmpty() ? 0 : mList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (view == null) {
                view = LayoutInflater.from(TradingRecordActivity.this).inflate(R.layout.trading_record_item, null);
                holder.time = view.findViewById(R.id.trading_record_item_time);
                holder.text = view.findViewById(R.id.trading_record_item_text);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            Trading_Record_Bean.DataBean bean = mList.get(i);
            if (bean.getStatus() == 0) {
                holder.text.setText("转入+" + bean.getMoney());
            } else {
                holder.text.setText("转出-" + bean.getMoney());
            }
            holder.time.setText(bean.getAdd_time() + "");
            return view;
        }

        class ViewHolder {
            private TextView time, text;
        }
    }
}
