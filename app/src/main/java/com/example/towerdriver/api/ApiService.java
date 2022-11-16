package com.example.towerdriver.api;

import com.example.towerdriver.Constant;
import com.example.towerdriver.base_authentication.model.bean.ImageToUrlBean;
import com.example.towerdriver.base_change_phone.model.NewPhoneBean;
import com.example.towerdriver.base_driver.model.bean.ReleaseBean;
import com.example.towerdriver.base_driver.model.bean.ReleaseDeleteBean;
import com.example.towerdriver.base_driver.model.bean.ReleaseListBean;
import com.example.towerdriver.base_drivier_coin.model.DriverCoinBean;
import com.example.towerdriver.base_invite.model.InviteBean;
import com.example.towerdriver.base_login.bean.UserBean;
import com.example.towerdriver.base_main.bean.AdvertiBean;
import com.example.towerdriver.base_main.bean.MessageNumberBean;
import com.example.towerdriver.base_main.bean.PickUpBean;
import com.example.towerdriver.base_main.bean.RepairBean;
import com.example.towerdriver.base_member_level.model.bean.LevelBean;
import com.example.towerdriver.base_notice.model.NoticeBean;
import com.example.towerdriver.base_order_list.model.ChangeCarBean;
import com.example.towerdriver.base_order_list.model.OrderStatusBean;
import com.example.towerdriver.base_order_list.model.OrderDetailBean;
import com.example.towerdriver.base_order_list.model.OrderListBean;
import com.example.towerdriver.base_pay.model.PayBean;
import com.example.towerdriver.base_pay.model.ZfbBean;
import com.example.towerdriver.base_person.bean.MemberImageBean;
import com.example.towerdriver.base_register.model.bean.RegisterBean;
import com.example.towerdriver.base_rescue.model.RescueListBean;
import com.example.towerdriver.base_sendcode.model.bean.VerificationBean;
import com.example.towerdriver.base_setmenu.model.CreateOrderBean;
import com.example.towerdriver.base_order_list.model.EntrepotBean;
import com.example.towerdriver.base_setmenu.model.GoodsDetailBean;
import com.example.towerdriver.base_setmenu.model.PriceBean;
import com.example.towerdriver.base_setmenu.model.SelectBean;
import com.example.towerdriver.base_setting.bean.UserLogoutBean;
import com.example.towerdriver.bean.AreaBean;
import com.example.towerdriver.bean.VersionBean;
import com.example.towerdriver.member_model.MemberInfoBean;
import com.example.towerdriver.staff.base_approval.model.ApprovalDetailBean;
import com.example.towerdriver.staff.base_approval.model.ApprovalStatusBean;
import com.example.towerdriver.staff.base_approval.model.AudioListBean;
import com.example.towerdriver.staff.base_approval.model.FileToUrlBean;
import com.example.towerdriver.staff.base_approval.model.SponsorListBean;
import com.example.towerdriver.repair.base_order.model.RepairListBean;
import com.example.towerdriver.repair.base_warehouse.model.MountBean;
import com.example.towerdriver.repair.base_warehouse.model.MountNumberBean;
import com.example.towerdriver.station.base_daka.model.WorkStatusBean;
import com.example.towerdriver.station.station_person.model.ImageBean;
import com.example.towerdriver.station.station_rescue.model.RescueBean;
import com.example.towerdriver.station.station_rescue.model.RescueDetailBean;
import com.example.towerdriver.station.station_rescue.model.StationRescueBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

import static com.example.towerdriver.Constant.URL_ADVERTISING_LIST;
import static com.example.towerdriver.Constant.URL_ARTICLE_LIST;
import static com.example.towerdriver.Constant.URL_CREATE_ARTICLE;
import static com.example.towerdriver.Constant.URL_DEL_ARTICLE;
import static com.example.towerdriver.Constant.URL_IMAGE_PX_URL;
import static com.example.towerdriver.Constant.URL_MEMBER_EQUITIES;
import static com.example.towerdriver.Constant.URL_MEMBER_LOGIN;
import static com.example.towerdriver.Constant.URL_MEMBER_LOGOUT;
import static com.example.towerdriver.Constant.URL_MEMBER_REGISTER;
import static com.example.towerdriver.Constant.URL_PHONE_LOGIN;
import static com.example.towerdriver.Constant.URL_SEND_CODE;

/**
 * @author 53288
 * @description 接口管理
 * @date 2021/5/19
 */
public interface ApiService {

    @POST(Constant.URL_PICKER)
    Observable<ResponseBean<List<AreaBean>>> getArea();

    //获得百度人脸识别的token
    @FormUrlEncoded
    @POST
    Observable<Response<ResponseBody>> getToken(@Url String url, @FieldMap Map<String, String> requestMap);

    //图片转url
    @POST(URL_IMAGE_PX_URL)
    Observable<ResponseBean<ImageToUrlBean>> changeNewImageHead(@Body RequestBody body);

    //图片转url
    @POST(URL_IMAGE_PX_URL)
    @Multipart
    Observable<ResponseBean<ImageToUrlBean>> changeNewImageHeads(@Part RequestBody file, @FieldMap Map<String, String> map);


    //百度人脸识别验证
    @FormUrlEncoded
    @POST
    Observable<Response<ResponseBody>> getPersonVerify(@Url String url, @FieldMap Map<String, String> requestMap);

    //发送验证码
    @FormUrlEncoded
    @POST(URL_SEND_CODE)
    Observable<ResponseBean<VerificationBean>> getVerificationCode(@FieldMap Map<String, String> requestMap);

    //用户注册
    @FormUrlEncoded
    @POST(URL_MEMBER_REGISTER)
    Observable<ResponseBean<RegisterBean>> getUserRegister(@FieldMap Map<String, String> requestMap);

    //用户登录
    @FormUrlEncoded
    @POST(URL_MEMBER_LOGIN)
    Observable<ResponseBean<UserBean>> getUserLogin(@FieldMap Map<String, String> requestMap);

    //用户退出
    @FormUrlEncoded
    @POST(URL_MEMBER_LOGOUT)
    Observable<ResponseBean<UserLogoutBean>> getUserLogout(@FieldMap Map<String, String> requestMap);

    //用户验证码登录
    @FormUrlEncoded
    @POST(URL_PHONE_LOGIN)
    Observable<ResponseBean<UserBean>> getUserPhoneLogin(@FieldMap Map<String, String> requestMap);

    //小哥发布
    @FormUrlEncoded
    @POST(URL_CREATE_ARTICLE)
    Observable<ResponseBean<ReleaseBean>> getDriverRelease(@FieldMap Map<String, String> requestMap);

    //小哥列表
    @FormUrlEncoded
    @POST(URL_ARTICLE_LIST)
    Observable<ResponseBean<ReleaseListBean>> getDriverList(@FieldMap Map<String, String> requestMap);

    //小哥列表删除
    @FormUrlEncoded
    @POST(URL_DEL_ARTICLE)
    Observable<ResponseBean<ReleaseDeleteBean>> getDriverDeleteItem(@FieldMap Map<String, String> requestMap);

    //首页轮播图
    @FormUrlEncoded
    @POST(URL_ADVERTISING_LIST)
    Observable<ResponseBean<AdvertiBean>> getLunBo(@FieldMap Map<String, String> requestMap);

    //会员权益
    @FormUrlEncoded
    @POST(URL_MEMBER_EQUITIES)
    Observable<ResponseBean<LevelBean>> getMemberLevel(@FieldMap Map<String, String> requestMap);

    //更换头像
    @POST(Constant.URL_MEMBER_UPDATE_IMG)
    Observable<ResponseBean<MemberImageBean>> ChangeMemberImg(@Body RequestBody body);

    //修改密码
    @FormUrlEncoded
    @POST(Constant.URL_CHANGE_PASS)
    Observable<ResponseBean<UsuallyBean>> ChangePass(@FieldMap Map<String, String> requestMap);

    //忘记密码
    @FormUrlEncoded
    @POST(Constant.URL_FORGET_PASS)
    Observable<ResponseBean<UsuallyBean>> ForgetPassClient(@FieldMap Map<String, String> requestMap);

    //获得用户信息
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_INFO)
    Observable<ResponseBean<MemberInfoBean>> getMemberInfo(@FieldMap Map<String, String> requestMap);

    //实名认证
    @FormUrlEncoded
    @POST(Constant.URL_APPROVE)
    Observable<ResponseBean<UsuallyBean>> getApprove(@FieldMap Map<String, String> requestMap);

    //套餐列表
    @FormUrlEncoded
    @POST(Constant.URL_RENT_LIST)
    Observable<ResponseBean<SelectBean>> getMenuList(@FieldMap Map<String, String> requestMap);

    //套餐详情
    @FormUrlEncoded
    @POST(Constant.URL_RENT_DETAIL)
    Observable<ResponseBean<GoodsDetailBean>> getGoodsDetail(@FieldMap Map<String, String> requestMap);

    //提车点
    @FormUrlEncoded
    @POST(Constant.URL_WAREHOUSE_LIST)
    Observable<ResponseBean<PickUpBean>> getPickUpPoint(@FieldMap Map<String, String> requestMap);

    //维修点
    @FormUrlEncoded
    @POST(Constant.URL_STATION_LIST)
    Observable<ResponseBean<RepairBean>> getRepairPoint(@FieldMap Map<String, String> requestMap);

    //计算价格
    @FormUrlEncoded
    @POST(Constant.URL_COMPUTE_PRICE)
    Observable<ResponseBean<PriceBean>> getPrice(@FieldMap Map<String, String> requestMap);

    //立即购买
    @FormUrlEncoded
    @POST(Constant.URL_CREATE_ORDER)
    Observable<ResponseBean<CreateOrderBean>> getCreateOrder(@FieldMap Map<String, String> requestMap);

    //消息列表
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_NOTICE_LIST)
    Observable<ResponseBean<NoticeBean>> getNoticeList(@FieldMap Map<String, String> requestMap);

    //订单列表
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_ORDER_LIST)
    Observable<ResponseBean<OrderListBean>> getOrderList(@FieldMap Map<String, String> requestMap);

    //邀请列表
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_INVITE_INFO)
    Observable<ResponseBean<InviteBean>> getInviteList(@FieldMap Map<String, String> requestMap);

    //更换邀请人
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_CHANGE_INVITER)
    Observable<ResponseBean<UsuallyBean>> getChangeInvite(@FieldMap Map<String, String> requestMap);

    //修改绑定手机,验证旧手机
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_CHANGE_OLD_PHONE)
    Observable<ResponseBean<UsuallyBean>> getCheckOldPhone(@FieldMap Map<String, String> requestMap);

    //修改绑定手机,验证新手机
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_CHANGE_NEW_PHONE)
    Observable<ResponseBean<NewPhoneBean>> getCheckNewPhone(@FieldMap Map<String, String> requestMap);

    //我的骑行币
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_COMMISSION)
    Observable<ResponseBean<DriverCoinBean>> getCommissionList(@FieldMap Map<String, String> requestMap);

    //发起救援
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_CREATE_RESCUE)
    Observable<ResponseBean<UsuallyBean>> getCreateRescue(@FieldMap Map<String, String> requestMap);

    //消息标记为已读
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_CHANGE_NOTICE)
    Observable<ResponseBean<UsuallyBean>> getChangeNotice(@FieldMap Map<String, String> requestMap);

    //订单详情
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_ORDER_DETAIL)
    Observable<ResponseBean<OrderDetailBean>> getOrderDetail(@FieldMap Map<String, String> requestMap);

    //换车
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_REPLACE_CAR)
    Observable<ResponseBean<ChangeCarBean>> getReplaceCar(@FieldMap Map<String, String> requestMap);

    //用户救援评价列表
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_RESCUE_LIST)
    Observable<ResponseBean<RescueListBean>> getRescueList(@FieldMap Map<String, String> requestMap);

    //用户救援评价
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_RESCUE_EVALUATE)
    Observable<ResponseBean<UsuallyBean>> getRescueComment(@FieldMap Map<String, String> requestMap);

    //调起微信支付接口
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_PAY_ORDER)
    Observable<ResponseBean<PayBean>> getPay(@FieldMap Map<String, String> requestMap);

    //调起支付宝支付接口
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_ALI_PAY)
    Observable<ResponseBean<ZfbBean>> getZfbPay(@FieldMap Map<String, String> requestMap);


    //取消订单
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_CANCEL_ORDER)
    Observable<ResponseBean<OrderStatusBean>> getCancelOrder(@FieldMap Map<String, String> requestMap);

    //删除订单
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_DELETE_ORDER)
    Observable<ResponseBean<UsuallyBean>> getDeleteOrder(@FieldMap Map<String, String> requestMap);

    //用户提车
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_RENT_CAR)
    Observable<ResponseBean<OrderStatusBean>> getRentCar(@FieldMap Map<String, String> requestMap);

    //提车还车仓库列表
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_ENTREPOT_LIST)
    Observable<ResponseBean<EntrepotBean>> getEntrepotList(@FieldMap Map<String, String> requestMap);

    //退款微信订单
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_WX_ORDER_REFUND)
    Observable<ResponseBean<OrderStatusBean>> getWxRefundOrder(@FieldMap Map<String, String> requestMap);

    //退款支付宝订单
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_ZFB_ORDER_REFUND)
    Observable<ResponseBean<OrderStatusBean>> getZfbRefundOrder(@FieldMap Map<String, String> requestMap);

    //续费计算价格
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_RENEW_COMPUTE_PRICE)
    Observable<ResponseBean<PriceBean>> getComputePrice(@FieldMap Map<String, String> requestMap);

    //去续费
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_RENEW_CREATE_ORDER)
    Observable<ResponseBean<CreateOrderBean>> getReCreateOrder(@FieldMap Map<String, String> requestMap);

    //调起支付接口(续租,微信)
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_WX_RENEW_PAY_ORDER)
    Observable<ResponseBean<PayBean>> getWxNewPay(@FieldMap Map<String, String> requestMap);

    //调起支付接口(续租 支付宝)
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_ZFB_RENEW_PAY_ORDER)
    Observable<ResponseBean<ZfbBean>> getZfbNewPay(@FieldMap Map<String, String> requestMap);

    //获取救援详情
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_RESCUE_DETAIL)
    Observable<ResponseBean<com.example.towerdriver.base_rescue.model.RescueBean>> RescueDetail(@FieldMap Map<String, String> requestMap);


    //用户提现
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_MEMBER_WITHDRAW)
    Observable<ResponseBean<UsuallyBean>> getTixian(@FieldMap Map<String, String> requestMap);


    //用户注销
    @FormUrlEncoded
    @POST(Constant.URL_MEMBER_WRITE_OFF)
    Observable<ResponseBean<UsuallyBean>> getWriteOff(@FieldMap Map<String, String> requestMap);

    //去续费
    @FormUrlEncoded
    @POST(Constant.URL_MESSAGE_NUM)
    Observable<ResponseBean<MessageNumberBean>> getNoticeNumber(@FieldMap Map<String, String> requestMap);


    //站长登录
    @FormUrlEncoded
    @POST(Constant.URL_SELL_LOGIN)
    Observable<ResponseBean<UserBean>> getStationUserLogin(@FieldMap Map<String, String> requestMap);

    //站长验证码登录
    @FormUrlEncoded
    @POST(Constant.URL_SELL_PHONE_LOGIN)
    Observable<ResponseBean<UserBean>> getStationPhoneLogin(@FieldMap Map<String, String> requestMap);

    //站长订单列表
    @FormUrlEncoded
    @POST(Constant.URL_SELL_ORDER_LIST)
    Observable<ResponseBean<OrderListBean>> getStationOrderList(@FieldMap Map<String, String> requestMap);

    //站长订单详情
    @FormUrlEncoded
    @POST(Constant.URL_SELL_ORDER_DETAIL)
    Observable<ResponseBean<OrderDetailBean>> getStationOrderDetail(@FieldMap Map<String, String> requestMap);


    //站长救援评价列表
    @FormUrlEncoded
    @POST(Constant.URL_SELL_RESCUE_LIST)
    Observable<ResponseBean<StationRescueBean>> getStationRescueList(@FieldMap Map<String, String> requestMap);

    //站长用户退出
    @FormUrlEncoded
    @POST(Constant.URL_SELL_LOGOUT)
    Observable<ResponseBean<UserLogoutBean>> getStationLogout(@FieldMap Map<String, String> requestMap);

    //站长验证码
    @FormUrlEncoded
    @POST(Constant.URL_SELL_SEND_CODE)
    Observable<ResponseBean<VerificationBean>> getStationVerificationCode(@FieldMap Map<String, String> requestMap);

    //站长更换头像
    @POST(Constant.URL_SELL_UPDATE_IMG)
    Observable<ResponseBean<ImageBean>> ChangeStationImg(@Body RequestBody body);

    //站长修改密码
    @FormUrlEncoded
    @POST(Constant.URL_SELL_CHANGE_PASS)
    Observable<ResponseBean<UsuallyBean>> ChangeStationPass(@FieldMap Map<String, String> requestMap);

    //站长修忘记密码
    @FormUrlEncoded
    @POST(Constant.URL_SELL_FORGET_PASS)
    Observable<ResponseBean<UsuallyBean>> ForgetStationPassClient(@FieldMap Map<String, String> requestMap);

    //站长打卡
    @FormUrlEncoded
    @POST(Constant.URL_SELL_SELL_CLOCK)
    Observable<ResponseBean<UsuallyBean>> StationSignIn(@FieldMap Map<String, String> requestMap);

    //站长更新位置
    @FormUrlEncoded
    @POST(Constant.URL_SELL_UPDATE_ADDRESS)
    Observable<ResponseBean<UsuallyBean>> StationUpdateAddress(@FieldMap Map<String, String> requestMap);

    //获取站长打卡状态
    @FormUrlEncoded
    @POST(Constant.URL_SELL_CLOCK_STATUS)
    Observable<ResponseBean<WorkStatusBean>> StationWorkStatus(@FieldMap Map<String, String> requestMap);

    //获取站长救援接单
    @FormUrlEncoded
    @POST(Constant.URL_SELL_RECEIVE_ORDER)
    Observable<ResponseBean<RescueBean>> stationReceiverOrder(@FieldMap Map<String, String> requestMap);

    //获取站长救援详情
    @FormUrlEncoded
    @POST(Constant.URL_SELL_RESCUE_DETAIL)
    Observable<ResponseBean<RescueDetailBean>> stationRescueDetail(@FieldMap Map<String, String> requestMap);

    //站长图片转url
    @POST(Constant.URL_SELL_IMAGE_PX_URL)
    Observable<ResponseBean<ImageToUrlBean>> changeStationImage(@Body RequestBody body);


    //站长完成救援
    @FormUrlEncoded
    @POST(Constant.URL_SELL_END_ORDER)
    Observable<ResponseBean<RescueBean>> stationFinishRescue(@FieldMap Map<String, String> requestMap);


    //维修登录
    @FormUrlEncoded
    @POST(Constant.URL_AGENT_LOGIN)
    Observable<ResponseBean<UserBean>> getAgentUserLogin(@FieldMap Map<String, String> requestMap);

    //维修用户退出
    @FormUrlEncoded
    @POST(Constant.URL_AGENT_LOGOUT)
    Observable<ResponseBean<UserLogoutBean>> getRepairLogout(@FieldMap Map<String, String> requestMap);

    //维修修改密码
    @FormUrlEncoded
    @POST(Constant.URL_AGENT_CHANGE_PASS)
    Observable<ResponseBean<UsuallyBean>> ChangeRepairPass(@FieldMap Map<String, String> requestMap);

    //维修验证码
    @FormUrlEncoded
    @POST(Constant.URL_AGENT_SEND_CODE)
    Observable<ResponseBean<VerificationBean>> getRepairVerificationCode(@FieldMap Map<String, String> requestMap);

    //维修验证码登录
    @FormUrlEncoded
    @POST(Constant.URL_AGENT_PHONE_LOGIN)
    Observable<ResponseBean<UserBean>> getRepairPhoneLogin(@FieldMap Map<String, String> requestMap);

    //维修忘记密码
    @FormUrlEncoded
    @POST(Constant.URL_AGENT_FORGET_PASS)
    Observable<ResponseBean<UsuallyBean>> ForgetRepairPassClient(@FieldMap Map<String, String> requestMap);

    //维修更换头像
    @POST(Constant.URL_AGENT_UPDATE_IMG)
    Observable<ResponseBean<ImageBean>> ChangeRepairImg(@Body RequestBody body);

    //维修忘记密码
    @FormUrlEncoded
    @POST(Constant.URL_AGENT_MOUNTINGS_LIST)
    Observable<ResponseBean<MountBean>> getMountList(@FieldMap Map<String, String> requestMap);

    //设置数量
    @FormUrlEncoded
    @POST(Constant.URL_AGENT_UP_MOUNTINGS_NUM)
    Observable<ResponseBean<MountNumberBean>> getMountNumber(@FieldMap Map<String, String> requestMap);

    //添加订单
    @FormUrlEncoded
    @POST(Constant.URL_AGENT_UP_ADD_ORDER)
    Observable<ResponseBean<UsuallyBean>> getAgentAddOrder(@FieldMap Map<String, String> requestMap);

    //添加订单
    @FormUrlEncoded
    @POST(Constant.URL_AGENT_ORDER_LIST)
    Observable<ResponseBean<RepairListBean>> getAgentOrderList(@FieldMap Map<String, String> requestMap);

    //删除订单
    @FormUrlEncoded
    @POST(Constant.URL_AGENT_DELETE_ORDER)
    Observable<ResponseBean<UsuallyBean>> deleteAgentOrder(@FieldMap Map<String, String> requestMap);

    //检车
    @FormUrlEncoded
    @POST(Constant.URL_AGENT_CHECK_CAR)
    Observable<ResponseBean<OrderStatusBean>> checkCarOrder(@FieldMap Map<String, String> requestMap);

    //图片转url
    @POST(Constant.URL_OSS_UPLOAD)
    Observable<ResponseBean<ImageToUrlBean>> repairUrl(@Body RequestBody body);

    //员工登录
    @FormUrlEncoded
    @POST(Constant.URL_STAFF_LOGIN)
    Observable<ResponseBean<UserBean>> getStaffUserLogin(@FieldMap Map<String, String> requestMap);

    //员工用户退出
    @FormUrlEncoded
    @POST(Constant.URL_STAFF_LOGOUT)
    Observable<ResponseBean<UserLogoutBean>> getStaffLogout(@FieldMap Map<String, String> requestMap);

    //员工修改密码
    @FormUrlEncoded
    @POST(Constant.URL_STAFF_CHANGE_PASS)
    Observable<ResponseBean<UsuallyBean>> ChangeStaffPass(@FieldMap Map<String, String> requestMap);

    //员工验证码
    @FormUrlEncoded
    @POST(Constant.URL_STAFF_SEND_CODE)
    Observable<ResponseBean<VerificationBean>> getStaffVerificationCode(@FieldMap Map<String, String> requestMap);

    //员工验证码登录
    @FormUrlEncoded
    @POST(Constant.URL_STAFF_PHONE_LOGIN)
    Observable<ResponseBean<UserBean>> getStaffPhoneLogin(@FieldMap Map<String, String> requestMap);

    //员工忘记密码
    @FormUrlEncoded
    @POST(Constant.URL_STAFF_FORGET_PASS)
    Observable<ResponseBean<UsuallyBean>> ForgetStaffPassClient(@FieldMap Map<String, String> requestMap);

    //员工更换头像
    @POST(Constant.URL_STAFF_UPDATE_IMG)
    Observable<ResponseBean<ImageBean>> ChangeStaffImg(@Body RequestBody body);

    //我发起的列表
    @FormUrlEncoded
    @POST(Constant.URL_STAFF_SPONSOR_APPROVAL)
    Observable<ResponseBean<SponsorListBean>> getLaunchList(@FieldMap Map<String, String> requestMap);

    //我审批的列表
    @FormUrlEncoded
    @POST(Constant.URL_STAFF_APPROVAL_LIST)
    Observable<ResponseBean<SponsorListBean>> getApprovalList(@FieldMap Map<String, String> requestMap);

    //图片转url
    @POST(Constant.URL_STAFF_UPDATE_FILE)
    Observable<ResponseBean<FileToUrlBean>> FileToUrl(@Body RequestBody body);

    //新建审批
    @FormUrlEncoded
    @POST(Constant.URL_STAFF_ADD_APPROVAL)
    Observable<ResponseBean<UsuallyBean>> createApprovalRelease(@FieldMap Map<String, String> requestMap);

    //审批人列表
    @FormUrlEncoded
    @POST(Constant.URL_STAFF_APPROVER_LIST)
    Observable<ResponseBean<AudioListBean>> getAudioList(@FieldMap Map<String, String> requestMap);

    //通过或者拒绝
    @FormUrlEncoded
    @POST(Constant.URL_STAFF_APPROVAL_OPERATE)
    Observable<ResponseBean<ApprovalStatusBean>> approvalOperate(@FieldMap Map<String, String> requestMap);

    //删除审批
    @FormUrlEncoded
    @POST(Constant.URL_STAFF_DEL_APPROVAL)
    Observable<ResponseBean<UsuallyBean>> getDeleteApproval(@FieldMap Map<String, String> requestMap);

    //订单详情
    @FormUrlEncoded
    @POST(Constant.URL_STAFF_APPROVAL_DETAIL)
    Observable<ResponseBean<ApprovalDetailBean>> getApprovalDetail(@FieldMap Map<String, String> requestMap);

    //获得版本号
    @POST(Constant.URL_MEMBER_ANDROID_VERSION)
    Observable<ResponseBean<VersionBean>> getVersion();
}

