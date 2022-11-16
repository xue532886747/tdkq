package com.example.towerdriver.base_order_list.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author 53288
 * @description 订单列表
 * @date 2021/6/17
 */
public class OrderListBean implements Serializable {


    /**
     * total_page : 1
     * order : [{"id":12,"order_sn":"41623726758501","rent_id":1,"rent_name":"单电","rent_price":10,"cash_price":0,"order_status":1,"createtime":"2021-06-15 11:12","expire_time":"2021-06-15 23:59","total_price":10,"status_name":"待支付"},{"id":2,"order_sn":"41623678713892","rent_id":1,"rent_name":"单电","rent_price":10,"cash_price":200,"order_status":0,"createtime":"2021-06-14 21:51","expire_time":"1970-01-01 08:00","total_price":null,"status_name":"已取消"}]
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
         * id : 12
         * order_sn : 41623726758501
         * rent_id : 1
         * rent_name : 单电
         * rent_price : 10
         * cash_price : 0
         * order_status : 1
         * createtime : 2021-06-15 11:12
         * expire_time : 2021-06-15 23:59
         * total_price : 10
         * status_name : 待支付
         * payment_code
         */

        private Integer id;
        private String order_sn;
        private Integer rent_id;
        private String rent_name;
        private String rent_price;
        private String cash_price;
        private Integer order_status;
        private String createtime;
        private String expire_time;
        private String pay_price;
        private String status_name;
        private Integer payment_code;

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

        public Integer getRent_id() {
            return rent_id;
        }

        public void setRent_id(Integer rent_id) {
            this.rent_id = rent_id;
        }

        public String getRent_name() {
            return rent_name;
        }

        public void setRent_name(String rent_name) {
            this.rent_name = rent_name;
        }

        public String getRent_price() {
            return rent_price;
        }

        public void setRent_price(String rent_price) {
            this.rent_price = rent_price;
        }

        public String getCash_price() {
            return cash_price;
        }

        public void setCash_price(String cash_price) {
            this.cash_price = cash_price;
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

        public String getExpire_time() {
            return expire_time;
        }

        public void setExpire_time(String expire_time) {
            this.expire_time = expire_time;
        }

        public String getPay_price() {
            return pay_price;
        }

        public void setPay_price(String pay_price) {
            this.pay_price = pay_price;
        }

        public String getStatus_name() {
            return status_name;
        }

        public void setStatus_name(String status_name) {
            this.status_name = status_name;
        }

        public Integer getPayment_code() {
            return payment_code;
        }

        public void setPayment_code(Integer payment_code) {
            this.payment_code = payment_code;
        }
    }
}
