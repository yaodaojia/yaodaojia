package com.example.yaodaojia.yaodaojia.model.http.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaodaojia on 2017/8/24.
 */

public class OrderBean {
    private String price;
    private String name;
    private String content;
    private String all_price;
    private String num;
    private String image_url;

    public OrderBean(String price, String name, String content, String all_price, String image_url, String num) {
        this.price = price;
        this.name = name;
        this.content = content;
        this.all_price = all_price;
        this.image_url = image_url;
        this.num=num;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAll_price() {
        return all_price;
    }

    public void setAll_price(String all_price) {
        this.all_price = all_price;
    }


    public String getImage_url() {
        return image_url;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public static List<OrderBean> getList() {
        List<OrderBean> list = new ArrayList<>();
        list.add(new OrderBean("100", "满199减元可用", "仅可使用感冒类药物商品", "200", "http://img.yaodaojia360.com/images/201601/thumb_img/422_thumb_G_1451939420452.jpg", "15"));
        list.add(new OrderBean("200", "满199减元可用", "仅可使用感冒类药物商品", "400", "http://img.yaodaojia360.com/images/201601/thumb_img/422_thumb_G_1451939420452.jpg", "15"));
        list.add(new OrderBean("90", "满199减元可用", "仅可使用感冒类药物商品", "180", "http://img.yaodaojia360.com/images/201601/thumb_img/422_thumb_G_1451939420452.jpg", "55"));
        list.add(new OrderBean("100", "满199减元可用", "仅可使用感冒类药物商品", "200", "http://img.yaodaojia360.com/images/201601/thumb_img/422_thumb_G_1451939420452.jpg", "15"));
        list.add(new OrderBean("100", "满199减元可用", "仅可使用感冒类药物商品", "200", "http://img.yaodaojia360.com/images/201601/thumb_img/422_thumb_G_1451939420452.jpg", "35"));
        list.add(new OrderBean("100", "满199减元可用", "仅可使用感冒类药物商品", "200", "http://img.yaodaojia360.com/images/201601/thumb_img/422_thumb_G_1451939420452.jpg", "15"));
        list.add(new OrderBean("100", "满199减元可用", "仅可使用感冒类药物商品", "200", "http://img.yaodaojia360.com/images/201601/thumb_img/422_thumb_G_1451939420452.jpg", "12"));
        list.add(new OrderBean("100", "满199减元可用", "仅可使用感冒类药物商品", "200", "http://img.yaodaojia360.com/images/201601/thumb_img/422_thumb_G_1451939420452.jpg", "15"));
        return list;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "price='" + price + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", all_price='" + all_price + '\'' +
                ", num='" + num + '\'' +
                ", image_url='" + image_url + '\'' +
                '}';
    }
}
