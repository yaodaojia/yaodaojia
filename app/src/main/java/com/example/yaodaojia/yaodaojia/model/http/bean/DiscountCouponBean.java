package com.example.yaodaojia.yaodaojia.model.http.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：DiscountCouponBean
 * 创建人：
 * 创建时间：2017/8/22 20:13
 */
public class DiscountCouponBean {
    /**
     *    TextView price,full,scope,time,use;
     */

    private String price;
    private String full;
    private String scope;
    private String time;
    private String use;

    public DiscountCouponBean(String price, String full, String scope, String time, String use) {
        this.price = price;
        this.full = full;
        this.scope = scope;
        this.time = time;
        this.use = use;
    }

    public DiscountCouponBean() {
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }
public static List<DiscountCouponBean> getList(){
    List<DiscountCouponBean> list=new ArrayList<>();
    list.add(new DiscountCouponBean("100","满199减元可用","仅可使用感冒类药物商品","2017/07/23到期","立即使用"));
    list.add(new DiscountCouponBean("100","满199减元可用","仅可使用感冒类药物商品","2017/07/23到期","立即使用"));
    list.add(new DiscountCouponBean("100","满199减元可用","仅可使用感冒类药物商品","2017/07/23到期","立即使用"));
    list.add(new DiscountCouponBean("100","满199减元可用","仅可使用感冒类药物商品","2017/07/23到期","立即使用"));
    list.add(new DiscountCouponBean("100","满199减元可用","仅可使用感冒类药物商品","2017/07/23到期","立即使用"));
    return list;

}

    @Override
    public String toString() {
        return "DiscountCouponBean{" +
                "price='" + price + '\'' +
                ", full='" + full + '\'' +
                ", scope='" + scope + '\'' +
                ", time='" + time + '\'' +
                ", use='" + use + '\'' +
                '}';
    }
}
