package com.example.yaodaojia.yaodaojia.model.http.bean;

import java.util.List;

/**
 * /**
 * 项目名称: 药到家
 * 类描述:
 * 创建人: XI
 * 创建时间: 2017/8/30 0030 14:28
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class Trading_Record_Bean {
    /**
     * success : true
     * data : [{"trans_id":1,"add_time":"1504096477","money":100000,"status":0,"user_id":18}]
     */

    private boolean success;
    private String data;

    public void setData(String data) {
        this.data = data;
    }
    public String getData(){
        return data;
    }
    private List<DataBean> datas;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getDatas() {
        return datas;
    }

    public void setData(List<DataBean> data) {
        this.datas = data;
    }

    public static class DataBean {
        /**
         * trans_id : 1
         * add_time : 1504096477
         * money : 100000
         * status : 0
         * user_id : 18
         */

        private int trans_id;
        private String add_time;
        private int money;
        private int status;
        private int user_id;

        public int getTrans_id() {
            return trans_id;
        }

        public void setTrans_id(int trans_id) {
            this.trans_id = trans_id;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }

}
