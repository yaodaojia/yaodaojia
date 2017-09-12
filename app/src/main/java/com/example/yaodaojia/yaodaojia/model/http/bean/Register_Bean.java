package com.example.yaodaojia.yaodaojia.model.http.bean;

/**
 * /**
 * 项目名称: 药到家
 * 类描述:
 * 创建人: XI
 * 创建时间: 2017/8/29 0029 15:18
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class Register_Bean {


    /**
     * success : false
     * data : 验证码不正确
     */

    private boolean success;
    private String data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
