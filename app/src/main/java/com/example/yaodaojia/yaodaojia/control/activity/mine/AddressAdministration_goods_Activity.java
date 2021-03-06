package com.example.yaodaojia.yaodaojia.control.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.example.yaodaojia.yaodaojia.model.http.bean.Address_Admin_Goods_Bean;
import com.example.yaodaojia.yaodaojia.model.http.http.OkHttp;
import com.example.yaodaojia.yaodaojia.view.MySmartRefreshLayout;
import com.example.yaodaojia.yaodaojia.view.swipelist.SwipeMenu;
import com.example.yaodaojia.yaodaojia.view.swipelist.SwipeMenuCreator;
import com.example.yaodaojia.yaodaojia.view.swipelist.SwipeMenuItem;
import com.example.yaodaojia.yaodaojia.view.swipelist.SwipeMenuListView;
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
public class AddressAdministration_goods_Activity extends AppCompatActivity implements View.OnClickListener {

    private SwipeMenuListView listview;
    private List<AddressBean.DataBean> list = new ArrayList<>();
    private ImageView back;
    private TextView address_tv;
    private static final int MODIFI_ADDRESS = 200;
    private static final int NEWMODIFI_ADDRESS = 100;
    private int currentModifiyAddressPosion;
    private String name;
    private String phone;
    private String address;
    private AddressAdapter adapter;
    private SharedPreferences mShared,mShare,mShares;
    private SharedPreferences.Editor mEditor,mEditors;
    private AddressBean addressBean;
    private MySmartRefreshLayout homeFragmentSmart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_administration);
        initView();
        initData();
        setListener();
        homeFragmentSmart.setOnRefreshListener(new MySmartRefreshLayout.onRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        initData();
                        homeFragmentSmart.stopRefresh();
                    }
                }).start();

            }

            @Override
            public void onLoadMore() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        initData();
                        homeFragmentSmart.stopLoadMore();
                    }
                }).start();

            }
        });
    }
    private void setListener() {
        back.setOnClickListener(this);
        address_tv.setOnClickListener(this);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                
                initAddress(String.valueOf(addressBean.getData().get(i).getAddress_id()),addressBean.getData().get(i).getConsignee());
                finish();
            }
        });
    }

    private void initAddress(String id, final String name) {
        Map<String,String> map = new HashMap<>();
        map.put("token",mShared.getString("token",""));
        map.put("address_id",id);
        OkHttp.postAsync("http://api.googlezh.com/v1/order/lenth", map, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Log.d("AddressAdministration_g", result);
                Gson gson = new Gson();
                Address_Admin_Goods_Bean address_admin_goods_bean = gson.fromJson(result, Address_Admin_Goods_Bean.class);
                if(address_admin_goods_bean.isSuccess()){
                    //商家id
//                    address_admin_goods_bean.getData().get_$0().getStore_id()
//                     mEditors.putString("address_name",name);
//                    mEditors.putString("");
//                    mEditors.commit();
//                    address_admin_goods_bean.getData().get_$0().
                    Intent intent =getIntent();
                    //这里使用bundle绷带来传输数据
                    Bundle bundle =new Bundle();
                    //传输的内容仍然是键值对的形式
                    bundle.putString("second",name);//回发的消息,hello world from secondActivity!
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finish();
                }else {
                    Toast.makeText(AddressAdministration_goods_Activity.this, "address_admin_goods_bean.getData():" + address_admin_goods_bean.getData(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initView() {
        mShared = getSharedPreferences("login",MODE_PRIVATE);
        mShare = getSharedPreferences("location",MODE_PRIVATE);
        mShares = getSharedPreferences("order",MODE_PRIVATE);
        mEditor = mShare.edit();
        mEditors = mShares.edit();
        listview = (SwipeMenuListView) findViewById(R.id.address_lv);
        back = (ImageView) findViewById(R.id.address_back);
        address_tv = (TextView) findViewById(R.id.address_iv_editor);
        homeFragmentSmart = (MySmartRefreshLayout) findViewById(R.id.address_smart);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem changeItem = new SwipeMenuItem(AddressAdministration_goods_Activity.this);
                changeItem.setBackground(new ColorDrawable(Color.GREEN));
                changeItem.setWidth(dp2px(60));
                changeItem.setTitle("修改");
                changeItem.setTitleSize(17);
                changeItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(changeItem);
                SwipeMenuItem deleteItem = new SwipeMenuItem(AddressAdministration_goods_Activity.this);
                deleteItem.setBackground(new ColorDrawable(Color.RED));
                deleteItem.setWidth(dp2px(60));
                deleteItem.setTitle("删除");
                deleteItem.setTitleSize(17);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };
        listview.setMenuCreator(creator);
        listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu,int index) {
                //index的值就是在SwipeMenu依次添加SwipeMenuItem顺序值，类似数组的下标。
                //从0开始，依次是：0、1、2、3...
                switch (index) {
                    case 0:
                        Intent intent=new Intent(AddressAdministration_goods_Activity.this,ModificationAddressActivity.class);
                        intent.putExtra("phone",list.get(index).getMobile());
                        intent.putExtra("address",list.get(index).getProvince_name()+list.get(index).getCity_name()+list.get(index).getDistrict_name());
//                mEditor.putString("province", item.getProvinceName());
//                mEditor.putString("city", item.getCityName());
//                mEditor.putString("distric", item.getAdName());
//                map.put("lng", String.valueOf(mShared.getFloat("longitude",0)));
//                map.put("lat", String.valueOf(mShared.getFloat("latitude",0)));
                        intent.putExtra("province",list.get(index).getProvince_name());
                        intent.putExtra("city",list.get(index).getCity_name());
                        intent.putExtra("distric",list.get(index).getDistrict_name());
                        intent.putExtra("lat",mShare.getFloat("latitude",0));
                        intent.putExtra("lng",mShare.getFloat("longitude",0));
                        intent.putExtra("address_id",list.get(index).getAddress_id()+"");
                        intent.putExtra("name",list.get(index).getConsignee());
                        currentModifiyAddressPosion = index;
                        startActivityForResult(intent,MODIFI_ADDRESS);
                        break;

                    case 1:
                       initDelete();
                        break;
                }

                // false : 当用户触发其他地方的屏幕时候，自动收起菜单。
                // true : 不改变已经打开菜单的样式，保持原样不收起。
                return false;
            }
        });
        // 监测用户在ListView的SwipeMenu侧滑事件。
        listview.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int pos) {
                Log.d("位置:" + pos, "开始侧滑...");
            }

            @Override
            public void onSwipeEnd(int pos) {
                Log.d("位置:" + pos, "侧滑结束.");
            }
        });

    }

    private void initDelete() {

    }


    private void initData() {

        Map<String,String> map = new HashMap<>();
        map.put("token",mShared.getString("token",""));
        OkHttp.postAsync("http://api.googlezh.com//v1/person/alist", map, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Log.d("AddressAdministrationAc", result);
                Gson gson = new Gson();
                addressBean = gson.fromJson(result, AddressBean.class);
                if(addressBean.isSuccess()){
                    list.addAll(addressBean.getData());
                    if(adapter == null){
                        adapter=new AddressAdapter(AddressAdministration_goods_Activity.this, list);
                        listview.setAdapter(adapter);
                    }else {
                        adapter.getData(list);
                    }
                }
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
                Intent intent=new Intent(AddressAdministration_goods_Activity.this,Home_Location.class);
//                star|tActivityForResult(intent,NEWMODIFI_ADDRESS);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case MODIFI_ADDRESS:
                if (resultCode == Activity.RESULT_OK){
                    name = data.getStringExtra("name");
                    phone = data.getStringExtra("phone");
                    address = data.getStringExtra("address");
                    AddressBean.DataBean addressBean = (AddressBean.DataBean) adapter.getItem(currentModifiyAddressPosion);
                    addressBean.setConsignee(name);
                    addressBean.setMobile(phone);
//                    addressBean.setAddress(address);
                    adapter.notifyDataSetInvalidated();
                }
                break;
            case NEWMODIFI_ADDRESS:
//                if (resultCode == Activity.RESULT_OK){
//                    String newName = data.getStringExtra("newName");
//                    String newPhoneNum = data.getStringExtra("newPhoneNum");
//                    String addAddress= data.getStringExtra("addAddress");
//                    String newAddress = data.getStringExtra("newAddress");
//                    List<AddressBean.DataBean> mlist=new ArrayList<>();
//                    AddressBean.DataBean addressBean=new AddressBean.DataBean();
//                    addressBean.setConsignee(newName);
//                    addressBean.setMobile(newPhoneNum);
//                    addressBean.setCity_name(addAddress);
//                    mlist.add(addressBean);
//                    adapter.setItem(mlist);
//                    adapter.notifyDataSetInvalidated();
//                }
                break;
            default:
                break;
        }
    }
    public int dp2px(float dipValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }
}
