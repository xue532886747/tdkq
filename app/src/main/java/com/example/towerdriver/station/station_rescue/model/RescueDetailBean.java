package com.example.towerdriver.station.station_rescue.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * @author 53288
 * @description 救援详情
 * @date 2021/7/1
 */
public class RescueDetailBean {

    /**
     * id : 202
     * member_id : 10
     * member_name : 赵晋聪
     * member_phone : 18535913153
     * reason : 电
     * content : 我在北大街
     * lng : 1
     * lat : 1
     * address :
     * status : 1
     * sell_id : 1
     * sell_name : 1
     * sell_phone : 1
     * createtime : 2021-06-29 20:41
     * accept_time :
     * rescue_time :
     * end_time :
     * images : ["http://pic.tdpower.net/sell_image/c765020f70546e0c9c89c40166d85d64W4qUMUqt9A.png","http://pic.tdpower.net/sell_image/c765020f70546e0c9c89c40166d85d64W4qUMUqt9A.png"]
     * remark :
     * score :
     * evaluate :
     * status_name : 待接单
     */

    private Integer id;
    private Integer member_id;
    private String member_name;
    private String member_phone;
    private String reason;
    private String content;
    private String lng;
    private String lat;
    private String address;
    private Integer status;
    private Integer sell_id;
    private String sell_name;
    private String sell_phone;
    private String createtime;
    private String accept_time;
    private String rescue_time;
    private String end_time;
    private String remark;
    private String score;
    private String evaluate;
    private String status_name;
    private List<String> images;
    private int sType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSell_id() {
        return sell_id;
    }

    public void setSell_id(Integer sell_id) {
        this.sell_id = sell_id;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }


}
