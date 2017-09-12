package com.example.yaodaojia.yaodaojia.control.activity.mine;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.base.BaseActivity;
import com.example.yaodaojia.yaodaojia.model.http.bean.Register_Bean;
import com.example.yaodaojia.yaodaojia.model.http.bean.YanZhengBean;
import com.example.yaodaojia.yaodaojia.model.http.http.MyCallBack;
import com.example.yaodaojia.yaodaojia.model.http.http.OkHttp;
import com.example.yaodaojia.yaodaojia.model.http.http.Parsing;
import com.example.yaodaojia.yaodaojia.model.http.http.ParsingImple;
import com.example.yaodaojia.yaodaojia.util.MyCountDownTime;
import com.example.yaodaojia.yaodaojia.util.Utils_Host;
import com.example.yaodaojia.yaodaojia.model.http.http.OkHttp;
import com.example.yaodaojia.yaodaojia.util.MyCountDownTime;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;

public class ForgetPasswordActivity extends BaseActivity {
    @BindView(R.id.forget_password_back)
    ImageView forgetPasswordBack;
    @BindView(R.id.register_et_phone)
    EditText registerEtPhone;
    @BindView(R.id.register_bt_code)
    Button registerBtCode;
    @BindView(R.id.register_et_code)
    EditText registerEtCode;
    @BindView(R.id.register_et_password)
    EditText registerEtPassword;
    @BindView(R.id.register_et_newpassword)
    EditText registerEtNewpassword;
    @BindView(R.id.register_bt_login)
    Button registerBtLogin;
    private MyCountDownTime myCountDownTime;// 用于验证码倒计时
    private Handler mHandler;//用于执行耗时操作
    private String path= Utils_Host.host+"v1/goods/send";
    private String s;
    private String phone_str;
    private String codestr;
    private String password_str;
    private String trim;
    private Intent in;

    @Override
    public int getLayout() {
        return R.layout.activity_register_password;
    }

    @Override
    public void initView() {
        in = getIntent();
        registerEtPhone.setText(in.getStringExtra("phones"));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.forget_password_back, R.id.register_bt_code, R.id.register_bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.forget_password_back:
                ForgetPasswordActivity.this.finish();
                break;
            case R.id.register_bt_code:
                s = registerEtPhone.getText().toString();
                if(s.isEmpty()){
                    Toast.makeText(this,"您必须输入手机号才能获取验证码",Toast.LENGTH_LONG).show();
                }else {
                    getCode(s);
                }

                break;
            case R.id.register_bt_login:
                initForget();
                break;
        }
    }

    private void initForget() {
        phone_str = registerEtPhone.getText().toString().trim();
        codestr = registerEtCode.getText().toString().trim();
        password_str = registerEtPassword.getText().toString().trim();
        trim = registerEtNewpassword.getText().toString().trim();
        if(phone_str.isEmpty()){
            Toast.makeText(this, "请输入您的手机号", Toast.LENGTH_SHORT).show();
        }else if(phone_str.length()<1&& phone_str.length()>11){
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
        }else {
            if(codestr.isEmpty()){
                Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
            }else if(codestr.length()!=6){
                Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
            }else {
                if(password_str.isEmpty()){
                    Toast.makeText(this, "请输入您的密码", Toast.LENGTH_SHORT).show();
                }else if(password_str.length()<6&& password_str.length()>20){
                    Toast.makeText(this, "密码最少6位 最多20位，请重新输入", Toast.LENGTH_SHORT).show();
                }else {
                    if(trim.equals(password_str)){
                        initPwd();
                    }else {
                        Toast.makeText(this, "请与您上一次输入的密码一致", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private void initPwd() {
        Parsing par = new ParsingImple();
        Map<String,String> map = new HashMap<>();
        map.put("mobile",phone_str);
        map.put("code",codestr);
        map.put("password",trim);
        Log.d("ForgetPasswordActivity", "phone" + phone_str);
        Log.d("ForgetPasswordActivity", "code" + codestr);
        Log.d("ForgetPasswordActivity", "pwd" + trim);
        OkHttp.postAsync(Utils_Host.host+"v1/register/register_pwd", map, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Log.d("ForgetPasswordActivity", result);
                Gson gson = new Gson();
                Register_Bean register_bean = gson.fromJson(result, Register_Bean.class);
                if (register_bean.isSuccess()) {
                    Toast.makeText(ForgetPasswordActivity.this, register_bean.getData(), Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                    in.putExtra("mobile", phone_str);
                    in.putExtra("pwd", password_str);
                    startActivity(in);
                    finish();
                } else {
                    Toast.makeText(ForgetPasswordActivity.this, register_bean.getData(), Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void initData(String message) {
        OkHttp.getAsync(path+"?tel="+message, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }
            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson=new Gson();
                YanZhengBean list=gson.fromJson(result,YanZhengBean.class);
                String message= list.message;
            }
        });
    }
    /**
     *
     * @Description: TODO 获取验证码，模拟网络访问耗时操作
     * @author Sunday
     * @date 2016年3月16日
     * @param
     */
    private void getCode(String phoneNum){
        initData(phoneNum);
        Toast.makeText(this,"获取验证码中，请稍等", Toast.LENGTH_SHORT).show();
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "" +
                        "获取验证码成功，稍后请查看手机信息", Toast.LENGTH_SHORT).show();
                startTimer();
            }
        }, 2000);
    }
    /**
     *
     * @Description: TODO 发送成功后，开始倒计时
     * @throws @author
     *             Sunday
     * @date 2016年3月16日
     */
    private void startTimer() {
        if (null == myCountDownTime) {
                myCountDownTime = new MyCountDownTime(60000,1000,registerBtCode,"重新发送");
        }
        myCountDownTime.start();
    }
    /**
     *
     * @Description: TODO 一般发送失败时，需要重置Button状态
     * @author Sunday
     * @date 2016年3月16日
     */
    private void cancelTimer(){
        if(null != myCountDownTime){
            myCountDownTime.cancel();
            myCountDownTime.onFinish();
        }
    }
}


