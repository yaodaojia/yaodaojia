package com.example.yaodaojia.yaodaojia.model.http.bean;

/**
 * /**
 * 项目名称: 药到家
 * 类描述:
 * 创建人: XI
 * 创建时间: 2017/8/31 0031 10:37
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class Persional_Bean {

    /**
     * success : true
     * data : {"balance":0,"no_pay":0,"pay":0,"shipping":0}
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
         * balance : 0
         * no_pay : 0
         * pay : 0
         * shipping : 0
         */

        private int balance;
        private int no_pay;
        private int pay;
        private int shipping;

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public int getNo_pay() {
            return no_pay;
        }

        public void setNo_pay(int no_pay) {
            this.no_pay = no_pay;
        }

        public int getPay() {
            return pay;
        }

        public void setPay(int pay) {
            this.pay = pay;
        }

        public int getShipping() {
            return shipping;
        }

        public void setShipping(int shipping) {
            this.shipping = shipping;
        }
    }
}
