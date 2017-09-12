package com.example.yaodaojia.yaodaojia.model.http.bean;

import java.util.List;

/**
 * /**
 * 项目名称: 城市通
 * 类描述:
 * 创建人: XI
 * 创建时间: 2017/8/18 0018 11:55
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class Home_Search_Hot_Recycler_Bean {

    /**
     * success : true
     * data : [{"keyword":""},{"keyword":"感冒"},{"keyword":"颗粒"},{"keyword":"颗粒度"},{"keyword":"你好"},{"keyword":"氯雷"}]
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
         * keyword :
         */

        private String keyword;

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }
    }
}
