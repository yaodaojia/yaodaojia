package com.example.yaodaojia.yaodaojia.control.activity.mine;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.base.BaseActivity;
import com.example.yaodaojia.yaodaojia.model.http.bean.Register_Bean;
import com.example.yaodaojia.yaodaojia.model.http.http.OkHttp;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import okhttp3.Request;

public class ModificationAddressActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.modifition_back)
    ImageView modifitionBack;
    @BindView(R.id.modificition_tv_save)
    TextView modificitionTvSave;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_danyuan)
    EditText etDanyuan;
    private Intent intent;
    private String name;
    private String phone;
    private String address;
    private EditText et_name, et_phone, et_address, et_danyuan;
    private TextView save;
    private ImageView back;
    private SharedPreferences mShared, mShare;
    private String danyuan;
    @Override
    public int getLayout() {
        return R.layout.activity_modification_address;
    }


    public void initData() {
        et_address.setText( intent.getStringExtra("address"));
        et_name.setText(intent.getStringExtra("name"));
        et_phone.setText(intent.getStringExtra("phone"));
        et_name.setSelection(intent.getStringExtra("name").length());
    }

    @Override
    public void initListener() {
        save.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    public void initView() {

    }

    public void getData() {
        intent = getIntent();
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        address = intent.getStringExtra("address");
        danyuan = et_danyuan.getText().toString().trim();
        mShare = getSharedPreferences("login", MODE_PRIVATE);
        mShared = getSharedPreferences("location", MODE_PRIVATE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.modificition_tv_save:
                getData();
                if (name.isEmpty()) {
                    Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                } else if (phone.isEmpty()) {
                    Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                } else if (address.isEmpty()) {
                    Toast.makeText(this, "地址不能为空", Toast.LENGTH_SHORT).show();
                } else if (danyuan.isEmpty()) {
                    Toast.makeText(this, "详细地址不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    initSave();
                }
                break;
            case R.id.modifition_back:
                ModificationAddressActivity.this.finish();
                break;
        }
    }

    private void initSave() {
        Map<String, String> map = new HashMap<>();
        map.put("token", mShare.getString("token", ""));
        map.put("address_id", intent.getStringExtra("address_id"));
        map.put("type", String.valueOf(1));
        map.put("province_name", intent.getStringExtra("province"));
        map.put("city_name", intent.getStringExtra("city"));
        map.put("district_name", intent.getStringExtra("distric"));
        map.put("consignee", intent.getStringExtra("name"));
        map.put("mobile", intent.getStringExtra("phone"));
        map.put("is_default", String.valueOf(false));
        map.put("lng", String.valueOf(intent.getFloatExtra("lng", 0)));
        map.put("lat", String.valueOf(intent.getFloatExtra("lat", 0)));
        OkHttp.postAsync("http://api.googlezh.com//v1/person/upAddress", map, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
                Register_Bean register_bean = gson.fromJson(result, Register_Bean.class);
                if (register_bean.isSuccess()) {
                    Toast.makeText(ModificationAddressActivity.this, register_bean.getData(), Toast.LENGTH_SHORT).show();
                    showNormalDialog();
                } else {
                    Toast.makeText(ModificationAddressActivity.this, register_bean.getData(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showNormalDialog() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);
        normalDialog.setMessage("确定要保存吗?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent mIntent = new Intent();
                        String name = et_name.getText().toString().trim();
                        String phoneNum = et_phone.getText().toString().trim();
                        String address = et_address.getText().toString().trim();
                        mIntent.putExtra("name", name);
                        mIntent.putExtra("phone", phoneNum);
                        mIntent.putExtra("address", address);
                        mIntent.putExtra("danyuan", danyuan);
                        // 设置结果，并进行传送
                        setResult(Activity.RESULT_OK, mIntent);
                        Toast.makeText(ModificationAddressActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        finish();
                        dialog.dismiss();
                        ModificationAddressActivity.this.finish();
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        dialog.dismiss();
                    }
                });
        // 显示
        normalDialog.show();
    }

}
