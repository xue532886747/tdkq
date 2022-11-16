package com.example.towerdriver.station.station_rescue.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author 53288
 * @description
 * @date 2021/7/1
 */
public class StationRescueBean implements Serializable {


    /**
     * total_page : 1
     * order : [{"id":202,"member_name":"赵晋聪","member_phone":"18535913153","reason":"电","lng":"1","lat":"1","createtime":"2021-06-29 20:41","status":1,"status_name":"待接单"}]
     */

    private Integer total_page;
    private List<OrderBean> order;

    public Integer getTotal_page() {
        return total_page;
    }

    public void setTotal_page(Integer total_page) {
        this.total_page = total_page;
    }

    public List<OrderBean> getOrder() {
        return order;
    }

    public void setOrder(List<OrderBean> order) {
        this.order = order;
    }

    public static class OrderBean implements Serializable {
        /**
         * id : 202
         * member_name : 赵晋聪
         * member_phone : 18535913153
         * reason : 电
         * lng : 1
         * lat : 1
         * createtime : 2021-06-29 20:41
         * status : 1
         * status_name : 待接单
         */

        private Integer id;
        private String member_name;
        private String member_phone;
        private String reason;
        private String lng;
        private String lat;
        private String createtime;
        private Integer status;
        private String status_name;

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

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getStatus_name() {
            return status_name;
        }

        public void setStatus_name(String status_name) {
            this.status_name = status_name;
        }
    }
}
