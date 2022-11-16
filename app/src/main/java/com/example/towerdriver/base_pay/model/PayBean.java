package com.example.towerdriver.base_pay.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author 53288
 * @description 订单支付
 * @date 2021/6/22
 */
public class PayBean implements Serializable {

    /**
     * appid : wxfe7a299410bb670e
     * noncestr : b2oBHDMxWVICySF8Cgrf1zaLciMPz0aN
     * package : Sign=WXPay
     * partnerid : 1605858264
     * prepayid : wx22101741254526479ac9403e08c4a50000
     * timestamp : 1624328261
     * sign : 2659A24F2D08CCA28E9E5BF3391FD039
     */

    private String appid;
    private String noncestr;
    @SerializedName("package")
    private String packageX;
    private String partnerid;
    private String prepayid;
    private Integer timestamp;
    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
