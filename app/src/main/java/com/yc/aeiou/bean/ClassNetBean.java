package com.yc.aeiou.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caokun on 2019/11/5 10:06.
 */

public class ClassNetBean implements Serializable {


    /**
     * list : [{"id":"9","cover":"http://voice.wk2.com/img/2017091801.jpg","video":"http://voice.wk2.com/video/2017091801.mp4\t","title":"说说英语趣味音标课        ","desp":"共十二节微课，包教包会！","sort":"500","add_time":"0","app_id":"5"},{"id":"10","cover":"http://voice.wk2.com/img/2017091802.jpg","video":"http://voice.wk2.com/video/2017091802.mp4\t","title":"说说英语趣味音标课         ","desp":"第二课：前元音","sort":"500","add_time":"0","app_id":"5"},{"id":"11","cover":"http://voice.wk2.com/img/2017091803.jpg","video":"http://voice.wk2.com/video/2017091803.mp4\t","title":"说说英语趣味音标课        ","desp":"第三课：爆破音","sort":"500","add_time":"0","app_id":"5"},{"id":"12","cover":"http://voice.wk2.com/img/2017091804.jpg","video":"http://voice.wk2.com/video/2017091804.mp4\t","title":"说说英语趣味音标课        ","desp":"第四课：中元音和后元音","sort":"500","add_time":"0","app_id":"5"},{"id":"13","cover":"http://voice.wk2.com/img/2017091805.jpg","video":"http://voice.wk2.com/video/2017091805.mp4\t","title":"说说英语趣味音标课        ","desp":"第五课：摩擦音","sort":"500","add_time":"0","app_id":"5"},{"id":"14","cover":"http://voice.wk2.com/img/2017091806.jpg","video":"http://voice.wk2.com/video/2017091806.mp4\t","title":"说说英语趣味音标课        ","desp":"第六课：后元音","sort":"500","add_time":"0","app_id":"5"},{"id":"15","cover":"http://voice.wk2.com/img/2017091807.jpg","video":"http://voice.wk2.com/video/2017091807.mp4\t","title":"说说英语趣味音标课        ","desp":"第七课：摩擦音和半元音","sort":"500","add_time":"0","app_id":"5"},{"id":"16","cover":"http://voice.wk2.com/img/2017091808.jpg","video":"http://voice.wk2.com/video/2017091808.mp4\t","title":"说说英语趣味音标课        ","desp":"第八课：合口双元音","sort":"500","add_time":"0","app_id":"5"},{"id":"17","cover":"http://voice.wk2.com/img/2017091809.jpg","video":"http://voice.wk2.com/video/2017091809.mp4\t","title":"说说英语趣味音标课        ","desp":"第九课：破擦音","sort":"500","add_time":"0","app_id":"5"},{"id":"18","cover":"http://voice.wk2.com/img/2017091810.jpg","video":"http://voice.wk2.com/video/2017091810.mp4\t","title":"说说英语趣味音标课        ","desp":"第十课：集中双元音","sort":"500","add_time":"0","app_id":"5"},{"id":"19","cover":"http://voice.wk2.com/img/2017091811.jpg","video":"http://voice.wk2.com/video/2017091811.mp4\t","title":"说说英语趣味音标课        ","desp":"第十一课：鼻音和舌侧音","sort":"500","add_time":"0","app_id":"5"},{"id":"20","cover":"http://voice.wk2.com/img/2017091812.jpg","video":"http://voice.wk2.com/video/2017091812.mp4\t","title":"说说英语趣味音标课        ","desp":"第十二课：音标复习","sort":"500","add_time":"0","app_id":"5"}]
     * info : {"id":"2","app_id":"5","title":"微课","type":null,"alias":"","price":"89.00","real_price":"49.00","desp":"","status":"0","sort":"3","attr":"0","field1":"1","field2":"0","icon":"http://tic.upkao.com/Upload/icon/good_info_num3.png","sub_title":"24节原创微课协助记忆音标！"}
     */

    public InfoBean info;
    public List<ListBean> list;

    public static class InfoBean implements Serializable {
        /**
         * id : 2
         * app_id : 5
         * title : 微课
         * type : null
         * alias :
         * price : 89.00
         * real_price : 49.00
         * desp :
         * status : 0
         * sort : 3
         * attr : 0
         * field1 : 1
         * field2 : 0
         * icon : http://tic.upkao.com/Upload/icon/good_info_num3.png
         * sub_title : 24节原创微课协助记忆音标！
         */

        public String id;
        public String app_id;
        public String title;
        public Object type;
        public String alias;
        public String price;
        public String real_price;
        public String desp;
        public String status;
        public String sort;
        public String attr;
        public String field1;
        public String field2;
        public String icon;
        public String sub_title;

        @Override
        public String toString() {
            return "InfoBean{" +
                    "id='" + id + '\'' +
                    ", app_id='" + app_id + '\'' +
                    ", title='" + title + '\'' +
                    ", type=" + type +
                    ", alias='" + alias + '\'' +
                    ", price='" + price + '\'' +
                    ", real_price='" + real_price + '\'' +
                    ", desp='" + desp + '\'' +
                    ", status='" + status + '\'' +
                    ", sort='" + sort + '\'' +
                    ", attr='" + attr + '\'' +
                    ", field1='" + field1 + '\'' +
                    ", field2='" + field2 + '\'' +
                    ", icon='" + icon + '\'' +
                    ", sub_title='" + sub_title + '\'' +
                    '}';
        }
    }

    public static class ListBean implements Serializable {
        /**
         * id : 9
         * cover : http://voice.wk2.com/img/2017091801.jpg
         * video : http://voice.wk2.com/video/2017091801.mp4
         * title : 说说英语趣味音标课
         * desp : 共十二节微课，包教包会！
         * sort : 500
         * add_time : 0
         * app_id : 5
         */

        public String id;
        public String cover;
        public String video;
        public String title;
        public String desp;
        public String sort;
        public String add_time;
        public String app_id;

        @Override
        public String toString() {
            return "ListBean{" +
                    "id='" + id + '\'' +
                    ", cover='" + cover + '\'' +
                    ", video='" + video + '\'' +
                    ", title='" + title + '\'' +
                    ", desp='" + desp + '\'' +
                    ", sort='" + sort + '\'' +
                    ", add_time='" + add_time + '\'' +
                    ", app_id='" + app_id + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ClassNetBean{" +
                "info=" + info +
                ", list=" + list +
                '}';
    }
}
