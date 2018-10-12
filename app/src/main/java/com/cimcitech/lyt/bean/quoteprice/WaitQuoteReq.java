package com.cimcitech.lyt.bean.quoteprice;

/**
 * Created by qianghe on 2018/10/9.
 */

public class WaitQuoteReq {
    private TranSportBean tranSport;

    public WaitQuoteReq(TranSportBean tranSport) {
        this.tranSport = tranSport;
    }

    public static class TranSportBean{
        private String startingport;
        private String aimport;
        private int pageNum;
        private int pageSize;
        private String orderBy;

        public TranSportBean(String startingport, String aimport, int pageNum, int pageSize, String orderBy) {
            this.startingport = startingport;
            this.aimport = aimport;
            this.pageNum = pageNum;
            this.pageSize = pageSize;
            this.orderBy = orderBy;
        }

        public String getStartingport() {
            return startingport;
        }

        public void setStartingport(String startingport) {
            this.startingport = startingport;
        }

        public String getAimport() {
            return aimport;
        }

        public void setAimport(String aimport) {
            this.aimport = aimport;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }
    }
}
