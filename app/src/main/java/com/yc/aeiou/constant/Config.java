package com.yc.aeiou.constant;

public class Config {
    public static final boolean DEBUG = false;

    private static String baseUrl = "http://tic.upkao.com/api/";
    private static String debugBaseUrl = "http://np.197734.com/api/";
    public static String APPID = "?app_id=5";
    public static final String INIT_URL = getBaseUrl() + "index/init" + APPID;
    public static final String LIST_URL = getBaseUrl() + "index/phonetic_list"+ APPID;
    public static final String CLASS_URL = getBaseUrl() + "index/phonetic_class"+ APPID;
    public static final String JUMP_NUM_LOG = getBaseUrl() + "jump/jump_num_log"+ APPID;
    public static final String RECORD_URL = getBaseUrl() + "jump/user_jump_list"+ APPID;

    public static String getBaseUrl() {
        return (DEBUG ? debugBaseUrl : baseUrl);
    }
}
