package com.example.yaodaojia.yaodaojia.control.activity.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.control.activity.MainActivity;
import com.example.yaodaojia.yaodaojia.model.http.bean.Login_Bean;
import com.example.yaodaojia.yaodaojia.model.http.http.OkHttp;
import com.example.yaodaojia.yaodaojia.util.Utils_Host;
import com.google.gson.Gson;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int MSG_AUTH_COMPLETE = 0;
    private static final int MSG_AUTH_ERROR = 1;
    private static final int MSG_AUTH_CANCEL = 2;
    private Button bt_login;
    private ImageView login_back;
    private ImageView weixin;
    private ImageView zhufubao;
    private EditText et_phone;
    private EditText et_password;
    private TextView forget_password;
    private TextView register;
    private String phone;
    private String password;
    private UMShareAPI mShareAPI;
    private FragmentManager man;
    private FragmentTransaction tra;
    private SharedPreferences mShared;
    private SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setListener();
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    bt_login.setBackgroundColor(Color.rgb(202, 202, 197));
                } else {
                    String AN = et_password.getText().toString();
                    int length = AN.length();
                    if (length > 0 ) {
                        bt_login.setBackgroundResource(R.mipmap.mine_setting_pwd_btn);
                    }
                }
            }
        });
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    bt_login.setBackgroundColor(Color.rgb(202, 202, 197));
                } else {
                    String AP = et_password.getText().toString();
                    int length=AP.length();
                    if (length >0) {
                        bt_login.setBackgroundResource(R.mipmap.mine_setting_pwd_btn);
                    }
                }
            }
        });
    }



    private void setListener() {
        bt_login.setOnClickListener(this);
        login_back.setOnClickListener(this);
        weixin.setOnClickListener(this);
        zhufubao.setOnClickListener(this);
        register.setOnClickListener(this);
        forget_password.setOnClickListener(this);
    }

    private void initView() {
        mShared = getSharedPreferences("login", MODE_PRIVATE);
        mEditor = mShared.edit();
        Intent in = getIntent();
        man = getSupportFragmentManager();
        mShareAPI = UMShareAPI.get(this);
        bt_login = (Button) findViewById(R.id.login_bt_denlu);//登录按钮
        login_back = (ImageView) findViewById(R.id.login_iv_back);//登录返回
        weixin = (ImageView) findViewById(R.id.login_iv_weixin);//微信登录
        zhufubao = (ImageView) findViewById(R.id.login_iv_zhifubao);//支付宝登录
        et_phone = (EditText) findViewById(R.id.login_et_phone);//输入手机号
        et_password = (EditText) findViewById(R.id.login_et_password);//输入密码
        forget_password = (TextView) findViewById(R.id.login_tv_forgetpassword);//忘记密码
        register = (TextView) findViewById(R.id.login_tv_register);//注册按钮
        et_phone.setText(in.getStringExtra("mobile"));
        et_password.setText(in.getStringExtra("pwd"));
    }



    @Override
    public void onClick(View view) {
        password = et_password.getText().toString();
        phone = et_phone.getText().toString();
        switch (view.getId()) {
            case R.id.login_bt_denlu:
                if(phone.isEmpty()){
                    Toast.makeText(this, "请输入您的手机号", Toast.LENGTH_SHORT).show();
                } else if (phone.length() < 1 && phone.length() > 11) {
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.isEmpty()) {
                        Toast.makeText(this, "请输入您的密码", Toast.LENGTH_SHORT).show();
                    } else if (password.length() < 6 && password.length() > 20) {
                        Toast.makeText(this, "密码最少6位 最多20位，请重新输入", Toast.LENGTH_SHORT).show();
                    } else {

                        initLogin();


                    }
                }
                break;
            case R.id.login_iv_weixin:
                SHARE_MEDIA platform = SHARE_MEDIA.WEIXIN;
                mShareAPI.doOauthVerify(this, platform, umAuthListener);
                mShareAPI = UMShareAPI.get(this);
                mShareAPI.getPlatformInfo(LoginActivity.this, platform, umAuthListener);
//                tra = man.beginTransaction();
//                tra.replace(R.id.main_frame, new Mine_Fragment());
//                tra.commit();
                finish();

                break;
            case R.id.login_iv_zhifubao:

                break;
            case R.id.login_tv_forgetpassword:
                Intent intent_forgetpassword = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                intent_forgetpassword.putExtra("phones",phone);
                startActivity(intent_forgetpassword);
                break;
            case R.id.login_iv_back:
                LoginActivity.this.finish();
                break;
            case R.id.login_tv_register:
                Intent intent_register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent_register);
                break;

            default:
                break;
        }
    }

    private void initLogin() {
        Map<String,String> map = new HashMap<>();
        map.put("mobile",phone);
        map.put("password",password);
        OkHttp.postAsync(Utils_Host.host+"v1/login/login_do", map, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Log.d("LoginActivity", result);
                Gson gson = new Gson();
                Login_Bean register_bean = gson.fromJson(result, Login_Bean.class);
                if(register_bean.isSuccess()){
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

//                    tra = man.beginTransaction();
//                    tra.replace(R.id.main_frame, new Mine_Fragment());
                    Intent in = new Intent(LoginActivity.this, MainActivity.class);
                    mEditor.putString("token",register_bean.getData().getToken());
                    mEditor.putString("over_token",String.valueOf(register_bean.getData().getOver_token()));
                    mEditor.putString("pwd",password);
                    mEditor.putString("name",register_bean.getData().getNickname());
                    mEditor.putString("MyPhone",register_bean.getData().getMobile());
                    mEditor.commit();
                    startActivity(in);
//                    tra.commit();
                    finish();

                }else {
                    Toast.makeText(LoginActivity.this, "手机号未注册或密码不正确", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Toast.makeText( getApplicationContext(), "Authorize start", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText( getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
//            SharedPreferences sp = getSharedPreferences("persional_info",MODE_PRIVATE);
//            SharedPreferences.Editor editor = sp.edit();
//            editor.putString("unionid",data.get("unionid"));
//            editor.putString("screen_name",data.get("screen_name"));
//            editor.putString("gender",data.get("gender"));
//            editor.putString("profile",data.get("profile_image_url"));
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "您手机暂无安装微信客户端，请安装！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get( this ).onActivityResult( requestCode, resultCode, data);
    }
}
