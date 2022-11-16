package com.example.towerdriver.member_model;

/**
 * @author 53288
 * @description 会员信息
 * @date 2021/6/9
 */
public interface IMemberModel {

    void getMemberSuccess(MemberInfoBean memberInfoBean, String msg);

    void getMemberFailure(String msg);

    void getTokenFailure();

    void getCodeElseFailure(int code, String msg);
}
