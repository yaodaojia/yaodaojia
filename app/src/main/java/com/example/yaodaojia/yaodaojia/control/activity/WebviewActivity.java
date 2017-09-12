package com.example.yaodaojia.yaodaojia.control.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yaodaojia.yaodaojia.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebviewActivity extends AppCompatActivity {

    @BindView(R.id.tv_deal)
    TextView tvDeal;
    @BindView(R.id.deal_back)
    ImageView dealBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        try {
            //读取本地文件
            InputStream inputStream = null;
            inputStream = getResources().getAssets().open("deal.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
            StringBuffer stringBuffer = new StringBuffer();
            String str = null;
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str + "\r\n");
            }
            str = stringBuffer.toString();
            tvDeal.setText(str);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @OnClick(R.id.deal_back)
    public void onViewClicked() {
      finish();
    }
}
