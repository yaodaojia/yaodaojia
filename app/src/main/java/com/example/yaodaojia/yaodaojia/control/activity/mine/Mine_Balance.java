package com.example.yaodaojia.yaodaojia.control.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * /**
 * 项目名称: 城市通
 * 类描述:
 * 创建人: XI
 * 创建时间: 2017/8/23 0023 10:09
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class Mine_Balance extends BaseActivity {
    @BindView(R.id.mine_balance_money)
    TextView mineBalanceMoney;
    @BindView(R.id.balance_trading_record)
    RelativeLayout balanceRechargeRela;
    @BindView(R.id.mine_balance_top_up)
    RelativeLayout mineBalanceTopUp;
    @BindView(R.id.mine_balance_withdraw)
    RelativeLayout mineBalanceWithdraw;
    @BindView(R.id.iv_mine_balance_back)
    ImageView ivMineBalanceBack;

    @Override
    public int getLayout() {
        return R.layout.mine_balance;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }


    @OnClick({R.id.mine_balance_top_up, R.id.mine_balance_withdraw, R.id.balance_trading_record,R.id.iv_mine_balance_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_balance_top_up:
                //充值
                Intent in = new Intent(Mine_Balance.this, RechargeActivity.class);
                startActivity(in);
                break;
            case R.id.mine_balance_withdraw:
                //提现
                startActivity(new Intent(Mine_Balance.this, WithdrawActivity.class));
                break;
            case R.id.balance_trading_record:
                //交易记录
                startActivity(new Intent(Mine_Balance.this, TradingRecordActivity.class));
                break;
            case R.id.iv_mine_balance_back:
                Mine_Balance.this.finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

}
