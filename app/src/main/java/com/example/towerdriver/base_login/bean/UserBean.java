package com.example.towerdriver.base_login.bean;

/**
 * @author 53288
 * @description 用户user
 * @date 2021/5/25
 */
public class UserBean {


    /**
     * token : uNrX9apQysDlaeqHcRDx32WD78fwpNuJw8dq2K01GeDbZ4eYl6
     * member_img : http://pic.tdpower.net/member/default.png
     * type : 1
     * if_intact : 1
     * name : 1
     * phone : 18535913153
     * qr_image : http://rentcar.me/uploads/member/qr_image/4.png
     * integral_image : http://pic.tdpower.net/uploads/20210607/78f4e110dcaa0d9d33a20b18286a2588.png
     * member_id: 4
     * if_rescue: 0是否允许救援:0=不允许,1=允许
     * if_withdraw: 0,是否允许提现:0=不允许,1=允许
     */

    private String token;
    private String member_img;
    private Integer type;
    private Integer if_intact;
    private String name;
    private String phone;
    private String qr_image;
    private String integral_image;
    private Integer login_type;
    private Integer member_id;
    private Integer if_rescue;
    private Integer if_withdraw;
    private String image;


    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getLogin_type() {
        return login_type;
    }

    public void setLogin_type(Integer login_type) {
        this.login_type = login_type;
    }

    public Integer getIf_rescue() {
        return if_rescue;
    }

    public void setIf_rescue(Integer if_rescue) {
        this.if_rescue = if_rescue;
    }

    public Integer getIf_withdraw() {
        return if_withdraw;
    }

    public void setIf_withdraw(Integer if_withdraw) {
        this.if_withdraw = if_withdraw;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "token='" + token + '\'' +
                ", member_img='" + member_img + '\'' +
                ", type=" + type +
                ", if_intact=" + if_intact +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", qr_image='" + qr_image + '\'' +
                ", integral_image='" + integral_image + '\'' +
                ", login_type=" + login_type +
                ", member_id=" + member_id +
                '}';
    }
}
