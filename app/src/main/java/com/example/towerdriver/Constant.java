package com.example.towerdriver;

import android.os.Environment;

import com.example.towerdriver.utils.sp.BaiDuAccessUtils;

/**
 * @author 53288
 * @description
 * @date 2021/5/19
 */
public interface Constant {
    String WxAppId = "wxfe7a299410bb670e";
    String WxAPPKey = "956NEWtdnysdfhhynbdsd5165rgsdvsd";
    String OUTPUTDIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();    //保存的路径
    int REQUEST_CODE_SCAN = 111;        //二维码
    int CHOOSE_PHOTO = 300;
    int TAKE_PHOTO = 301;
    String TOKEN_EXPIRED = "登录过期,请重新登陆";

    String CLIENT_ID = "H83X7PGfMraMRnEI3noCEr5b";  //百度面部识别client_id
    String CLIENT_SECRET = "Fhjzvxy8GFqC8Gb8hx8ZQ2487r4CgrHG";  //百度面部识别secret
    String BAIDU_FACE_LICENSEID = "tdkq-face-android";  //百度面部识别Licenseid；
    String BAIDU_FACE_FILE_NAME = "idl-license.faceexample-face-android-1";  //百度地图面部识别Licenseid；
    String JPUSH_APP_KEY = "4aa7ae8a2a2d1a5848a4af52";
    String JPUSH_SECRET = "f2cf9b1ec7dc0d86c08ded8c";

    String BAIDU_ASE_URL = "https://aip.baidubce.com/oauth/2.0/token";                              //实名认证获取token的url
    String BAIDU_IDCARD_URL = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";                    //身份证认证
    String BAUDU_PERSON_VERIFY = "https://aip.baidubce.com/rest/2.0/face/v3/person/verify?access_token=" + BaiDuAccessUtils.getInstance().getAccessToken();         //人脸身份证对比

    String BASE_URL = "http://rent.tdpower.net/api/";           //租车的url

    //用户协议
    String PRIVACY_AGREEMENT = "http://rent.tdpower.net/api/article/member_agreement";
    //隐私协议
    String USER_PRIVACY = "http://rent.tdpower.net/api/article/member_privacy";
    //租赁协议
    String LEASE_AGREEMENT = "http://rent.tdpower.net/api/article/rental_agreement";
    //关于我们
    String RENTAL_ABOUT = "http://rent.tdpower.net/api/article/rental_about";
    //地址
    String URL_PICKER = "member/get_area";
    //图片转换为url
    String URL_IMAGE_PX_URL = "member/oss_upload";
    //发送手机验证码
    String URL_SEND_CODE = "member/send_code";
    //用户注册
    String URL_MEMBER_REGISTER = "member/member_register";
    //用户密码登录
    String URL_MEMBER_LOGIN = "member/member_login";
    //用户退出
    String URL_MEMBER_LOGOUT = "member/member_logout";
    //手机验证码登陆
    String URL_PHONE_LOGIN = "member/phone_login";
    //小哥发布新建
    String URL_CREATE_ARTICLE = "member/create_article";
    //小哥发布列表
    String URL_ARTICLE_LIST = "member/article_list";
    //小哥发布删除
    String URL_DEL_ARTICLE = "member/del_article";
    //轮轮播图
    String URL_ADVERTISING_LIST = "member/advertising_list";
    //会员权益
    String URL_MEMBER_EQUITIES = "member/member_equities";
    //更换头像
    String URL_MEMBER_UPDATE_IMG = "member/member_update_img";
    //修改密码
    String URL_CHANGE_PASS = "member/change_pass";
    //忘记密码
    String URL_FORGET_PASS = "member/forget_pass";
    //会员信息
    String URL_MEMBER_INFO = "member/member_info";
    //实名认证
    String URL_APPROVE = "member/approve";
    //套餐列表
    String URL_RENT_LIST = "member/rent_list";
    //套餐详情
    String URL_RENT_DETAIL = "member/rent_detail";
    //提车点
    String URL_WAREHOUSE_LIST = "member/warehouse_list";
    //维修点
    String URL_STATION_LIST = "member/station_list";
    //套餐详情页计算价格
    String URL_COMPUTE_PRICE = "member/compute_price";
    //立即购买
    String URL_CREATE_ORDER = "member/create_order";
    //消息列表
    String URL_MEMBER_NOTICE_LIST = "member/member_notice_list";
    //订单列表
    String URL_MEMBER_ORDER_LIST = "member/order_list";
    //邀请列表
    String URL_MEMBER_INVITE_INFO = "member/invite_info";
    //更换邀请人
    String URL_MEMBER_CHANGE_INVITER = "member/change_inviter";
    //修改绑定手机,验证旧手机
    String URL_MEMBER_CHANGE_OLD_PHONE = "member/change_old_phone";
    //修改绑定手机,验证新手机
    String URL_MEMBER_CHANGE_NEW_PHONE = "member/change_new_phone";
    //我的骑行币
    String URL_MEMBER_COMMISSION = "member/commission";
    //发起救援
    String URL_MEMBER_CREATE_RESCUE = "member/create_rescue";
    //消息标记为已读
    String URL_MEMBER_CHANGE_NOTICE = "member/change_notice";
    //订单详情
    String URL_MEMBER_ORDER_DETAIL = "member/order_detail";
    //换车
    String URL_MEMBER_REPLACE_CAR = "member/replace_car";
    //救援列表
    String URL_MEMBER_RESCUE_LIST = "member/rescue_list";
    //用户救援详情
    String URL_MEMBER_RESCUE_DETAIL = "member/rescue_detail";
    //用户救援评价
    String URL_MEMBER_RESCUE_EVALUATE = "member/rescue_evaluate";
    //订单微信支付
    String URL_MEMBER_PAY_ORDER = "member/pay_order";
    //支付宝支付
    String URL_MEMBER_ALI_PAY = "member/ali_pay";   //ali_freeze
    //取消订单
    String URL_MEMBER_CANCEL_ORDER = "member/cancel_order";
    //删除订单
    String URL_MEMBER_DELETE_ORDER = "member/delete_order";
    //用户提车
    String URL_MEMBER_RENT_CAR = "member/rent_car";
    //提车还车仓库列表
    String URL_MEMBER_ENTREPOT_LIST = "member/entrepot_list";
    //微信退款
    String URL_MEMBER_WX_ORDER_REFUND = "member/order_refund";
    //支付宝退款
    String URL_MEMBER_ZFB_ORDER_REFUND = "member/ali_refund";
    //去续费
    String URL_MEMBER_RENEW_CREATE_ORDER = "member/renew_create_order";
    //续费套餐详情页计算价格
    String URL_MEMBER_RENEW_COMPUTE_PRICE = "member/renew_compute_price";
    //续费订单支付（微信）
    String URL_MEMBER_WX_RENEW_PAY_ORDER = "member/renew_pay_order";
    //续费订单支付（支付宝）
    String URL_MEMBER_ZFB_RENEW_PAY_ORDER = "member/ali_renew_pay";
    //提现
    String URL_MEMBER_MEMBER_WITHDRAW = "member/member_withdraw";
    //注销
    String URL_MEMBER_WRITE_OFF = "member/write_off";
    //安卓版本号
    String URL_MEMBER_ANDROID_VERSION = "member/android_version";
    //消息红点
    String URL_MESSAGE_NUM= "member/message_num";


    //站长端
    //用户密码登录
    String URL_SELL_LOGIN = "sell/pass_login";
    //手机验证码登陆
    String URL_SELL_PHONE_LOGIN = "sell/phone_login";
    //订单列表
    String URL_SELL_ORDER_LIST = "sell/order_list";
    //订单详情
    String URL_SELL_ORDER_DETAIL = "sell/order_detail";
    //救援列表
    String URL_SELL_RESCUE_LIST = "sell/rescue_list";
    //用户退出
    String URL_SELL_LOGOUT = "sell/logout";
    //发送手机验证码
    String URL_SELL_SEND_CODE = "sell/send_code";
    //更换头像
    String URL_SELL_UPDATE_IMG = "sell/sell_update_img";
    //修改密码
    String URL_SELL_CHANGE_PASS = "sell/change_pass";
    //忘记密码
    String URL_SELL_FORGET_PASS = "sell/forget_pass";
    //打卡
    String URL_SELL_SELL_CLOCK = "sell/sell_clock";
    //更新位置
    String URL_SELL_UPDATE_ADDRESS = "sell/update_address";
    //获取打卡状态
    String URL_SELL_CLOCK_STATUS = "sell/clock_status";
    //救援接单
    String URL_SELL_RECEIVE_ORDER = "sell/receive_order";
    //完成救援
    String URL_SELL_END_ORDER = "sell/end_order";
    //救援详情
    String URL_SELL_RESCUE_DETAIL = "sell/rescue_detail";
    //图片转换为url
    String URL_SELL_IMAGE_PX_URL = "sell/oss_upload";


    //维修端
    //用户密码登录
    String URL_AGENT_LOGIN = "agent/pass_login";
    //用户退出
    String URL_AGENT_LOGOUT = "agent/logout";
    //修改密码
    String URL_AGENT_CHANGE_PASS = "agent/change_pass";
    //发送手机验证码
    String URL_AGENT_SEND_CODE = "agent/send_code";
    //手机验证码登陆
    String URL_AGENT_PHONE_LOGIN = "agent/phone_login";
    //忘记密码
    String URL_AGENT_FORGET_PASS = "agent/forget_pass";
    //更换头像
    String URL_AGENT_UPDATE_IMG = "agent/update_img";
    //配件列表
    String URL_AGENT_MOUNTINGS_LIST = "agent/mountings_list";
    //修改配件数量
    String URL_AGENT_UP_MOUNTINGS_NUM = "agent/up_mountings_num";
    //添加订单
    String URL_AGENT_UP_ADD_ORDER = "agent/add_order";
    //订单列表
    String URL_AGENT_ORDER_LIST = "agent/order_list";
    //删除列表
    String URL_AGENT_DELETE_ORDER = "agent/delete_order";
    //检车
    String URL_AGENT_CHECK_CAR = "agent/check_car";
    //图片转换为url
    String URL_OSS_UPLOAD = "agent/oss_upload";

    //员工端
    //用户密码登录
    String URL_STAFF_LOGIN = "staff/pass_login";
    //用户退出
    String URL_STAFF_LOGOUT = "staff/logout";
    //修改密码
    String URL_STAFF_CHANGE_PASS = "staff/change_pass";
    //发送手机验证码
    String URL_STAFF_SEND_CODE = "staff/send_code";
    //手机验证码登陆
    String URL_STAFF_PHONE_LOGIN = "staff/phone_login";
    //忘记密码
    String URL_STAFF_FORGET_PASS = "staff/forget_pass";
    //更换头像
    String URL_STAFF_UPDATE_IMG = "staff/update_img";
    //我发起的
    String URL_STAFF_SPONSOR_APPROVAL = "staff/sponsor_approval";
    //我的审批
    String URL_STAFF_APPROVAL_LIST = "staff/approval_list";
    //上传文件
    String URL_STAFF_UPDATE_FILE = "staff/update_file";
    //新建审批
    String URL_STAFF_ADD_APPROVAL = "staff/add_approval";
    //审批人列表
    String URL_STAFF_APPROVER_LIST = "staff/approver_list";
    //通过或者拒绝
    String URL_STAFF_APPROVAL_OPERATE = "staff/approval_operate";
    //删除审批
    String URL_STAFF_DEL_APPROVAL = "staff/del_approval";
    //审批详情
    String URL_STAFF_APPROVAL_DETAIL = "staff/approval_detail";

}
