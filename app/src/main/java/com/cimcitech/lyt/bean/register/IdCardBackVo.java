package com.cimcitech.lyt.bean.register;

/**
 * Created by qianghe on 2018/9/21.
 */

public class IdCardBackVo {
    private String reason;
    private IdCardBackResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public IdCardBackResultBean getResult() {
        return result;
    }

    public void setResult(IdCardBackResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public class IdCardBackResultBean{
        private String begin;
        private String department;
        private String end;
        private String side;

        public String getBegin() {
            return begin;
        }

        public void setBegin(String begin) {
            this.begin = begin;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getSide() {
            return side;
        }

        public void setSide(String side) {
            this.side = side;
        }
    }
}
