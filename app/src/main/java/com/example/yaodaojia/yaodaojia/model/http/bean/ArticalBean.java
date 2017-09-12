package com.example.yaodaojia.yaodaojia.model.http.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by yaodaojia on 2017/8/23.
 */

public class ArticalBean {


    /**
     * success : true
     * data : [{"article_id":136,"keywords":"","add_time":"267天前","file_url":"api.googlezh.com/","click_count":0,"title":"","url":"api.googlezh.com/test.html?id=136"},{"article_id":135,"keywords":"","add_time":"267天前","file_url":"api.googlezh.com/","click_count":0,"title":"感冒发烧","url":"api.googlezh.com/test.html?id=135"},{"article_id":134,"keywords":"","add_time":"274天前","file_url":"api.googlezh.com/","click_count":0,"title":"牙疼应急","url":"api.googlezh.com/test.html?id=134"},{"article_id":133,"keywords":"","add_time":"337天前","file_url":"api.googlezh.com/","click_count":0,"title":"为什么五点钟要起床（答案真让人吃惊）","url":"api.googlezh.com/test.html?id=133"},{"article_id":132,"keywords":"","add_time":"511天前","file_url":"api.googlezh.com/data/article/1459877032671268088.png","click_count":0,"title":"感冒了应该注意些什么？","url":"api.googlezh.com/test.html?id=132"}]
     */

    public boolean success;
    public List<DataBean> data;

    public static ArticalBean objectFromData(String str) {

        return new Gson().fromJson(str, ArticalBean.class);
    }

    public static class DataBean {
        /**
         * article_id : 136
         * keywords :
         * add_time : 267天前
         * file_url : api.googlezh.com/
         * click_count : 0
         * title :
         * url : api.googlezh.com/test.html?id=136
         */

        public int article_id;
        public String keywords;
        public String add_time;
        public String file_url;
        public int click_count;
        public String title;
        public String url;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }
    }
}
