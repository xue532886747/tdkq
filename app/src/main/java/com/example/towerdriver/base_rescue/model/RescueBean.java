package com.example.towerdriver.base_rescue.model;

import java.util.List;

/**
 * @author 53288
 * @description
 * @date 2021/7/2
 */
public class RescueBean {


    /**
     * id : 212
     * member_name : 薛瑞丰
     * member_phone : 15667162027
     * reason : 车
     * content : 7好的好的好的
     * lng : 34.189435
     * lat : 108.891637
     * address : 中国陕西省西安市雁塔区电子城街道锦业二路
     * status : 已完成
     * sell_name : xuezei
     * sell_phone : 15667162027
     * createtime : 2021-07-01 17:58
     * accept_time : 2021-07-01 17:58
     * rescue_time : 1625205713
     * end_time : 2021-07-02 14:27
     * images : ["http://pic.tdpower.net/sell_save/bd4de90432dec2157f61a4136d963bd5JTNZWL43aL.jpg"]
     * score :
     * evaluate :
     * company_id : 1
     */

    private Integer id;
    private String member_name;
    private String member_phone;
    private String reason;
    private String content;
    private String lng;
    private String lat;
    private String address;
    private String status;
    private String sell_name;
    private String sell_phone;
    private String createtime;
    private String accept_time;
    private String rescue_time;
    private String end_time;
    private String score;
    private String evaluate;
    private Integer company_id;
    private List<String> images;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSell_name() {
        return sell_name;
    }

    public void setSell_name(String sell_name) {
        this.sell_name = sell_name;
    }

    public String getSell_phone() {
        return sell_phone;
    }

    public void setSell_phone(String sell_phone) {
        this.sell_phone = sell_phone;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getAccept_time() {
        return accept_time;
    }

    public void setAccept_time(String accept_time) {
        this.accept_time = accept_time;
    }

    public String getRescue_time() {
        return rescue_time;
    }

    public void setRescue_time(String rescue_time) {
        this.rescue_time = rescue_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

}
