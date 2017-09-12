package com.example.yaodaojia.yaodaojia.control.activity.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.base.BaseActivity;
import com.example.yaodaojia.yaodaojia.model.http.bean.Register_Bean;
import com.example.yaodaojia.yaodaojia.model.http.http.OkHttp;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * /**
 * 项目名称: 药到家
 * 类描述:
 * 创建人: XI
 * 创建时间: 2017/8/30 0030 14:59
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class Setting_Pwd_Activity extends BaseActivity {

    @BindView(R.id.mine_setting_pwd_old)
    EditText mineSettingPwdOld;
    @BindView(R.id.mine_setting_pwd_new)
    EditText mineSettingPwdNew;
    @BindView(R.id.mine_setting_pwd_sure)
    Button mineSettingPwdSure;
    @BindView(R.id.iv_mine_setting_back)
    ImageView ivMineSettingBack;
    private SharedPreferences mShared;
    private SharedPreferences.Editor mEditor;
    private String old;
    private String news;


    @Override
    public int getLayout() {
        return R.layout.mine_setting_pwd;
    }

    @Override
    public void initView() {
        mShared = getSharedPreferences("login", MODE_PRIVATE);
        old = mineSettingPwdOld.getText().toString().trim();
        news = mineSettingPwdNew.getText().toString().trim();

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }


    @OnClick({R.id.mine_setting_pwd_sure,R.id.iv_mine_setting_back})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.mine_setting_pwd_sure:
                if (old.equals(mShared.getString("pwd", "")) && old != null) {
                    Toast.makeText(this, "原来的密码不正确或者不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (news.length() < 6 && news.length() > 20) {

                        Toast.makeText(this, "新密码的长度必须大于6位并且大于20位", Toast.LENGTH_SHORT).show();
                    } else {
                        initChange();
                    }
                }
                break;
            case R.id.iv_mine_setting_back:
                Setting_Pwd_Activity.this.finish();
                break;
        }


    }

    private void initChange() {
        Map<String, String> map = new HashMap<>();
        map.put("token", mShared.getString("token", ""));
        map.put("password", mineSettingPwdOld.getText().toString());
        map.put("new_password", mineSettingPwdNew.getText().toString());
        OkHttp.postAsync("http://api.googlezh.com/v1/register/register_pwd", map, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Log.d("Setting_Pwd_Activity", result);
                Gson gson = new Gson();
                Register_Bean register_bean = gson.fromJson(result, Register_Bean.class);
                if (register_bean.isSuccess()) {
                    Toast.makeText(Setting_Pwd_Activity.this, register_bean.getData(), Toast.LENGTH_SHORT).show();
                    mEditor.remove("token");
                    mEditor.commit();
                    Intent in = new Intent(Setting_Pwd_Activity.this, LoginActivity.class);
                    startActivity(in);
                } else {
                    Toast.makeText(Setting_Pwd_Activity.this, register_bean.getData(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
