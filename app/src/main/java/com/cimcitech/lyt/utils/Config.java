package com.cimcitech.lyt.utils;

import android.content.Context;

import com.cimcitech.lyt.bean.login.Loginback;

/**
 * Created by cimcitech on 2017/7/31.
 */

public class Config {

    public static String AppAuthStr = "";

    public static  String  userName = "";//用户登录名

    public static  String  realName = "";//用户真实的姓名

    public static  boolean isLeader = false;

    public static boolean isLogin = false;

    public static Loginback loginback;

    public static String KEY_LOGIN_AUTO = "key_login_auto";

    public static int unReadMsg = 0;

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

    //public static final String IP = "http://zh.lingyu.com:8081/ly";  //正式

    //public static final String IP = "http://10.34.10.21:8080/tdsh";//本机

    public static final String IP = "http://113.106.81.132:8086/tdsh";//zxq

    //public static final String IP = "http://10.34.10.79:8080/tdsh";//zxq

    //public static final String IP = "http://10.34.42.122:8080/tdsh";//hmn

    public static final String USER_LOGIN_URL = IP + "/users/login"; //登录

    public static final String VERIFICATION_CODE_URL = IP + "/users/sendMsg"; //注册的验证码

    public static final String REGISTER_USER_URL = IP + "/users/reg"; //注册新用户

    public static final String MODIFY_PASSWORD = IP + "/users/updPassword"; //修改密码

    public static final String QUERY_USERINFO_URL = IP + "/users/list"; //查询所有用户信息

    public static final String QUERY_ROLEINFO_URL = IP + "/roles/queryRoles"; //查询所有角色信息

    public static final String FILE_SEARCH_URL = IP + "/saleContDet/list"; //合同文档查询

    public static final String FILE_SEARCH_DETAIL_URL = IP + "/file/queryByDetailId"; //合同文档对应文件查询

    //public static final String FILE_DOWNLOAD_URL = IP + "/file/download"; //文件下载

    public static final String FILE_DOWNLOAD_URL = IP + "/file/downloadSaleContFile"; //文件下载

    public static final String IMAGE_DOWNLOAD_URL = IP + "/file/showIcon2App?iconUrl=";
    //图片下载

    public static final String QUERY_CONTACT_URL = IP + "/contact/list"; //查询联系人

    public static final String UPDATE_CONTACT_STATE_URL = IP + "/contact/modifyContactState"; //修改联系人状态

    public static final String GET_POPWIN_DATA_URL = IP + "/baseCodeValue/getAllBaseValueByCodeType"; //获取下拉数据

    public static final String ADD_CONTACT_URL = IP + "/contact/addContact"; //新增联系人信息

    public static final String UPDATE_CONTACT_URL = IP + "/contact/modifyContact"; //修改联系人信息

    public static final String DELETE_CONTACT_URL = IP + "/contact/deleteContact"; //删除联系人

    public static final String GET_ALL_CUST_URL = IP + "/cust/list"; //获取所有的客户信息（可分页）

    public static final String QUERY_DISPATCH_SALE_URL = IP +
            "/saleMaintenPlan/queryByRegionCharge"; //查询派工信息,当合同类型=销售合同时调用

    public static final String QUERY_DISPATCH_MAIN_URL = IP +
            "/maintenancePlan/queryByRegionCharge"; //查询派工信息,当合同类型=维保合同时调用

    public static final String SALE_CONFIRM_PLAN_URL = IP + "/saleMaintenPlan/confirmSaleMaintenPlan";
    //确认接收派工单，当合同类型=销售合同时调用

    public static final String MAIN_CONFIRM_PLAN_URL = IP + "/maintenancePlan/confirmMaintenancePlan";
    //确认接收派工单,当合同类型=维保合同时调用

    public static final String QUERY_FAULT_HANDLE = IP + "/faultHandle/list"; //查询故障处理办法

    public static final String announceDetail = IP + "/announce/getCurrInfo"; //获取公告详情

    public static final String announceList = IP + "/announce/pageList"; //获取公告列表

    public static final String getProviceAndCity = IP + "/cate/getProviceAndCity"; //省市

    public static final String updateApk = IP + "/appUpdate/getNewAppUpdate"; //更新apk
}
