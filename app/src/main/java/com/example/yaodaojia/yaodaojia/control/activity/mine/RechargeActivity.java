package com.example.yaodaojia.yaodaojia.control.activity.mine;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.control.activity.zhifubao.AuthResult;
import com.example.yaodaojia.yaodaojia.control.activity.zhifubao.PayResult;
import com.example.yaodaojia.yaodaojia.control.activity.zhifubao.util.OrderInfoUtil2_0;
import com.example.yaodaojia.yaodaojia.util.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
* 充值界面
* */
public class RechargeActivity extends AppCompatActivity implements IWXAPIEventHandler, View.OnClickListener {
    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2017081708241952";

    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "2017081708241952";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "2017081708241952";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEpAIBAAKCAQEAq73M/sScSic7EHNBT446oT8fGX/IQ5+cnVzoUBjnuez8V+9k3u64FdF38VeI84Sr5+MdT/k4+Fvsi1vahBB/gKLnp6YJQOFN3KPDnDcUxiwWliz0Kzc9l1ETgb0rl1bxBPOhKPCeyRCRDDzJnYPkOxR+FuOeFC5S4Z0tppyEZs/A3le2rf+Fw0ccVny/KD24Cj6eOT535xADlMz+FY86XVUc8tj+4eo6LcGqxsjeq0xM3oAglJjDQ8GGC7Fl2CSLK5T70FwljBkcEVrakDVb2zEgq10l4Xw4ZyWhRrtM2JSOrZmnYq2bi9Od0kotRj00qdYszOFm6Lk4rrrQDP4joQIDAQABAoIBACH6+N+DuUS2xGn50/dQtesHmTirXs16fDtqkZCfjOHtsPQZKsUtSIEQpAG9hxxfqL/F4RvcvNfvIDtmnjK5LPEfymQSZwxOR+CbCm4TO+oF+SYXmOwgDvCsmwZ1jnVwP1nydnWqPlCcFYbtVBr1aKgD4vrWaIWnsMBxIAIyP8QzjcVGz27VzrBI+vND9gCg+cdwjb+xGXSk5MRUW2n3uySVRNfrX4HJXMMFtk6tb9X/fiaPd+acaAUIw6FWmE7kDrr+0fYKXYnAzyqhDIhDAUkEydoPQJ19YR+yPizSbS0LTFtc2ZwC9hf0NnT165DuOcTHEMYaMGtKzwyoi3iMDUECgYEA4pOZv8f4FTnl2U7m15HLj9qPslNwe+Gcjbp7Z30w/Tao/w2kdKxaTS+268f7tcISt3DCOTI4OZMhukbNmwABS5bSGmz5mUeUtKOIo2AIOjRuJRpYDcg6gz9ITkZNOkMSh0xRdn+hLaPpiLrVCRxPHYSwN6v8tu1Rjqo3jjIQT2kCgYEAwgs9K61S9rlcTUVmncJVcYVrBggjwysNrhg1cM1BVXKiDzkWJdr3gOAQSYUsYxplli2G+KhHabztkWiR3kfuiydvxAOlwms0m1K69LCH6oKmu9xVzr2r8wHaZ6amOFeL/adYAVSESJMdWGjtV6y6Ss+bjWD1TN2W4ZpirgCbY3kCgYB5IKdlW6ZI0rcKQaAvRoYo1ZNUpj7QO4DSVAt8+/SgqgQWtlhg93MfKI8F5devB52x69opcauVPA8f4pIwdwQ6QNr5vaCCTT42g+acUM6VL0pjE35Xf9+oIsCo9/R5XToekrJKQ1kLaUGEEz2orTWudi0u1gc4VA7x0hX5MtJ9YQKBgQC0omG/Vwn0Q3l5NTHhihagJuMKKmmgNWP2vPKCn5BCSwQxx6KmesAMaIjA6Af5+Yi31Ing0EYqWIWO5xljIvMc9wlEH2EfK009ijYfsnXUCOvN1b7LRiXbgc4ezzPNHzJiXnW+Rz8dkFWxB3xqMZD6M2lKowe3FlBuk5lpAYelgQKBgQClPn08x1bywHR/V6zOEWEgmJGpoy6CwQgSYQR8mJvJGTnezhed9eDzdSn0TNeP4B4VdIqJwp4SIJBEnbN6R/9/UxAg2W7NFE1ApdMxNWeJUPZoX6RdsvUqee8UbYfC7CKVHiWFDkdn/iiVfAxlKsTb663NfDQ5H5A02uh7Trm+YA==";
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(RechargeActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(RechargeActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(RechargeActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(RechargeActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };
    String mode = "01";
    String tranNum = "670257093383220381900";
    @BindView(R.id.rb_weixin)
    RadioButton rbWeixin;
    private PayReq req;
    private IWXAPI api;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        ButterKnife.bind(this);
        req = new PayReq();
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
        back = (ImageView) findViewById(R.id.iv_recharge_bak);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i == R.id.rb_zhifubao) {
                    pay();
                } else if (i == R.id.rb_weixin) {
                    sendPayReq();
                }
            }
        });
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

    /**
     * 支付宝支付业务
     */
    public void pay() {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(RechargeActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int code = resp.errCode;
            switch (code) {
                case 0:
                    Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
                    break;
                case -1:
                    Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case -2:
                    Toast.makeText(this, "支付取消", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                default:
                    Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                    break;
            }
        }
    }

    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_recharge_bak:
                RechargeActivity.this.finish();
                break;
            case R.id.btn_to_weixin:
                sendPayReq();
                break;
        }
    }
}
