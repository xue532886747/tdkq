package com.example.towerdriver.base_authentication.model.bean;

/**
 * @author 53288
 * @description 百度bean
 * @date 2021/5/25
 */
public class BaiduTokenBean {

    /**
     * refresh_token : 25.b95c0cf285fe2cd8e37c854a73514592.315360000.1937292552.282335-24242741
     * expires_in : 2592000
     * session_key : 9mzdWTw/d0ehVTt0BMVTZYcxS0Qua1FTccxw7XgVhjRuGxQwEg7x6WouST/meMiX4K3fzbfqbAdWNqEVmuHeFp6oeV/FIw==
     * access_token : 24.bf94a514fbee81e02820881c917f1f9e.2592000.1624524552.282335-24242741
     * scope : vis-ocr_ocr brain_ocr_idcard public brain_all_scope vis-faceverify_faceverify_h5-face-liveness vis-faceverify_FACE_V3 vis-faceverify_idl_face_merge vis-faceverify_FACE_EFFECT vis-faceverify_face_beauty vis-faceverify_face_feature_sdk wise_adapt lebo_resource_base lightservice_public hetu_basic lightcms_map_poi kaidian_kaidian ApsMisTest_Test权限 vis-classify_flower lpq_开放 cop_helloScope ApsMis_fangdi_permission smartapp_snsapi_base smartapp_mapp_dev_manage iop_autocar oauth_tp_app smartapp_smart_game_openapi oauth_sessionkey smartapp_swanid_verify smartapp_opensource_openapi smartapp_opensource_recapi fake_face_detect_开放Scope vis-ocr_虚拟人物助理 idl-video_虚拟人物助理 smartapp_component smartapp_search_plugin avatar_video_test
     * session_secret : d7ec9e077897cb5f7949405b1ebe34c9
     */

    private String refresh_token;
    private Long expires_in;
    private String session_key;
    private String access_token;
    private String scope;
    private String session_secret;

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getSession_secret() {
        return session_secret;
    }

    public void setSession_secret(String session_secret) {
        this.session_secret = session_secret;
    }

    @Override
    public String toString() {
        return "BaiduTokenBean{" +
                "refresh_token='" + refresh_token + '\'' +
                ", expires_in=" + expires_in +
                ", session_key='" + session_key + '\'' +
                ", access_token='" + access_token + '\'' +
                ", scope='" + scope + '\'' +
                ", session_secret='" + session_secret + '\'' +
                '}';
    }
}
