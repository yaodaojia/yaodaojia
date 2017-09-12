package com.example.yaodaojia.yaodaojia.model.http.bean;

import java.util.List;

/**
 * /**
 * 项目名称: 城市通
 * 类描述:
 * 创建人: XI
 * 创建时间: 2017/8/17 0017 16:57
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class Home_DaoHang {

    /**
     * success : true
     * list : [{"name":"两性专场","image":"http://img.yaodaojia360.com/mobile/data/afficheimg/1472032792120935015.jpg","id":60,"content":"","act_type":0,"url":"affiche.php?ad_id=60&uri=http://m.yaodaojia360.com/mobile/search.php?intro=hot"},{"name":"家中必备","image":"http://img.yaodaojia360.com/mobile/data/afficheimg/1483588755577274259.jpg","id":61,"content":"","act_type":0,"url":"affiche.php?ad_id=61&uri=http://m.yaodaojia360.com/mobile/search.php?intro=best"},{"name":"出游必备","image":"http://img.yaodaojia360.com/mobile/data/afficheimg/1483007634990982078.jpg","id":62,"content":"","act_type":0,"url":"affiche.php?ad_id=62&uri=http://m.yaodaojia360.com/mobile/search.php?intro=new"},{"name":"首页红包[删除]","image":"http://img.yaodaojia360.com/mobile/data/afficheimg/1486118039082661903.png","id":66,"content":"","act_type":0,"url":"affiche.php?ad_id=66&uri=http://m.yaodaojia360.com/mobile/get_user_bonus.php?bonus_pid_id=114"}]
     */

    private boolean success;
    private List<ListBean> list;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * name : 两性专场
         * image : http://img.yaodaojia360.com/mobile/data/afficheimg/1472032792120935015.jpg
         * id : 60
         * content :
         * act_type : 0
         * url : affiche.php?ad_id=60&uri=http://m.yaodaojia360.com/mobile/search.php?intro=hot
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
