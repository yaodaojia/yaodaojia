package com.example.yaodaojia.yaodaojia.control.activity.shop_car;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.base.BaseActivity;
import com.example.yaodaojia.yaodaojia.control.activity.mine.AddressAdministration_goods_Activity;
import com.example.yaodaojia.yaodaojia.control.activity.zhifubao.AuthResult;
import com.example.yaodaojia.yaodaojia.control.activity.zhifubao.PayResult;
import com.example.yaodaojia.yaodaojia.control.activity.zhifubao.util.OrderInfoUtil2_0;
import com.example.yaodaojia.yaodaojia.model.http.bean.CarData;
import com.example.yaodaojia.yaodaojia.util.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by axi on 2017/9/7.
 */

public class Shopcart_order_confirmation extends BaseActivity implements IWXAPIEventHandler {
    @BindView(R.id.shopcart_confir_cancel)
    ImageView shopcartConfirCancel;
    @BindView(R.id.tv_new_address)
    TextView tvNewAddress;
    @BindView(R.id.shopcart_confir_address_name)
    TextView shopcartConfirAddressName;
    @BindView(R.id.shopcart_confir_address)
    LinearLayout shopcartConfirAddress;
    @BindView(R.id.shopcart_confir_time)
    TextView shopcartConfirTime;
    @BindView(R.id.shopcart_confir_payfor_text)
    TextView shopcartConfirPayforText;
    @BindView(R.id.shopcart_confir_payfor)
    LinearLayout shopcartConfirPayfor;
    @BindView(R.id.lv_confirmation)
    ListView lvConfirmation;
    @BindView(R.id.shopcart_confir_goods_send_money)
    TextView shopcartConfirGoodsSendMoney;
    @BindView(R.id.shopcart_confir_beizhu)
    EditText shopcartConfirBeizhu;
    @BindView(R.id.shopcart_confir_sure)
    Button shopcartConfirSure;
    private SharedPreferences mShared;
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
                        Toast.makeText(Shopcart_order_confirmation.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(Shopcart_order_confirmation.this, "支付失败", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Shopcart_order_confirmation.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(Shopcart_order_confirmation.this,
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
    //微信
    private PayReq req;
    private IWXAPI api;
    private List<CarData> listAll;
    private MyConfirmationAdapter adapter;
    private String name;
    private String price;
    private String num;
    private String symptom;
    private List<CarData> list;
    private String img;

    @Override
    public int getLayout() {
        return R.layout.shopcart_order_confirmation;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        Intent intent = getIntent();
        name = intent.getStringExtra("goods_name");
        img = intent.getStringExtra("goods_img");
        price = intent.getStringExtra("goods_price");
        num = intent.getStringExtra("goods_num");
        symptom = intent.getStringExtra("goods_symptom");
        req = new PayReq();
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
        mShared = getSharedPreferences("order", MODE_PRIVATE);
        long time = System.currentTimeMillis();
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        // 取得小时：
        int mHour = mCalendar.get(Calendar.HOUR);
        // 取得分钟：
        int mMinuts = mCalendar.get(Calendar.MINUTE);
        shopcartConfirTime.setText((time + (mMinuts + 30)) + "");
    }

    @Override
    public void initData() {
        list = new ArrayList<>();
        CarData data = new CarData();
        data.setGoodEffect(symptom);
        data.setGoodImage(img);
        data.setGoodName(name);
        data.setGoodNum(num);
        data.setGoodPrice(price + "");
        adapter = new MyConfirmationAdapter(getApplication(), list);
        lvConfirmation.setAdapter(adapter);
    }

    @Override
    public void initListener() {

    }


    @OnClick({R.id.shopcart_confir_cancel, R.id.shopcart_confir_address, R.id.shopcart_confir_payfor, R.id.shopcart_confir_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shopcart_confir_cancel:
                finish();
                break;
            case R.id.shopcart_confir_address:
                Intent in = new Intent(Shopcart_order_confirmation.this, AddressAdministration_goods_Activity.class);
                startActivityForResult(in, 1);
                break;
            case R.id.shopcart_confir_payfor:
                showSheetDialog();
                break;
            case R.id.shopcart_confir_sure:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String text = null;
            if (bundle != null)
                text = bundle.getString("second");
            Log.d("text", text);
            shopcartConfirPayforText.setText(text);
        }
    }

    private void showSheetDialog() {
        View view = LayoutInflater.from(Shopcart_order_confirmation.this).inflate(
                R.layout.payfor_dialog, null);

        final Dialog dialog = new Dialog(Shopcart_order_confirmation.this, R.style.Theme_Design_BottomSheetDialog);
        dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.onWindowAttributesChanged(wl);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Button btnCamera = (Button) view.findViewById(R.id.btn_to_zhifubao);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopcartConfirPayforText.setText("支付宝支付");
                zhifubaopay();
                dialog.dismiss();


            }
        });
        Button btnPhoto = view.findViewById(R.id.btn_to_weixin);
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopcartConfirPayforText.setText("微信支付");
                sendPayReq();
                dialog.dismiss();

            }
        });
        Button btnBalance = view.findViewById(R.id.btn_to_yue);
        btnBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopcartConfirPayforText.setText("余额支付");
                dialog.dismiss();
            }
        });
        Button btnCancel = view.findViewById(R.id.btn_to_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 支付宝支付业务
     */
    public void zhifubaopay() {
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
                PayTask alipay = new PayTask(Shopcart_order_confirmation.this);
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
}
