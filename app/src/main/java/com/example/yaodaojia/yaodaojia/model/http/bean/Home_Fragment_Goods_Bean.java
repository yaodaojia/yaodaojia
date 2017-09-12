package com.example.yaodaojia.yaodaojia.model.http.bean;

import java.util.List;

/**
 * /**
 * 项目名称: 城市通
 * 类描述:
 * 创建人: XI
 * 创建时间: 2017/8/17 0017 20:43
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class Home_Fragment_Goods_Bean {


    /**
     * success : true
     * data : [{"g_name":"孟鲁司特钠片","g_size":"5mg*5s","g_id":359,"real_price":1,"g_img":"http://img.yaodaojia360.com/images/201601/thumb_img/359_thumb_G_1451932177806.jpg"},{"g_name":"强力枇杷露","g_size":"100ml","g_id":364,"real_price":1,"g_img":"http://img.yaodaojia360.com/images/201612/thumb_img/364_thumb_G_1481599123761.jpg"},{"g_name":"百令胶囊(百令)","g_size":"500mg*42s","g_id":370,"real_price":1,"g_img":"http://img.yaodaojia360.com/images/201601/thumb_img/370_thumb_G_1451933083120.jpg"},{"g_name":"叶酸片（斯利安）","g_size":"10片","g_id":388,"real_price":1,"g_img":"http://img.yaodaojia360.com/images/201601/thumb_img/388_thumb_G_1451935477644.jpg"},{"g_name":"速力菲（琥珀酸亚铁片）","g_size":"10片","g_id":389,"real_price":1,"g_img":"http://img.yaodaojia360.com/images/201601/thumb_img/389_thumb_G_1451935561973.jpg"},{"g_name":"[养生堂]天然维生素C咀嚼片","g_size":"10片","g_id":393,"real_price":1,"g_img":"http://img.yaodaojia360.com/images/201601/thumb_img/393_thumb_G_1451935783794.jpg"}]
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
         * g_name : 孟鲁司特钠片
         * g_size : 5mg*5s
         * g_id : 359
         * real_price : 1
         * g_img : http://img.yaodaojia360.com/images/201601/thumb_img/359_thumb_G_1451932177806.jpg
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
