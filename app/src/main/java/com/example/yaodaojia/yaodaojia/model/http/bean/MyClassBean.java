package com.example.yaodaojia.yaodaojia.model.http.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by yaodaojia on 2017/8/18.
 */

public class MyClassBean {

    /**
     * success : true
     * data : {"style":[{"cat_id":1,"cat_name":"感冒常用"},{"cat_id":2,"cat_name":"清热解毒"},{"cat_id":3,"cat_name":"心脑血管"},{"cat_id":4,"cat_name":"消化系统"},{"cat_id":5,"cat_name":"妇科用药"},{"cat_id":6,"cat_name":"儿童天地"},{"cat_id":8,"cat_name":"风湿骨伤"},{"cat_id":356,"cat_name":"性福男女"},{"cat_id":357,"cat_name":"滋补养生"},{"cat_id":358,"cat_name":"医疗器材"},{"cat_id":359,"cat_name":"皮肤用药"},{"cat_id":360,"cat_name":"五官用药"},{"cat_id":362,"cat_name":"呼吸系统"},{"cat_id":363,"cat_name":"内分泌类"},{"cat_id":364,"cat_name":"神经用药"},{"cat_id":365,"cat_name":"个人护理"}],"goods":[{"g_img_thumb":"images/201601/thumb_img/388_thumb_G_1451935477644.jpg","g_id":388,"g_name":"叶酸片（斯利安）","g_size":"10片","shop_price":16,"g_symptom":"适用于缓解普通感冒及流行性感冒引起的发热、头痛、四肢酸痛、打喷嚏、流鼻涕、鼻塞、咽痛等症状","img_url":"http://img.yaodaojia360.com/images/201601/thumb_img/388_thumb_G_1451935477644.jpg"},{"g_img_thumb":"images/201601/thumb_img/389_thumb_G_1451935561973.jpg","g_id":389,"g_name":"速力菲（琥珀酸亚铁片）","g_size":"10片","shop_price":30,"g_symptom":"疏风解表，清热解毒。用于小儿风热感冒，症见发热、头胀痛、咳嗽痰黏、咽喉肿痛；流感见上述证候者。","img_url":"http://img.yaodaojia360.com/images/201601/thumb_img/389_thumb_G_1451935561973.jpg"},{"g_img_thumb":"images/201601/thumb_img/390_thumb_G_1451935663331.jpg","g_id":390,"g_name":"[同仁堂]耳聋左慈丸","g_size":"10片","shop_price":19,"g_symptom":"清热散风，解表止咳。用于内热外感引起的头痛，怕冷发热，咳嗽流涕，咽喉疼痛，四肢酸懒","img_url":"http://img.yaodaojia360.com/images/201601/thumb_img/390_thumb_G_1451935663331.jpg"},{"g_img_thumb":"images/201601/thumb_img/393_thumb_G_1451935783794.jpg","g_id":393,"g_name":"[养生堂]天然维生素C咀嚼片","g_size":"10片","shop_price":83,"g_symptom":"补充钙、铁、锌","img_url":"http://img.yaodaojia360.com/images/201601/thumb_img/393_thumb_G_1451935783794.jpg"},{"g_img_thumb":"images/201601/thumb_img/395_thumb_G_1451936000221.jpg","g_id":395,"g_name":"[安士制药]迪巧（儿童维D钙咀嚼片）","g_size":"10片","shop_price":32,"g_symptom":"用于气阴两虚所致的消渴病，症见多饮、多尿、多食、消瘦、体倦乏力、眠差、腰痛；2型糖尿病见上述证候者","img_url":"http://img.yaodaojia360.com/images/201601/thumb_img/395_thumb_G_1451936000221.jpg"},{"g_img_thumb":"images/201601/thumb_img/422_thumb_G_1451939420452.jpg","g_id":422,"g_name":"海王金樽","g_size":"10片","shop_price":48,"g_symptom":"清热利咽，解毒止痛。用于肺实热引起的咽喉肿痛，咳嗽痰盛，咽炎。","img_url":"http://img.yaodaojia360.com/images/201601/thumb_img/422_thumb_G_1451939420452.jpg"}],"page":1}
     */

    public boolean success;
    public DataBean data;

    public static MyClassBean objectFromData(String str) {

        return new Gson().fromJson(str, MyClassBean.class);
    }

    public static class DataBean {
        /**
         * style : [{"cat_id":1,"cat_name":"感冒常用"},{"cat_id":2,"cat_name":"清热解毒"},{"cat_id":3,"cat_name":"心脑血管"},{"cat_id":4,"cat_name":"消化系统"},{"cat_id":5,"cat_name":"妇科用药"},{"cat_id":6,"cat_name":"儿童天地"},{"cat_id":8,"cat_name":"风湿骨伤"},{"cat_id":356,"cat_name":"性福男女"},{"cat_id":357,"cat_name":"滋补养生"},{"cat_id":358,"cat_name":"医疗器材"},{"cat_id":359,"cat_name":"皮肤用药"},{"cat_id":360,"cat_name":"五官用药"},{"cat_id":362,"cat_name":"呼吸系统"},{"cat_id":363,"cat_name":"内分泌类"},{"cat_id":364,"cat_name":"神经用药"},{"cat_id":365,"cat_name":"个人护理"}]
         * goods : [{"g_img_thumb":"images/201601/thumb_img/388_thumb_G_1451935477644.jpg","g_id":388,"g_name":"叶酸片（斯利安）","g_size":"10片","shop_price":16,"g_symptom":"适用于缓解普通感冒及流行性感冒引起的发热、头痛、四肢酸痛、打喷嚏、流鼻涕、鼻塞、咽痛等症状","img_url":"http://img.yaodaojia360.com/images/201601/thumb_img/388_thumb_G_1451935477644.jpg"},{"g_img_thumb":"images/201601/thumb_img/389_thumb_G_1451935561973.jpg","g_id":389,"g_name":"速力菲（琥珀酸亚铁片）","g_size":"10片","shop_price":30,"g_symptom":"疏风解表，清热解毒。用于小儿风热感冒，症见发热、头胀痛、咳嗽痰黏、咽喉肿痛；流感见上述证候者。","img_url":"http://img.yaodaojia360.com/images/201601/thumb_img/389_thumb_G_1451935561973.jpg"},{"g_img_thumb":"images/201601/thumb_img/390_thumb_G_1451935663331.jpg","g_id":390,"g_name":"[同仁堂]耳聋左慈丸","g_size":"10片","shop_price":19,"g_symptom":"清热散风，解表止咳。用于内热外感引起的头痛，怕冷发热，咳嗽流涕，咽喉疼痛，四肢酸懒","img_url":"http://img.yaodaojia360.com/images/201601/thumb_img/390_thumb_G_1451935663331.jpg"},{"g_img_thumb":"images/201601/thumb_img/393_thumb_G_1451935783794.jpg","g_id":393,"g_name":"[养生堂]天然维生素C咀嚼片","g_size":"10片","shop_price":83,"g_symptom":"补充钙、铁、锌","img_url":"http://img.yaodaojia360.com/images/201601/thumb_img/393_thumb_G_1451935783794.jpg"},{"g_img_thumb":"images/201601/thumb_img/395_thumb_G_1451936000221.jpg","g_id":395,"g_name":"[安士制药]迪巧（儿童维D钙咀嚼片）","g_size":"10片","shop_price":32,"g_symptom":"用于气阴两虚所致的消渴病，症见多饮、多尿、多食、消瘦、体倦乏力、眠差、腰痛；2型糖尿病见上述证候者","img_url":"http://img.yaodaojia360.com/images/201601/thumb_img/395_thumb_G_1451936000221.jpg"},{"g_img_thumb":"images/201601/thumb_img/422_thumb_G_1451939420452.jpg","g_id":422,"g_name":"海王金樽","g_size":"10片","shop_price":48,"g_symptom":"清热利咽，解毒止痛。用于肺实热引起的咽喉肿痛，咳嗽痰盛，咽炎。","img_url":"http://img.yaodaojia360.com/images/201601/thumb_img/422_thumb_G_1451939420452.jpg"}]
         * page : 1
         */

        public int page;
        public List<StyleBean> style;
        public List<GoodsBean> goods;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public static class StyleBean {
            /**
             * cat_id : 1
             * cat_name : 感冒常用
             */

            public int cat_id;
            public String cat_name;

            public static StyleBean objectFromData(String str) {

                return new Gson().fromJson(str, StyleBean.class);
            }
        }

        public static class GoodsBean {
            /**
             * g_img_thumb : images/201601/thumb_img/388_thumb_G_1451935477644.jpg
             * g_id : 388
             * g_name : 叶酸片（斯利安）
             * g_size : 10片
             * shop_price : 16
             * g_symptom : 适用于缓解普通感冒及流行性感冒引起的发热、头痛、四肢酸痛、打喷嚏、流鼻涕、鼻塞、咽痛等症状
             * img_url : http://img.yaodaojia360.com/images/201601/thumb_img/388_thumb_G_1451935477644.jpg
             */

            public String g_img_thumb;
            public int g_id;
            public String g_name;
            public String g_size;
            public int shop_price;
            public String g_symptom;
            public String img_url;

            public static GoodsBean objectFromData(String str) {

                return new Gson().fromJson(str, GoodsBean.class);
            }
        }
    }
}
