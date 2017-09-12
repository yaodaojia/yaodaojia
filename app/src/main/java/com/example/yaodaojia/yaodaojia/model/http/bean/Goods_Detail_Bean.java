package com.example.yaodaojia.yaodaojia.model.http.bean;

/**
 * /**
 * 项目名称: 药到家
 * 类描述:
 * 创建人: XI
 * 创建时间: 2017/8/28 0028 17:14
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class Goods_Detail_Bean {


    /**
     * success : true
     * data : {"g_id":359,"g_name":"孟鲁司特钠片","g_size":"5mg*5s","market_price":44,"g_component":"","g_symptom":"疏风散寒，解表清热。用于风寒感冒，头痛发热，恶寒身痛，鼻流清涕，咳嗽咽干","g_usage":"","adver":"","note":"","g_img_thumb":"images/201601/thumb_img/359_thumb_G_1451932177806.jpg","contra":null,"img":"http://img.yaodaojia360.com/images/201601/thumb_img/359_thumb_G_1451932177806.jpg"}
     */

    private boolean success;
    private DataBean data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * g_id : 359
         * g_name : 孟鲁司特钠片
         * g_size : 5mg*5s
         * market_price : 44
         * g_component :
         * g_symptom : 疏风散寒，解表清热。用于风寒感冒，头痛发热，恶寒身痛，鼻流清涕，咳嗽咽干
         * g_usage :
         * adver :
         * note :
         * g_img_thumb : images/201601/thumb_img/359_thumb_G_1451932177806.jpg
         * contra : null
         * img : http://img.yaodaojia360.com/images/201601/thumb_img/359_thumb_G_1451932177806.jpg
         */

        private int g_id;
        private String g_name;
        private String g_size;
        private int market_price;
        private String g_component;
        private String g_symptom;
        private String g_usage;
        private String adver;
        private String note;
        private String g_img_thumb;
        private Object contra;
        private String img;

        public int getG_id() {
            return g_id;
        }

        public void setG_id(int g_id) {
            this.g_id = g_id;
        }

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

        public int getMarket_price() {
            return market_price;
        }

        public void setMarket_price(int market_price) {
            this.market_price = market_price;
        }

        public String getG_component() {
            return g_component;
        }

        public void setG_component(String g_component) {
            this.g_component = g_component;
        }

        public String getG_symptom() {
            return g_symptom;
        }

        public void setG_symptom(String g_symptom) {
            this.g_symptom = g_symptom;
        }

        public String getG_usage() {
            return g_usage;
        }

        public void setG_usage(String g_usage) {
            this.g_usage = g_usage;
        }

        public String getAdver() {
            return adver;
        }

        public void setAdver(String adver) {
            this.adver = adver;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getG_img_thumb() {
            return g_img_thumb;
        }

        public void setG_img_thumb(String g_img_thumb) {
            this.g_img_thumb = g_img_thumb;
        }

        public Object getContra() {
            return contra;
        }

        public void setContra(Object contra) {
            this.contra = contra;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
