package com.example.yaodaojia.yaodaojia.model.http.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by yaodaojia on 2017/8/18.
 */

public class ShoppingCarBean {

    /**
     * success : true
     * data : [{"cart_id":63988,"goods_id":298,"goods_name":"感冒软胶囊","market_price":"12","goods_number":1,"goods_img":"http://img.yaodaojia360.com/images/201601/thumb_img/298_thumb_G_1451867209714.jpg"},{"cart_id":63989,"goods_id":302,"goods_name":"养阴清肺丸","market_price":"17","goods_number":1,"goods_img":"http://img.yaodaojia360.com/images/201601/thumb_img/302_thumb_G_1451867429533.jpg"},{"cart_id":63990,"goods_id":287,"goods_name":"复方氨酚烷胺片[感康]","market_price":"13","goods_number":1,"goods_img":"http://img.yaodaojia360.com/images/201601/thumb_img/287_thumb_G_1453412385863.jpg"},{"cart_id":63991,"goods_id":287,"goods_name":"复方氨酚烷胺片[感康]","market_price":"13","goods_number":1,"goods_img":"http://img.yaodaojia360.com/images/201601/thumb_img/287_thumb_G_1453412385863.jpg"}]
     */

    public boolean success;
    public List<DataBean> data;

    public static ShoppingCarBean objectFromData(String str) {

        return new Gson().fromJson(str, ShoppingCarBean.class);
    }

    public static class DataBean {
        /**
         * cart_id : 63988
         * goods_id : 298
         * goods_name : 感冒软胶囊
         * market_price : 12
         * goods_number : 1
         * goods_img : http://img.yaodaojia360.com/images/201601/thumb_img/298_thumb_G_1451867209714.jpg
         */
        public String goods_content;
        public int cart_id;
        public int goods_id;
        public String goods_name;
        public String market_price;
        public int goods_number;
        public String goods_img;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }
    }
}
