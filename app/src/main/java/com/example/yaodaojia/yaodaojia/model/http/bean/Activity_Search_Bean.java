package com.example.yaodaojia.yaodaojia.model.http.bean;

import java.util.List;

/**
 * /**
 * 项目名称: 城市通
 * 类描述:
 * 创建人: XI
 * 创建时间: 2017/8/18 0018 14:45
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class Activity_Search_Bean {


    /**
     * success : true
     * data : [{"g_name":"[鱼跃]SY型氧气袋 SY-30L ","g_size":"1个","g_id":2041,"real_price":1,"g_img":"http://img.yaodaojia360.com/images/201602/thumb_img/2041_thumb_G_1456090965421.jpg"},{"g_name":"鱼跃电子血压计YE-8700A","g_size":"1台","g_id":2083,"real_price":1,"g_img":"http://img.yaodaojia360.com/images/201602/thumb_img/2083_thumb_P_1456094526179.jpg"},{"g_name":"Kimi Yimi人体润滑液","g_size":"42g","g_id":2537,"real_price":1,"g_img":"http://img.yaodaojia360.com/images/201602/thumb_img/2537_thumb_G_1456268012887.jpg"},{"g_name":"威阳WY-1型 手持式吸痰器","g_size":"1台","g_id":3042,"real_price":1,"g_img":"http://img.yaodaojia360.com/images/201603/thumb_img/3042_thumb_P_1457484359307.jpg"},{"g_name":"拐杖(四爪YU850)","g_size":"四爪YU850","g_id":3474,"real_price":0,"g_img":"http://img.yaodaojia360.com/images/201604/thumb_img/3474_thumb_G_1461036773917.jpg"},{"g_name":"SY型氧气袋","g_size":"SY-42L","g_id":34084,"real_price":0,"g_img":"http://img.yaodaojia360.com/images/img_s/20452_M1.jpg"}]
     */

    private boolean success;
    private List<DataBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * g_name : [鱼跃]SY型氧气袋 SY-30L
         * g_size : 1个
         * g_id : 2041
         * real_price : 1
         * g_img : http://img.yaodaojia360.com/images/201602/thumb_img/2041_thumb_G_1456090965421.jpg
         */

        private String g_name;
        private String g_size;
        private int g_id;
        private int real_price;
        private String g_img;

        public String getG_name() {
            return g_name;
        }

        public void setG_name(String g_name) {
            this.g_name = g_name;
        }

        public String getG_size() {
            return g_size;
        }

        public void setG_size(String g_size) {
            this.g_size = g_size;
        }

        public int getG_id() {
            return g_id;
        }

        public void setG_id(int g_id) {
            this.g_id = g_id;
        }

        public int getReal_price() {
            return real_price;
        }

        public void setReal_price(int real_price) {
            this.real_price = real_price;
        }

        public String getG_img() {
            return g_img;
        }

        public void setG_img(String g_img) {
            this.g_img = g_img;
        }
    }
}
