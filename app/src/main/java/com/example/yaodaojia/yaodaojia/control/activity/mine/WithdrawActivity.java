package com.example.yaodaojia.yaodaojia.control.activity.mine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.yaodaojia.yaodaojia.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/*
* 提现
* */
public class WithdrawActivity extends AppCompatActivity {

    @BindView(R.id.iv_withdraw_back)
    ImageView ivWithdrawBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_withdraw_back)
    public void onViewClicked(View view) {
        switch(view.getId()){
            case R.id.iv_withdraw_back:
                WithdrawActivity.this.finish();
                break;
        }
    }
}
