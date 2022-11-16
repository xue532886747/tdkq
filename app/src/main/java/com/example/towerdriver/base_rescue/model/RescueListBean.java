package com.example.towerdriver.base_rescue.model;

import java.util.List;

/**
 * @author 53288
 * @description 救援列表
 * @date 2021/6/21
 */
public class RescueListBean {

    /**
     * total_page : 1
     * rescue_order : [{"id":201,"member_name":"1","member_phone":"18535913153","reason":"电","status":1,"createtime":"2021-06-17 10:47","status_name":"待接单"}]
     */

    private Integer total_page;
    private List<RescueOrderBean> rescue_order;

    public Integer getTotal_page() {
        return total_page;
    }

    public void setTotal_page(Integer total_page) {
        this.total_page = total_page;
    }

    public List<RescueOrderBean> getRescue_order() {
        return rescue_order;
    }

    public void setRescue_order(List<RescueOrderBean> rescue_order) {
        this.rescue_order = rescue_order;
    }

    public static class RescueOrderBean {
        /**
         * id : 201
         * member_name : 1
         * member_phone : 18535913153
         * reason : 电
         * status : 1
         * createtime : 2021-06-17 10:47
         * status_name : 待接单
         */

        private Integer id;
        private String member_name;
        private String member_phone;
        private String reason;
        private Integer status;
        private String createtime;
        private String status_name;
        private String lng;
        private String lat;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public String getMember_phone() {
            return member_phone;
        }

        public void setMember_phone(String member_phone) {
            this.member_phone = member_phone;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getStatus_name() {
            return status_name;
        }

        public void setStatus_name(String status_name) {
            this.status_name = status_name;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }
}
