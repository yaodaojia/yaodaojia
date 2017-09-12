package com.example.yaodaojia.yaodaojia.control.activity.mine;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.base.BaseActivity;
import com.example.yaodaojia.yaodaojia.model.http.bean.Register_Bean;
import com.example.yaodaojia.yaodaojia.model.http.http.MyCallBack;
import com.example.yaodaojia.yaodaojia.model.http.http.Parsing;
import com.example.yaodaojia.yaodaojia.model.http.http.ParsingImple;
import com.example.yaodaojia.yaodaojia.util.Utils_Host;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class NewAddressActivity extends BaseActivity {

    @BindView(R.id.address_iv_back)
    ImageView addressIvBack;
    @BindView(R.id.new_address_save)
    TextView newAddressSave;
    @BindView(R.id.new_address_et_name)
    EditText newAddressEtName;
    @BindView(R.id.new_address_et_phone)
    EditText newAddressEtPhone;
    @BindView(R.id.new_address_et_add)
    EditText newAddressEtAdd;
    @BindView(R.id.new_address_et_danyuan)
    EditText newAddressEtDanyuan;
    private SharedPreferences mShared,mShare;
    private String name;
    private String phone;
    private String address;
    private String danyuan;



    @Override
    public int getLayout() {
        return R.layout.activity_new_address;
    }

    @Override
    public void initView() {
        mShared = getSharedPreferences("location", MODE_PRIVATE);
        mShare = getSharedPreferences("login",MODE_PRIVATE);
        newAddressEtAdd.setText(mShared.getString("location_add_name",""));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    private void initAdd() {
        name = newAddressEtName.getText().toString().trim();
        phone = newAddressEtPhone.getText().toString().trim();
        address = newAddressEtAdd.getText().toString().trim();
        danyuan = newAddressEtDanyuan.getText().toString().trim();
        if(name.isEmpty()){
            Toast.makeText(this, "请填写收件人姓名", Toast.LENGTH_SHORT).show();
        }else if(phone.isEmpty()){
            Toast.makeText(this, "请填写您的手机号", Toast.LENGTH_SHORT).show();
        }else if(address.isEmpty()){
            Toast.makeText(this, "请填写您的地址", Toast.LENGTH_SHORT).show();
        }else if(danyuan.isEmpty()){
            Toast.makeText(this, "请填写您的详细地址", Toast.LENGTH_SHORT).show() ;
        }else {
            initNew();
        }
    }

    /**
     *  mEditor.putString("latitude", String.valueOf(item.getLatLonPoint().getLatitude()));
     mEditor.putString("longitude",String.valueOf(item.getLatLonPoint().getLongitude()));
     mEditor.putString("province",item.getProvinceName());
     mEditor.putString("city",item.getCityName());
     mEditor.putString("distric",item.getDirection());
     *
     */
    private void initNew() {
        Parsing par = new ParsingImple();
        Map<String,String> map = new HashMap<>();
        map.put("token",mShare.getString("token",""));
        map.put("add_content",danyuan);
        map.put("province_name",mShared.getString("province",""));
        map.put("city_name",mShared.getString("city",""));
        map.put("district_name",mShared.getString("distric",""));
        map.put("consignee",name);
        map.put("mobile",phone);
        map.put("address",mShared.getString("location_add_name",""));
        map.put("is_default", String.valueOf(false));
        map.put("lng", String.valueOf(mShared.getFloat("longitude",0)));
        map.put("lat", String.valueOf(mShared.getFloat("latitude",0)));
        par.post(Utils_Host.host+"v1/person/addAddress", map, new MyCallBack() {
            @Override
            public void onSuccess(String strSuccess) throws UnsupportedEncodingException {
                Log.d("NewAddressActivity", strSuccess);
                Gson gson = new Gson();
                Register_Bean register_bean = gson.fromJson(strSuccess, Register_Bean.class);
                if(register_bean.isSuccess()){
                    showNormalDialog();

                }else {
                    Toast.makeText(NewAddressActivity.this, register_bean.getData(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(String strError) {

            }
        });
    }

    @OnClick({R.id.address_iv_back, R.id.new_address_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.address_iv_back:
                NewAddressActivity.this.finish();
                break;
            case R.id.new_address_save:
                initAdd();
                break;
        }
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
                        Intent intent = new Intent();
                        intent.putExtra("newName", name);
                        intent.putExtra("newPhoneNum", phone);
                        intent.putExtra("addAddress", address);
                        intent.putExtra("newAddress", danyuan);
                        setResult(Activity.RESULT_OK, intent);
                        Toast.makeText(NewAddressActivity.this, "保存成功", Toast.LENGTH_LONG).show();
                        finish();
                        dialog.dismiss();
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
        normalDialog.show();
    }
}
