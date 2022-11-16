package com.example.towerdriver.repair.base_order.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author 53288
 * @description
 * @date 2021/7/5
 */
public class RepairListBean implements Serializable {


    /**
     * total_page : 1
     * order : [{"id":13,"order_sn":"11625201707794","member_name":"赵晋聪","phone":"18535913153","rent_name":"车电套餐","order_status":4,"createtime":"2021-07-02 12:55","car_number":"","frame_number":"","status_name":"使用中"}]
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
         * id : 13
         * order_sn : 11625201707794
         * member_name : 赵晋聪
         * phone : 18535913153
         * rent_name : 车电套餐
         * order_status : 4
         * createtime : 2021-07-02 12:55
         * car_number :
         * frame_number :
         * status_name : 使用中
         */

        private Integer id;
        private String order_sn;
        private String member_name;
        private String phone;
        private String rent_name;
        private Integer order_status;
        private String createtime;
        private String car_number;
        private String frame_number;
        private String status_name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRent_name() {
            return rent_name;
        }

        public void setRent_name(String rent_name) {
            this.rent_name = rent_name;
        }

        public Integer getOrder_status() {
            return order_status;
        }

        public void setOrder_status(Integer order_status) {
            this.order_status = order_status;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getCar_number() {
            return car_number;
        }

        public void setCar_number(String car_number) {
            this.car_number = car_number;
        }

        public String getFrame_number() {
            return frame_number;
        }

        public void setFrame_number(String frame_number) {
            this.frame_number = frame_number;
        }

        public String getStatus_name() {
            return status_name;
        }

        public void setStatus_name(String status_name) {
            this.status_name = status_name;

        }
    }
}
