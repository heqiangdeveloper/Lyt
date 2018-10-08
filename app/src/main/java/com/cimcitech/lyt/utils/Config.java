package com.cimcitech.lyt.utils;

import android.content.Context;

import com.cimcitech.lyt.bean.login.Loginback;

/**
 * Created by cimcitech on 2017/7/31.
 */

public class Config {
    //是否需要调用聚合数据的开源OCR身份证识别
    public static boolean isNeedVerify = false;

    public static  String  USERNAME = "";//用户登录名

    public static int ACCOUNTID = 0;

    public static String ACCOUNTNO = "";

    public static int ACCOUNTTYPE = 0;

    public static String TOKEN = "";

    public static boolean isLogin = false;

    public static Loginback loginback;

    public static String KEY_LOGIN_AUTO = "key_login_auto";

    public static int unReadMsg = 0;

    //聚合数据appkey
    public static final String APPKEY= "bf90b08a2f5cf540d796ae413cad2660";

    public static String PUBLIC_KEY =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDqB+OB1GMmNsOushhd3nHKO3OsextO+UYjtxUBLViHIQ"
                    + "qrwaRnN2NU0vg0WBSpO0qtArglvCDWbThZgdApw1AJvUH3Rl9cMajoMQo3PFZqzJbwd6mwmmaiWmNPOsYL+4pVr/ndG23QjUMthS0h1K9"
                    + "9YVtfbwZUW9OkFn0bSk970QIDAQAB";
    public static String STR_PRI_KEY =
            "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOoH44HUYyY2w66yGF3ecco7c6x7G075RiO3FQEt"
                    + "WIchCqvBpGc3Y1TS+DRYFKk7Sq0CuCW8INZtOFmB0CnDUAm9QfdGX1wxqOgxCjc8VmrMlvB3qbCaZqJaY086xgv7ilWv+d0bbdCNQy2FLSHUr31h"
                    + "W19vBlRb06QWfRtKT3vRAgMBAAECgYEA3P1YAtfITGR4QXVg9vq/XqTnJN8WuWs/pSgTyInGR8uWpPt0wBaYUJfq0pq939PiPx8LPm64X2LBTBv9s"
                    + "gIBh3JNb/S/7SSyUe5GLRPcaqXclljUGuo6i5XYpBccudgaDd2HSiirouBEgzeYdOdgrowMPzwG0EPW5M4l+B5pvGUCQQD9IqI++zgRAhChj9OcEGn"
                    + "5UaKAEucOd78oJexxRuVmXfTLkeNqNMCgQPuQyWC6RU2D9+KrdFIQafRZacSQ4GUjAkEA7K3oIRwMDKHd+3hwd1wBFsB3P+8ZRq29QcSegn2rghdVz"
                    + "8vj92pVDgBXAsQc1uWvOg9YecNXlA1/UXoE2m3MewJAYG88TjAYiOo3ecU4QYFDThcaiB4rZiiaa2ofLHxs0lXC93apo4FbTPHUpLEDJ5NVV4wtD1"
                    + "JuHRCwZQvVNbg27wJAGS23EC6nqYBe2S3K6SfEheMbzJVnHvnEtIZyRneQE1rgKicKrXuGaOLoXsb2OnZc2dRgGTQK0RlOfiB4+7PKmQJBAJqmete39"
                    + "YPDMQdQCUeuIl1Mt/68goYZvOMeXgHAObD47g0G9jDXSWitYvKpU8Ad+DNaiZLQjcHX2OOcKeRuqzA=";
    public static Context context;
    public static String password;
    public static Long userId;

    public static boolean isUserNameVaild = false;
    public static boolean isSubordinate = false;
    public static boolean isAddMyClient = false;
    public static boolean isAddVisit = false;
    public static boolean isAddPerson = false;
    public static boolean isAddTrack = false;
    public static boolean isFollowUp = false;
    public static boolean isAddWork = false;
    public static boolean isQuotedPrice = false;
    public static int type = 1; //周报类型  1.今天 2.本周 3.历史

    //public static final String IP = "http://test.lingyu.com:8081/ly";  //测试

    public static final String IP = "http://10.43.10.142:8081/lyt";  //本机2

    //public static final String IP = "http://192.168.2.91:8081/lyt";//本机

    //public static final String IP = "http://113.106.81.132:8086/tdsh";//zxq

    //public static final String IP = "http://10.34.10.79:8080/tdsh";//zxq

    //public static final String IP = "http://10.34.42.122:8080/tdsh";//hmn

    public static final String USER_LOGIN_URL = IP + "/account/appLogin"; //登录

    public static final String VERIFICATION_CODE_URL = IP + "/account/sendCheckCode"; //注册的验证码

    public static final String REGISTER_USER_URL = IP + "/account/register"; //注册新用户

    public static final String FORGET_PASSWORD_URL = IP + "/account/forgetPassword"; //忘记密码

    public static final String IDCARD_IDENTIFY_URL= "http://apis.juhe.cn/idimage/verify";
    //调用聚合数据api,身份证OCR识别

    public static final String MODIFY_PASSWORD = IP + "/users/updPassword"; //修改密码

    public static final String QUERY_USERINFO_URL = IP + "/users/list"; //查询所有用户信息

    public static final String QUERY_BARGE_URL = IP + "/barge/pageListBarge"; //查询驳船信息

    public static final String DELETE_BARGE_URL = IP + "/barge/deleteBarge"; //删除驳船信息

    public static final String ADD_BARGE_URL = IP + "/barge/addBarge"; //新增驳船信息

    public static final String UPDATE_BARGE_URL = IP + "/barge/updateBarge"; //修改驳船信息

    public static final String QUERY_BARGECABIN_URL = IP + "/bargeCabin/queryBargeCabin";
    //查询驳船对应的船舱信息

    public static final String ADD_BARGECABIN_URL = IP + "/bargeCabin/addBargeCabin"; //添加驳船对应的船舱信息

    public static final String UPDATE_BARGECABIN_URL = IP + "/bargeCabin/updateBargeCabin"; //修改驳船对应的船舱信息

    public static final String DELETE_BARGECABIN_URL = IP + "/bargeCabin/deleteBargeC"; //删除驳船对应的船舱信息
}
