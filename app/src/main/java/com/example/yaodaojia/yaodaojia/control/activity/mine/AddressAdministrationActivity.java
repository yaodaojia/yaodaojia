package com.example.yaodaojia.yaodaojia.control.activity.mine;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.control.activity.home.Home_Location;
import com.example.yaodaojia.yaodaojia.control.adapter.AddressAdapter;
import com.example.yaodaojia.yaodaojia.model.http.bean.AddressBean;
import com.example.yaodaojia.yaodaojia.model.http.bean.Register_Bean;
import com.example.yaodaojia.yaodaojia.model.http.http.MyCallBack;
import com.example.yaodaojia.yaodaojia.model.http.http.OkHttp;
import com.example.yaodaojia.yaodaojia.model.http.http.Parsing;
import com.example.yaodaojia.yaodaojia.model.http.http.ParsingImple;
import com.example.yaodaojia.yaodaojia.swipelist.SwipeMenu;
import com.example.yaodaojia.yaodaojia.swipelist.SwipeMenuCreator;
import com.example.yaodaojia.yaodaojia.swipelist.SwipeMenuItem;
import com.example.yaodaojia.yaodaojia.swipelist.SwipeMenuListView;
import com.example.yaodaojia.yaodaojia.util.Utils_Host;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/*
* 地址管理
* */
public class AddressAdministrationActivity extends AppCompatActivity implements View.OnClickListener {
    private SwipeMenuListView listView;
    private ImageView back;
    private TextView address_tv;
    private static final int MODIFI_ADDRESS = 200;
    private static final int NEWMODIFI_ADDRESS = 100;
    private int currentModifiyAddressPosion;
    private String name;
    private String phone;
    private String address;
    private String danyuan;
    private AddressAdapter adapter;
    private SharedPreferences mShared, mShare;
    private Context context;
    private List<AddressBean.DataBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_address_administration);
        initView();
        setListener();
        initSwipe();
        initData();
    }

    private void initSwipe() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(context);
                deleteItem.setBackground(new ColorDrawable(Color.RED));
                deleteItem.setWidth(dp2px(80));
                deleteItem.setTitle("删除");
                deleteItem.setTitleColor(Color.WHITE);
                deleteItem.setTitleSize(20);
                menu.addMenuItem(deleteItem);
            }
        };
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int i, SwipeMenu menu, int index) {
                //index的值就是在SwipeMenu依次添加SwipeMenuItem顺序值，类似数组的下标。
                //从0开始，依次是：0、1、2、3...
                switch (index) {
                    case 0:
                        initDelete(i);

                        break;
                }

                // false : 当用户触发其他地方的屏幕时候，自动收起菜单。
                // true : 不改变已经打开菜单的样式，保持原样不收起。
                return false;
            }
        });
        // 监测用户在ListView的SwipeMenu侧滑事件。
        listView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int pos) {
                Log.d("位置:" + pos, "开始侧滑...");
            }

            @Override
            public void onSwipeEnd(int pos) {
                Log.d("位置:" + pos, "侧滑结束.");
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(AddressAdministrationActivity.this, ModificationAddressActivity.class);
                intent.putExtra("phone",list.get(i).getMobile()+"");
                intent.putExtra("address", list.get(i).getProvince_name() + list.get(i).getCity_name() + list.get(i).getDistrict_name()+"");
                intent.putExtra("province", list.get(i).getProvince_name()+"");
                intent.putExtra("city", list.get(i).getCity_name()+"");
                intent.putExtra("distric", list.get(i).getDistrict_name()+"");
                intent.putExtra("lat", mShare.getFloat("latitude", 0)+"");
                intent.putExtra("lng", mShare.getFloat("longitude", 0)+"");
                intent.putExtra("address_id", list.get(i).getAddress_id() + "");
                intent.putExtra("name", list.get(i).getConsignee()+"");
                currentModifiyAddressPosion=i;
                startActivityForResult(intent,MODIFI_ADDRESS);
            }
        });
    }

    private void initDelete(final int i) {
        Map<String, String> map = new HashMap<>();
        map.put("token", mShared.getString("token", ""));
        map.put("address_id", list.get(i).getAddress_id() + "");
        map.put("type", String.valueOf(2));
        map.put("province_name",list.get(i).getProvince_name()+"");
        map.put("city_name",list.get(i).getCity_name()+"");
        map.put("district_name",  list.get(i).getDistrict_name()+"");
        map.put("consignee", list.get(i).getConsignee()+"");
        map.put("mobile", list.get(i).getMobile()+"");
        map.put("is_default", String.valueOf(false));
        map.put("lng",  mShare.getFloat("longitude", 0)+"");
        map.put("lat",  mShare.getFloat("latitude", 0)+"");
        OkHttp.postAsync(Utils_Host.host+"v1/person/upAddress", map, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
                Register_Bean register_bean = gson.fromJson(result, Register_Bean.class);
                if (register_bean.isSuccess()) {
//                    showNormalDialog();
                    list.remove(i);
                    adapter.notifyDataSetInvalidated();
                        Toast.makeText(AddressAdministrationActivity.this, register_bean.getData(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddressAdministrationActivity.this, register_bean.getData(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(AddressAdministrationActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
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
        // 显示
        normalDialog.show();
    }

    private void setListener() {
        back.setOnClickListener(this);
        address_tv.setOnClickListener(this);
    }

    private void initView() {
        mShared = getSharedPreferences("login", MODE_PRIVATE);
        mShare = getSharedPreferences("location", MODE_PRIVATE);
        listView = (SwipeMenuListView) findViewById(R.id.listView);
        back = (ImageView) findViewById(R.id.address_back);
        address_tv = (TextView) findViewById(R.id.address_iv_editor);
    }

    private void initData() {
        Parsing par = new ParsingImple();
        Map<String, String> map = new HashMap<>();
        map.put("token", mShared.getString("token", ""));
        par.post(Utils_Host.host+"v1/person/alist", map, new MyCallBack() {
            @Override
            public void onSuccess(String strSuccess) throws UnsupportedEncodingException {
                Log.d("AddressAdministrationAc", strSuccess);
                Gson gson = new Gson();
                AddressBean addressBean = gson.fromJson(strSuccess, AddressBean.class);
                if (addressBean.isSuccess()) {
                    list = addressBean.getData();
                    list.addAll(addressBean.getData());
                    if (adapter == null) {
                        adapter = new AddressAdapter(AddressAdministrationActivity.this, list);
                        listView.setAdapter(adapter);
                    } else {
                        adapter.getData(list);
                    }
                }
            }

            @Override
            public void onError(String strError) {

            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.address_back:
                finish();
                break;
            case R.id.address_iv_editor:
                Intent intent = new Intent(AddressAdministrationActivity.this, Home_Location.class);
                startActivityForResult(intent,NEWMODIFI_ADDRESS);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case MODIFI_ADDRESS:
                if (resultCode == Activity.RESULT_OK) {
                    name = data.getStringExtra("name");
                    phone = data.getStringExtra("phone");
                    address = data.getStringExtra("address");
                    AddressBean.DataBean addressBean = (AddressBean.DataBean) adapter.getItem(currentModifiyAddressPosion);
                    addressBean.setConsignee(name);
                    addressBean.setMobile(phone);
                    addressBean.setCity_name(address);
                    adapter.notifyDataSetInvalidated();
                }
                break;
            case NEWMODIFI_ADDRESS:
                if (resultCode == Activity.RESULT_OK) {
                    String newName = data.getStringExtra("newName");
                    String newPhoneNum = data.getStringExtra("newPhoneNum");
                    String addAddress = data.getStringExtra("addAddress");
                    //String newAddress = data.getStringExtra("newAddress");
                    List<AddressBean.DataBean> mlist = new ArrayList<>();
                    AddressBean.DataBean addressBean = new AddressBean.DataBean();
                    addressBean.setConsignee(newName);
                    addressBean.setMobile(newPhoneNum);
                    addressBean.setCity_name(addAddress);
                    mlist.add(addressBean);
                    adapter.setItem(mlist);
                    adapter.notifyDataSetInvalidated();
                }
                break;
            default:
                break;
        }
    }

    public int dp2px(float dipValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
