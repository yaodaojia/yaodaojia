package com.example.yaodaojia.yaodaojia.model.http.bean;

import java.util.List;

/**
 * /**
 * 项目名称: 城市通
 * 类描述:
 * 创建人: XI
 * 创建时间: 2017/8/16 0016 15:43
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class Home_LunBo {


    /**
     * success : true
     * data : [{"name":"肠胃调理","image":"http://img.yaodaojia360.com/mobile/data/afficheimg/1486117769241392267.jpg","id":3,"content":"介绍","act_type":1,"url":"affiche.php?ad_id=3&uri="},{"name":"首页幻灯广告2","image":"http://img.yaodaojia360.com/mobile/data/afficheimg/1484274243193622665.png","id":4,"content":"介绍","act_type":1,"url":"affiche.php?ad_id=4&uri="},{"name":"两性专场","image":"http://img.yaodaojia360.com/mobile/data/afficheimg/1472032792120935015.jpg","id":53,"content":"介绍","act_type":1,"url":"affiche.php?ad_id=53&uri="}]
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
         * name : 肠胃调理
         * image : http://img.yaodaojia360.com/mobile/data/afficheimg/1486117769241392267.jpg
         * id : 3
         * content : 介绍
         * act_type : 1
         * url : affiche.php?ad_id=3&uri=
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
