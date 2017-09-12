package com.example.yaodaojia.yaodaojia.model.http.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by axi on 2017/9/7.
 */

public class Address_Admin_Goods_Bean {

    /**
     * success : true
     * data : {"0":{"store_id":42,"lng":"116.43800354003906","lat":"39.89400863647461","juli":2422},"shipping_price":8}
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
         * 0 : {"store_id":42,"lng":"116.43800354003906","lat":"39.89400863647461","juli":2422}
         * shipping_price : 8
         */

        @SerializedName("0")
        private _$0Bean _$0;
        private int shipping_price;

        public _$0Bean get_$0() {
            return _$0;
        }

        public void set_$0(_$0Bean _$0) {
            this._$0 = _$0;
        }

        public int getShipping_price() {
            return shipping_price;
        }

        public void setShipping_price(int shipping_price) {
            this.shipping_price = shipping_price;
        }

        public static class _$0Bean {
            /**
             * store_id : 42
             * lng : 116.43800354003906
             * lat : 39.89400863647461
             * juli : 2422
             */

            private int store_id;
            private String lng;
            private String lat;
            private int juli;

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public int getJuli() {
                return juli;
            }

            public void setJuli(int juli) {
                this.juli = juli;
            }
        }
    }
}
