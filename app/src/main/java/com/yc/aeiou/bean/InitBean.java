package com.yc.aeiou.bean;

import java.util.List;

/**
 * Created by caokun on 2019/11/4 15:43.
 */

public class InitBean {


    /**
     * contact_info : {"qq":"","tel":"13164125027","weixin":"nuanjiguiren886"}
     * payway_list : []
     * share_info : {"content":"英语音标点读由一线中小学教学名师团队和移动智能应用公司强强联合，推出的专注小学英语音标学习软件。音标是学习英语的基础，扎实的音标功底是学好英语的关键。本应用让你零基础由浅入深学音标，并且通过语音评测技术帮助矫正你的发音。","share_add_hour":1,"title":"英语音标点读APP上线了，每天十分钟，十天就会读音标！","url":"http://a.app.qq.com/o/simple.jsp?pkgname=com.yc.phonogram&amp;channel=0002160650432d595942&amp;fromc"}
     * user_info : {"is_reg":false,"status":"0","user_id":"606297","vip_test_hour":1,"vip_test_num":"0"}
     */

    public ContactInfoBean contact_info;
    public ShareInfoBean share_info;
    public UserInfoBean user_info;
    public List<?> payway_list;

    public static class ContactInfoBean {
        /**
         * qq :
         * tel : 13164125027
         * weixin : nuanjiguiren886
         */

        public String qq;
        public String tel;
        public String weixin;
    }

    public static class ShareInfoBean {
        /**
         * content : 英语音标点读由一线中小学教学名师团队和移动智能应用公司强强联合，推出的专注小学英语音标学习软件。音标是学习英语的基础，扎实的音标功底是学好英语的关键。本应用让你零基础由浅入深学音标，并且通过语音评测技术帮助矫正你的发音。
         * share_add_hour : 1
         * title : 英语音标点读APP上线了，每天十分钟，十天就会读音标！
         * url : http://a.app.qq.com/o/simple.jsp?pkgname=com.yc.phonogram&amp;channel=0002160650432d595942&amp;fromc
         */

        public String content;
        public int share_add_hour;
        public String title;
        public String url;
    }

    public static class UserInfoBean {
        /**
         * is_reg : false
         * status : 0
         * user_id : 606297
         * vip_test_hour : 1
         * vip_test_num : 0
         */

        public boolean is_reg;
        public String status;
        public String user_id;
        public int vip_test_hour;
        public String vip_test_num;
    }

    @Override
    public String toString() {
        return "InitBean{" +
                "contact_info=" + contact_info +
                ", share_info=" + share_info +
                ", user_info=" + user_info +
                ", payway_list=" + payway_list +
                '}';
    }
}
