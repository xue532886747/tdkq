package com.example.towerdriver.base_order_list.model;

/**
 * @author 53288
 * @description 订单详情
 * @date 2021/6/21
 */
public class OrderDetailBean {

    /**
     * order_sn : 41623678713892
     * classify_name : 分类1
     * rent_name : 单电
     * car_number :
     * frame_number :
     * rent_price : 10
     * cash_price : 200
     * order_status : 已取消
     * payment_code : 无
     * createtime : 2021-06-14 21:51
     * start_time :
     * return_time :
     * expire_time :
     * renew_price : 0
     */

    private String order_sn;
    private String classify_name;
    private String rent_name;
    private String car_number;
    private String frame_number;
    private String rent_price;
    private String cash_price;
    private String order_status;
    private String status_name;
    private String payment_code;
    private String createtime;
    private String start_time;
    private String return_time;
    private String expire_time;
    private String renew_price;

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getClassify_name() {
        return classify_name;
    }

    public void setClassify_name(String classify_name) {
        this.classify_name = classify_name;
    }

    public String getRent_name() {
        return rent_name;
    }

    public void setRent_name(String rent_name) {
        this.rent_name = rent_name;
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

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getPayment_code() {
        return payment_code;
    }

    public void setPayment_code(String payment_code) {
        this.payment_code = payment_code;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getReturn_time() {
        return return_time;
    }

    public void setReturn_time(String return_time) {
        this.return_time = return_time;
    }

    public String getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(String expire_time) {
        this.expire_time = expire_time;
    }

    public String getRenew_price() {
        return renew_price;
    }

    public void setRenew_price(String renew_price) {
        this.renew_price = renew_price;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }
}
