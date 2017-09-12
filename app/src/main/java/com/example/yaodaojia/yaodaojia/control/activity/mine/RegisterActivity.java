package com.example.yaodaojia.yaodaojia.control.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.control.activity.WebviewActivity;
import com.example.yaodaojia.yaodaojia.model.http.bean.Register_Bean;
import com.example.yaodaojia.yaodaojia.model.http.bean.YanZhengBean;
import com.example.yaodaojia.yaodaojia.model.http.http.OkHttp;
import com.example.yaodaojia.yaodaojia.util.MyCountDownTime;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.tv_xianyi)
    TextView tvXianyi;
    private Button bt_code, bt_register;
    private EditText et_phone, et_password, et_code;
    private String codestr;
    private String password_str;
    private String phone_str;
    private CheckBox cb_xianyi;
    private MyCountDownTime myCountDownTime;// 用于验证码倒计时
    private Handler mHandler;//用于执行耗时操作
    private String path = "http://api.googlezh.com/v1/goods/send";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
        setListener();
    }

    private void initData(String message) {
        OkHttp.getAsync(path + "?tel=" + message, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
                YanZhengBean list = gson.fromJson(result, YanZhengBean.class);
                String str = list.message;

            }
        });
    }

    private void setListener() {

        bt_code.setOnClickListener(this);
        bt_register.setOnClickListener(this);
    }

    private void initView() {
        bt_code = (Button) findViewById(R.id.bt_code);//获取验证码
        et_phone = (EditText) findViewById(R.id.et_register_phone);//输入注册手机号
        et_password = (EditText) findViewById(R.id.et_password);//输入注册密码
        et_code = (EditText) findViewById(R.id.et_code);//输入验证码
        bt_register = (Button) findViewById(R.id.bt_register);//注册按钮
        cb_xianyi = (CheckBox) findViewById(R.id.cb_xianyi);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_code:
                if (et_phone.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "手机号正确,请输入验证码", Toast.LENGTH_SHORT).show();
                    String s = et_phone.getText().toString();
                    getCode(s);
                }
                break;
            case R.id.bt_register:
                phone_str = et_phone.getText().toString().trim();
                codestr = et_code.getText().toString().trim();
                password_str = et_password.getText().toString().trim();
                if (phone_str.isEmpty()) {
                    Toast.makeText(this, "请输入您的手机号", Toast.LENGTH_SHORT).show();
                } else if (phone_str.length() < 1 && phone_str.length() > 11) {
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                } else {
                    if (codestr.isEmpty()) {
                        Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                    } else if (codestr.length() != 6) {
                        Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                    } else {
                        if (password_str.isEmpty()) {
                            Toast.makeText(this, "请输入您的密码", Toast.LENGTH_SHORT).show();
                        } else if (password_str.length() < 6 && password_str.length() > 20) {
                            Toast.makeText(this, "密码最少6位 最多20位，请重新输入", Toast.LENGTH_SHORT).show();
                        } else {
                            if (cb_xianyi.isChecked()) {
                                //回家再说吧，看不见手机
                                initRegister();
                            } else {
                                Toast.makeText(this, "请阅读药到家用户协议", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                break;
        }
    }

    private void initRegister() {
        Map<String, String> map = new HashMap<>();
        map.put("tel", phone_str);
        map.put("password", password_str);
        map.put("code", codestr);
        Log.d("RegisterActivity", "phone" + map.get("tel"));
        Log.d("RegisterActivity", "password" + map.get("password"));
        Log.d("RegisterActivity", "code" + map.get("code"));
        OkHttp.postAsync("http://api.googlezh.com/v1/register/register_do", map, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Log.d("RegisterActivity", result);
                Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
                Register_Bean register_bean = gson.fromJson(result, Register_Bean.class);
                if (register_bean.isSuccess()) {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(RegisterActivity.this, LoginActivity.class);
                    in.putExtra("mobile", phone_str);
                    in.putExtra("pwd", password_str);
                    startActivity(in);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();

                }
            }
        });
//        OkHttp.postAsync("http://api.googlezh.com/v1/register/register_do", map, new OkHttp.DataCallBack() {
//            @Override
//            public void requestFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void requestSuccess(String result) throws Exception {
//                Log.d("RegisterActivity", result);
//            }
//        });
    }

    /**
     * @param
     * @Description: TODO 获取验证码，模拟网络访问耗时操作
     * @author Sunday
     * @date 2016年3月16日
     */
    private void getCode(String phoneNum) {
        initData(phoneNum);
        Toast.makeText(this, "获取验证码中，请稍等", Toast.LENGTH_SHORT).show();
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "获取验证码成功，稍后请查看手机信息", Toast.LENGTH_SHORT).show();
                startTimer();
            }
        }, 2000);
    }

    /**
     * @throws @author Sunday
     * @Description: TODO 发送成功后，开始倒计时
     * @date 2016年3月16日
     */
    private void startTimer() {
        if (null == myCountDownTime) {
            myCountDownTime = new MyCountDownTime(60000, 1000, bt_code, "重新发送");
        }
        myCountDownTime.start();
    }

    /**
     * @Description: TODO 一般发送失败时，需要重置Button状态
     * @author Sunday
     * @date 2016年3月16日
     */
    private void cancelTimer() {
        if (null != myCountDownTime) {
            myCountDownTime.cancel();
            myCountDownTime.onFinish();
        }
    }

    @OnClick(R.id.tv_xianyi)
    public void onViewClicked() {
        startActivity(new Intent(RegisterActivity.this, WebviewActivity.class));
    }
}
