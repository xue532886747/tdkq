package com.example.towerdriver.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.towerdriver.Constant;
import com.example.towerdriver.event.PayEvent;
import com.example.towerdriver.utils.tools.LogUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

import androidx.annotation.Nullable;

/**
 * @author 53288
 * @description
 * @date 2021/6/22
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constant.WxAppId);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == 0) {
                new PayEvent(1, "支付成功!",0).post();
                LogUtils.d("baseResp.errCode = "+baseResp.errCode);
                finish();
            } else if (baseResp.errCode == -2) {
                new PayEvent(1, "支付取消!",-2).post();
                LogUtils.d("baseResp.errCode = "+baseResp.errCode);
                finish();
            } else if (baseResp.errCode == -1) {
                new PayEvent(1, "支付失败!",-1).post();
                LogUtils.d("baseResp.errCode = "+baseResp.errCode);
                finish();
            } else {
                new PayEvent(1, "支付错误!",1).post();
                LogUtils.d("baseResp.errCode = "+baseResp.errCode);
                finish();
            }
        }
    }
}
