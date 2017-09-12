package com.example.yaodaojia.yaodaojia.control.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yaodaojia.yaodaojia.R;
import com.example.yaodaojia.yaodaojia.base.BaseFragment;
import com.example.yaodaojia.yaodaojia.control.activity.mine.AddressAdministrationActivity;
import com.example.yaodaojia.yaodaojia.control.activity.mine.DiscountCouponActivity;
import com.example.yaodaojia.yaodaojia.control.activity.mine.Mine_Balance;
import com.example.yaodaojia.yaodaojia.control.activity.mine.MyOrderActivity;
import com.example.yaodaojia.yaodaojia.control.activity.mine.MyOrderActivity_Payment;
import com.example.yaodaojia.yaodaojia.control.activity.mine.MyOrderActivity_Send;
import com.example.yaodaojia.yaodaojia.control.activity.mine.MyOrderActivity_Shipped;
import com.example.yaodaojia.yaodaojia.control.activity.mine.PreviewActivity;
import com.example.yaodaojia.yaodaojia.control.activity.mine.Setting_Activity;
import com.example.yaodaojia.yaodaojia.model.http.bean.Persional_Bean;
import com.example.yaodaojia.yaodaojia.model.http.http.OkHttp;
import com.example.yaodaojia.yaodaojia.util.SDPathUtils;
import com.example.yaodaojia.yaodaojia.util.Utils_Host;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Request;


/**
 * Created by axi on 2017/8/9.
 */

public class Mine_Fragment extends BaseFragment {


    @BindView(R.id.mine_img)
    CircleImageView mineImg;
    @BindView(R.id.mine_order_right)
    ImageView mineOrderRight;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.mine_order_payment)
    TextView mineOrderPayment;
    @BindView(R.id.mine_order_send)
    TextView mineOrderSend;
    @BindView(R.id.mine_order_shipped)
    TextView mineOrderShipped;

    @BindView(R.id.mine_balance_img)
    ImageView mineBalanceImg;
    @BindView(R.id.mine_balance)
    RelativeLayout mineBalance;
    @BindView(R.id.mine_health_img)
    ImageView mineHealthImg;
    @BindView(R.id.mine_health)
    RelativeLayout mineHealth;
    @BindView(R.id.mine_coupons_img)
    ImageView mineCouponsImg;
    @BindView(R.id.mine_coupons)
    RelativeLayout mineCoupons;
    @BindView(R.id.mine_address)
    ImageView mineAddress;
    @BindView(R.id.mine_costumor_img)
    ImageView mineCostumorImg;
    @BindView(R.id.mine_costumor)
    RelativeLayout mineCostumor;
    @BindView(R.id.mine_log_out_img)
    ImageView mineLogOutImg;
    @BindView(R.id.mine_log_out)
    RelativeLayout mineLogOut;
    @BindView(R.id.mine_name)
    TextView mineName;
    @BindView(R.id.mine_setting)
    TextView mineSetting;
    @BindView(R.id.mine_address_rela)
    RelativeLayout mineAddressRela;
    @BindView(R.id.mine_the_order)
    RelativeLayout mineTheOrder;
    @BindView(R.id.mine_order_payment_text)
    TextView mineOrderPaymentText;
    @BindView(R.id.mine_order_payment_rela)
    RelativeLayout mineOrderPaymentRela;
    @BindView(R.id.mine_order_send_text)
    TextView mineOrderSendText;
    @BindView(R.id.mine_order_send_rela)
    RelativeLayout mineOrderSendRela;
    @BindView(R.id.mine_order_shipped_text)
    TextView mineOrderShippedText;
    @BindView(R.id.mine_order_shipped_rela)
    RelativeLayout mineOrderShippedRela;
    @BindView(R.id.mine_balance_text)
    TextView mineBalanceText;
    Unbinder unbinder;
    private SharedPreferences mSp;
    private SharedPreferences.Editor editor;
    private String localImg;
    private DisplayImageOptions options;
    private Persional_Bean persional_bean;

    @Override
    public int getLayout() {

        return R.layout.mine_fragment;
    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this,view);
        mSp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
    }

    private void initPersional() {
        Map<String, String> map = new HashMap<>();
        map.put("token", mSp.getString("token", ""));
        Log.d("Mine_Fragment", mSp.getString("token", ""));
        OkHttp.postAsync(Utils_Host.host+"v1/person/person_do", map, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Log.d("Mine_Fragment", result);
                Gson gson = new Gson();
                persional_bean = gson.fromJson(result, Persional_Bean.class);
                if(persional_bean.isSuccess()) {
                    if (persional_bean.getData().getNo_pay() == 0){
                        mineOrderPaymentText.setVisibility(View.GONE);
                    }else {
                        mineOrderPaymentText.setVisibility(View.VISIBLE);
                        mineOrderPaymentText.setText(persional_bean.getData().getNo_pay() + "");
                    }
                    if(persional_bean.getData().getPay() == 0){
                        mineOrderSendText.setVisibility(View.GONE);
                    }else {
                        mineOrderSendText.setVisibility(View.VISIBLE);
                        mineOrderSendText.setText(persional_bean.getData().getPay() + "");
                    }
                    if(persional_bean.getData().getShipping() == 0){
                        mineOrderShippedText.setVisibility(View.GONE);
                    }else {
                        mineOrderShippedText.setVisibility(View.VISIBLE);
                        mineOrderShippedText.setText(persional_bean.getData().getShipping() + "");
                    }
                    mineBalanceText.setText("余额" + persional_bean.getData().getBalance() + "");
                }else {
                    Toast.makeText(getContext(), "persional_bean.getData():" + persional_bean.getData(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        mineName.setText(mSp.getString("name", ""));
        initPersional();

    }

    @OnClick({R.id.mine_img, R.id.mine_balance, R.id.mine_coupons, R.id.mine_order_payment_rela, R.id.mine_order_send_rela, R.id.mine_order_shipped_rela, R.id.mine_the_order, R.id.mine_costumor, R.id.mine_address_rela, R.id.mine_log_out, R.id.mine_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_setting:
                Intent in = new Intent(getContext(), Setting_Activity.class);
                startActivity(in);
                break;
            case R.id.mine_img:
                showSheetDialog();
                break;
            case R.id.mine_balance:
                Intent ins = new Intent(getContext(), Mine_Balance.class);
                startActivity(ins);
                break;
            case R.id.mine_coupons:
                Intent inc = new Intent(getContext(), DiscountCouponActivity.class);
                startActivity(inc);
                break;
            case R.id.mine_order_payment_rela:
                startActivity(new Intent(getContext(), MyOrderActivity_Payment.class));
                break;
            case R.id.mine_order_send_rela:
                startActivity(new Intent(getContext(), MyOrderActivity_Send.class));
                break;
            case R.id.mine_order_shipped_rela:
                startActivity(new Intent(getContext(), MyOrderActivity_Shipped.class));
                break;
            case R.id.mine_costumor:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:4006682869"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.mine_log_out:

                break;
            case R.id.mine_address_rela:
                Intent inq = new Intent(getContext(), AddressAdministrationActivity.class);
                startActivity(inq);
                break;
            case R.id.mine_the_order:
                Intent intent1 = new Intent(getContext(), MyOrderActivity.class);
                startActivity(intent1);
                break;
        }
    }


    private void showSheetDialog() {
        View view = getActivity().getLayoutInflater().inflate(
                R.layout.head_diolog, null);

        final Dialog dialog = new Dialog(getActivity(), R.style.Theme_Design_BottomSheetDialog);
        dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.onWindowAttributesChanged(wl);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        Button btnCamera = (Button) view.findViewById(R.id.btn_to_camera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 222);
                    return;
                } else if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 222);
                    return;
                } else if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 222);
                    return;
                } else {
                    Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(SDPathUtils.getCachePath(), "temp.jpg")));
                        startActivityForResult(openCameraIntent, 2);
                    } else {
                        Uri imageUri = FileProvider.getUriForFile(getActivity(), "com.camera_photos.fileprovider", new File(SDPathUtils.getCachePath(), "temp.jpg"));
                        openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(openCameraIntent, 2);
                    }
                }

            }
        });
        Button btnPhoto = view.findViewById(R.id.btn_to_photo);
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent,1);
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

    public static final Bitmap getBitmap(ContentResolver cr, Uri url)
            throws FileNotFoundException, IOException {
        InputStream input = cr.openInputStream(url);
        Bitmap bitmap = BitmapFactory.decodeStream(input);
        input.close();
        return bitmap;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            startPhotoZoom(data.getData());
        } else if (requestCode == 2) {
            File temp = new File(SDPathUtils.getCachePath(), "temp.jpg");
            startPhotoZoom(Uri.fromFile(temp));
        } else if (requestCode == 3) {
            if (data != null) {
                setPicToView(data);
            }
        }
    }
    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent(getActivity(), PreviewActivity.class);
        intent.setDataAndType(uri, "image/*");
        startActivityForResult(intent, 3);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bitmap bitmap = null;
        byte[] bis = picdata.getByteArrayExtra("bitmap");
        bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
        localImg = System.currentTimeMillis() + ".JPEG";

        if (bitmap != null) {

            SDPathUtils.saveBitmap(bitmap, localImg);
            Log.e("本地图片绑定", SDPathUtils.getCachePath() + localImg);
            setImageUrl(mineImg, "file:/" + SDPathUtils.getCachePath() + localImg, R.mipmap.dadou);
        }
    }



    public void setImageUrl(ImageView ivId, String imageUrl, int emptyImgId) {
        if (options == null) {
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(emptyImgId)
                    .showImageForEmptyUri(emptyImgId)
                    .showImageOnFail(emptyImgId).cacheInMemory(true)
                    .cacheOnDisk(true).considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
        }
        ImageLoader.getInstance().displayImage(imageUrl, ivId, options);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
