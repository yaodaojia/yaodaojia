package com.example.yaodaojia.yaodaojia.model.http.bean;

import java.util.List;

/**
 * Created by yaodaojia on 2017/8/29.
 */

public class AddressBean {

    /**
     * success : true
     * data : [{"address_id":30597,"province_name":"北京","city_name":"北京","district_name":"昌平区","consignee":"李海鹏","mobile":"18518132247","is_default":1}]
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
         * address_id : 30597
         * province_name : 北京
         * city_name : 北京
         * district_name : 昌平区
         * consignee : 李海鹏
         * mobile : 18518132247
         * is_default : 1
         */

        private int address_id;
        private String province_name;
        private String city_name;
        private String district_name;
        private String consignee;
        private String mobile;
        private int is_default;

        public int getAddress_id() {
            return address_id;
        }

        public void setAddress_id(int address_id) {
            this.address_id = address_id;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getDistrict_name() {
            return district_name;
        }

        public void setDistrict_name(String district_name) {
            this.district_name = district_name;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getIs_default() {
            return is_default;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
        }
    }
}
