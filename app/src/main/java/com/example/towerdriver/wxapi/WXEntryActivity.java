package com.example.towerdriver.wxapi;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

import com.example.towerdriver.Constant;
import com.example.towerdriver.event.LoginEvent;
import com.example.towerdriver.event.WxEntryEvent;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import androidx.appcompat.app.AppCompatActivity;


/**
 * @author 53288
 * @description WXCallbackActivity冲突了
 * @date 2020/12/4
 */
public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private static final String TAG = "WXEntryActivity1";
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(TAG, "onCreate");
        api = WXAPIFactory.createWXAPI(this, Constant.WxAppId);
        api.handleIntent(getIntent(), this);
        //handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(TAG, "onNewIntent");
        handleIntent(intent);
    }

    protected void handleIntent(Intent intent) {
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        api.handleIntent(data, this);
    }


    @Override
    public void onReq(BaseReq baseReq) {
        Log.d(TAG, "baseReq = " + baseReq.toString());
    }

    @Override
    public void onResp(BaseResp resp) {
//
//        Log.e("WXEntryActivity", "onResp");
//        // TODO Auto-generated method stub
//        if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
//            // 用户同意
////            Log.e("WXEntryActivity", "onResp" + resp.errCode);
////            Log.e("WXEntryActivity", "onResp" + resp.errStr);
////            Log.e("WXEntryActivity", "onResp" + resp.openId);
//            String code = ((SendAuth.Resp) resp).code;
//            Log.e("WXEntryActivitycode", "code = " + code);
//            finish();
//            EventBus.getDefault().post(new PostCodeEvent(code));
//        } else {
//            finish();
//        }
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK: //发送成功
                switch (resp.getType()) {
                    case ConstantsAPI.COMMAND_SENDAUTH:
                        //登录回调,处理登录成功的逻辑
                        String code = ((SendAuth.Resp) resp).code;
                        String openId = ((SendAuth.Resp) resp).openId;
                        Log.d(TAG, "COMMAND_SENDAUTH code=  " + code + ",openId = " + openId);
                        new WxEntryEvent(code, 1).post();
                        finish();
                        break;
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                        //分享回调,处理分享成功后的逻辑
                        Toast.makeText(this, "分享成功了！", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    default:
                        break;
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL: //发送取消
                switch (resp.getType()) {
                    case ConstantsAPI.COMMAND_SENDAUTH:
                        Toast.makeText(WXEntryActivity.this, "登录取消了", Toast.LENGTH_SHORT).show();
                        break;
                    case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
                        Toast.makeText(WXEntryActivity.this, "分享取消了", Toast.LENGTH_SHORT).show();
                        break;
                }
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED: //发送被拒绝
                Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
                break;
            default://发送返回
                break;
        }
    }
}
