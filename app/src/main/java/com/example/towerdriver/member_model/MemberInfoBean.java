package com.example.towerdriver.member_model;

/**
 * @author 53288
 * @description 会员信息
 * @date 2021/6/9
 */
public class MemberInfoBean {

    /**
     * member_img : http://pic.tdpower.net/member/default.png
     * type : 1
     * if_intact : 1
     * name : 赵晋聪
     * phone : 18535913153
     * qr_image : http://rentcar.me/uploads/member/qr_image/1.png
     * integral_image : http://pic.tdpower.net/uploads/20210607/78f4e110dcaa0d9d33a20b18286a2588.png
     * integral : 0
     * card_number : 14272719930109201X
     * card_front : http://pic.tdpower.net/uploads/member/qr_image/1.png
     * card_reverse : http://pic.tdpower.net/uploads/member/qr_image/1.png
     * balance : 0.00
     * if_withdraw : 0
     * if_rescue : 0
     */

    private String member_img;
    private Integer type;
    private Integer if_intact;
    private String name;
    private String phone;
    private String qr_image;
    private String integral_image;
    private Integer integral;
    private String card_number;
    private String card_front;
    private String card_reverse;
    private String balance;
    private Integer if_withdraw;
    private Integer if_rescue;
    private Integer if_residue;
    private Long residue_num;

    public String getMember_img() {
        return member_img;
    }

    public void setMember_img(String member_img) {
        this.member_img = member_img;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIf_intact() {
        return if_intact;
    }

    public void setIf_intact(Integer if_intact) {
        this.if_intact = if_intact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQr_image() {
        return qr_image;
    }

    public void setQr_image(String qr_image) {
        this.qr_image = qr_image;
    }

    public String getIntegral_image() {
        return integral_image;
    }

    public void setIntegral_image(String integral_image) {
        this.integral_image = integral_image;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getCard_front() {
        return card_front;
    }

    public void setCard_front(String card_front) {
        this.card_front = card_front;
    }

    public String getCard_reverse() {
        return card_reverse;
    }

    public void setCard_reverse(String card_reverse) {
        this.card_reverse = card_reverse;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Integer getIf_withdraw() {
        return if_withdraw;
    }

    public void setIf_withdraw(Integer if_withdraw) {
        this.if_withdraw = if_withdraw;
    }

    public Integer getIf_rescue() {
        return if_rescue;
    }

    public void setIf_rescue(Integer if_rescue) {
        this.if_rescue = if_rescue;
    }

    public Integer getIf_residue() {
        return if_residue;
    }

    public void setIf_residue(Integer if_residue) {
        this.if_residue = if_residue;
    }

    public Long getResidue_num() {
        return residue_num;
    }

    public void setResidue_num(Long residue_num) {
        this.residue_num = residue_num;
    }
}
