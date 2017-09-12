package com.example.yaodaojia.yaodaojia.model.http.bean;

import java.util.List;

/**
 * Created by axi on 2017/9/4.
 */

public class Home_DaoHang_Bean {

    /**
     * success : true
     * data : [{"name":"出游必备","image":"http://img.yaodaojia360.com/mobile/data/afficheimg/1483007634990982078.jpg","id":55,"content":"介绍","act_type":2,"url":"affiche.php?ad_id=55&uri="},{"name":"合作-金象","image":"http://img.yaodaojia360.com/mobile/data/afficheimg/1484274255085295616.png","id":64,"content":"介绍","act_type":2,"url":"affiche.php?ad_id=64&uri="},{"name":"首页红包[删除]","image":"http://img.yaodaojia360.com/mobile/data/afficheimg/1486118039082661903.png","id":66,"content":"介绍","act_type":2,"url":"affiche.php?ad_id=66&uri="},{"name":"情人节专场","image":"http://img.yaodaojia360.com/mobile/data/afficheimg/1486620221922452504.jpg","id":67,"content":"介绍","act_type":2,"url":"affiche.php?ad_id=67&uri="},{"name":"公司常用","image":"http://img.yaodaojia360.com/mobile/data/afficheimg/1486117769241392267.jpg","id":70,"content":"介绍","act_type":2,"url":"affiche.php?ad_id=70&uri="},{"name":"公司常用","image":"http://img.yaodaojia360.com/mobile/data/afficheimg/1486117769241392267.jpg","id":71,"content":"介绍","act_type":2,"url":"affiche.php?ad_id=71&uri="}]
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
         * name : 出游必备
         * image : http://img.yaodaojia360.com/mobile/data/afficheimg/1483007634990982078.jpg
         * id : 55
         * content : 介绍
         * act_type : 2
         * url : affiche.php?ad_id=55&uri=
         */

        private String name;
        private String image;
        private int id;
        private String content;
        private int act_type;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getAct_type() {
            return act_type;
        }

        public void setAct_type(int act_type) {
            this.act_type = act_type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
