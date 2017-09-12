package com.example.yaodaojia.yaodaojia.control.activity.mine;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.base.BaseActivity;
import com.example.yaodaojia.yaodaojia.control.activity.MainActivity;
import com.example.yaodaojia.yaodaojia.model.http.bean.Register_Bean;
import com.example.yaodaojia.yaodaojia.model.http.http.OkHttp;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * /**
 * 项目名称: 药到家
 * 类描述:
 * 创建人: XI
 * 创建时间: 2017/8/30 0030 14:53
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class Setting_Activity extends BaseActivity {

    @BindView(R.id.mine_setting_name_change)
    RelativeLayout mineSettingNameChange;

    @BindView(R.id.mine_setting_pwd_change)
    RelativeLayout mineSettingPwdChange;
    @BindView(R.id.mine_setting_cancel)
    ImageView mineSettingCancel;
    @BindView(R.id.mine_Setting_img_change)
    ImageView mineSettingImgChange;
    @BindView(R.id.mine_Setting_log_out)
    RelativeLayout mineSettingLogOut;
    private SharedPreferences mShared;
    private SharedPreferences.Editor mEditor;
    private String names;
    private EditText et;
    private Dialog dia;

    @Override
    public int getLayout() {
        return R.layout.mine_setting;
    }

    @Override
    public void initView() {
        mShared = getSharedPreferences("login",MODE_PRIVATE);
        mEditor = mShared.edit();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.mine_setting_cancel, R.id.mine_Setting_img_change, R.id.mine_setting_name_change, R.id.mine_Setting_log_out, R.id.mine_setting_pwd_change})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_setting_cancel:
                finish();
                break;
            case R.id.mine_Setting_img_change:

                break;
            case R.id.mine_setting_name_change:
                View v = LayoutInflater.from(Setting_Activity.this).inflate(R.layout.setting_name_change,null);
                dia = new AlertDialog.Builder(Setting_Activity.this).setTitle("修改昵称").setView(v).create();
                dia.show();
                et = v.findViewById(R.id.setting_name);
                Button btn = v.findViewById(R.id.setting_name_btn);
                et.setText(mShared.getString("name", ""));
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        initName_Change();
                    }
                });
                break;
            case R.id.mine_Setting_log_out:
                mEditor.remove("token");
                mEditor.commit();
                Toast.makeText(this, "注销成功", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(Setting_Activity.this,MainActivity.class);
                startActivity(in);
                finish();
                break;
            case R.id.mine_setting_pwd_change:
                Intent ina = new Intent(Setting_Activity.this, Setting_Pwd_Activity.class);
                startActivity(ina);
                break;

        }
    }

    private void initName_Change() {
        Map<String,String> map = new HashMap<>();
        map.put("token",mShared.getString("token",""));
        map.put("nickname",et.getText().toString().trim());
        Log.d("Setting_Activity", et.getText().toString().trim());
        OkHttp.postAsync("http://api.googlezh.com/v1/person/nickname", map, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Log.d("Setting_Activity", result);
                Gson gson = new Gson();
                Register_Bean register_bean = gson.fromJson(result, Register_Bean.class);
                if(register_bean.isSuccess()){
                    Toast.makeText(Setting_Activity.this, register_bean.getData(), Toast.LENGTH_SHORT).show();
                    mEditor.putString("name",et.getText().toString().trim());
                    mEditor.commit();
                    dia.dismiss();
                }else {
                    Toast.makeText(Setting_Activity.this, register_bean.getData(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
