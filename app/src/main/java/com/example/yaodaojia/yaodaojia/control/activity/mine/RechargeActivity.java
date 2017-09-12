package com.example.yaodaojia.yaodaojia.control.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.util.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* 充值界面
* */
public class RechargeActivity extends AppCompatActivity implements IWXAPIEventHandler {

    @BindView(R.id.rb_yinglian)
    RadioButton rbYinglian;
    String mode = "01";
    String tranNum = "670257093383220381900";
    @BindView(R.id.rb_weixin)
    RadioButton rbWeixin;
    private PayReq req;
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        ButterKnife.bind(this);
        req=new PayReq();
        api = com.tencent.mm.opensdk.openapi.WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(),this);
    }

    @OnClick({R.id.rb_yinglian, R.id.rb_weixin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_yinglian:
                UPPayAssistEx.startPayByJAR(this, PayActivity.class, null, null, tranNum, mode);
                break;
            case R.id.rb_weixin:
                sendPayReq();
                break;

        }


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    private void sendPayReq() {
        api.registerApp(Constants.APP_ID);
        api.sendReq(req);
    }
    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int code = resp.errCode;
           switch (code) {
                case 0:
                    Toast.makeText(this,"支付成功",Toast.LENGTH_SHORT).show();
                    break;
                case -1:
                    Toast.makeText(this,"支付失败",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case -2:
                    Toast.makeText(this,"支付取消",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                default:
                    Toast.makeText(this,"支付失败",Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                    break;
            }
        }
    }
}
