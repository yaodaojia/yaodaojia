package com.example.yaodaojia.yaodaojia.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
public class MyEditText extends android.support.v7.widget.AppCompatEditText{
    private Context mContext;
    private Drawable imgDel_Gray;
    private Drawable imgDel_Bule;
    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public MyEditText(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }
    /***
     * 初始化
     */
    public void init() {
        // TODO Auto-generated method stub
        setDrawble();
        // 对EditText文本状态监听
        this.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                setDrawble();
            }
        });
    }

    /***
     * 设置图片
     */
    public void setDrawble() {
        if (this.length() < 1) {
            /****
             * 此方法意思是在EditText添加图片 参数： left - 左边图片id top - 顶部图片id right - 右边图片id
             * bottom - 底部图片id
             */
            this.setCompoundDrawablesWithIntrinsicBounds(null, null,
                    imgDel_Gray, null);
        } else {
            this.setCompoundDrawablesWithIntrinsicBounds(null, null,
                    imgDel_Bule, null);
        }
    }

    /***
     * 设置删除事件监听
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (imgDel_Bule != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 50;
            if (rect.contains(eventX, eventY))
                setText("");
        }
        return super.onTouchEvent(event);
    }
}
