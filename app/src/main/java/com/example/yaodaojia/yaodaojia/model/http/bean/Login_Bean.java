package com.example.yaodaojia.yaodaojia.model.http.bean;

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


public class Login_Bean {

    /**
     * success : true
     * data : {"id":17,"mobile":"17600886398","nickname":"17600886398","token":"ZNsGMw47PMKIcw","over_token":1505303608}
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
         * id : 17
         * mobile : 17600886398
         * nickname : 17600886398
         * token : ZNsGMw47PMKIcw
         * over_token : 1505303608
         */

        private int id;
        private String mobile;
        private String nickname;
        private String token;
        private int over_token;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getOver_token() {
            return over_token;
        }

        public void setOver_token(int over_token) {
            this.over_token = over_token;
        }
    }
}
