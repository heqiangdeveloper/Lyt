package com.cimcitech.lyt.bean.user;

/**
 * Created by apple on 2017/8/15.
 */

public class ApkUpdateVo {


    /**
     * data : {"apkurl":"http://test.lingyu.com:8081/cimcly_test.apk","appdescription":"凌宇智汇平台app","appname":"lyzh","createtime":1503377812000,"id":2,"status":null,"versioncode":3,"versionname":"V3.0"}
     * msg :
     * success : true
     */

    private DataBean data;
    private String msg;
    private boolean success;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * apkurl : http://test.lingyu.com:8081/cimcly_test.apk
         * appdescription : 凌宇智汇平台app
         * appname : lyzh
         * createtime : 1503377812000
         * id : 2
         * status : null
         * versioncode : 3
         * versionname : V3.0
         */

        private String apkurl;
        private String appdescription;
        private String appname;
        private long createtime;
        private int id;
        private Object status;
        private int versioncode;
        private String versionname;

        public String getApkurl() {
            return apkurl;
        }

        public void setApkurl(String apkurl) {
            this.apkurl = apkurl;
        }

        public String getAppdescription() {
            return appdescription;
        }

        public void setAppdescription(String appdescription) {
            this.appdescription = appdescription;
        }

        public String getAppname() {
            return appname;
        }

        public void setAppname(String appname) {
            this.appname = appname;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public int getVersioncode() {
            return versioncode;
        }

        public void setVersioncode(int versioncode) {
            this.versioncode = versioncode;
        }

        public String getVersionname() {
            return versionname;
        }

        public void setVersionname(String versionname) {
            this.versionname = versionname;
        }
    }
}
