package com.github.yydzxz.open.api;

import com.github.yydzxz.open.api.v1.response.auth.GetAuthorizerAccessTokenResponse;
import com.github.yydzxz.open.api.v2.request.auth.GetPreAuthCodeRequest;
import com.google.common.collect.Multimap;

/**
 * 第三方平台业务
 * 具体包括授权，模版管理，上传图片材料，代授权小程序业务
 * @author yangyidian
 */
public interface IByteDanceOpenComponentService {

    /**
     * 获取第三方平台 component_access_token
     */
    String API_COMPONENT_TOKEN_URL = "https://open.microapp.bytedance.com/openapi/v1/auth/tp/token";

    /**
     * 获取预授权码 pre_auth_code
     */
    String API_CREATE_PRE_AUTH_CODE_URL = "https://open.microapp.bytedance.com/openapi/v1/create/tp/pre_auth_code";

    String V2_API_CREATE_PRE_AUTH_CODE_URL = "https://open.microapp.bytedance.com/openapi/v2/auth/pre_auth_code";
    /**
     * 使用授权码换取小程序的接口调用凭据
     */
    String API_GET_OAUTH_TOKEN_URL = "https://open.microapp.bytedance.com/openapi/v1/oauth/token";

    /**
     * 引导小程序管理员对第三方平台进行授权
     */
    String COMPONENT_LOGIN_PAGE_URL = "https://open.microapp.bytedance.com/mappconsole/tp/authorization?component_appid=%s&pre_auth_code=%s&redirect_uri=%s";


    IByteDanceOpenConfigStorage getByteDanceOpenConfigStorage();

    IByteDanceOpenService getByteDanceOpenService();

    IByteDanceOpenConfigStorage getOpenConfigStorage();

    /**
     * 获取素材管理service
     * @return
     */
    IByteDanceOpenMaterialService getByteDanceOpenMaterialService();


    /**
     * 获取模版管理service
     * @return
     */
    IByteDanceOpenTemplateService getByteDanceOpenTemplateService();

    /**
     * 获取指定appid的代授权小程序service
     * <br>用于代授权小程序业务</br>
     * @param appid
     * @return
     */
    IByteDanceOpenMiniProgramService getOpenMiniProgramServiceByAppid(String appid);

    /**
     * 验签
     * @param msgSignature
     * @param tpToken
     * @param timestamp
     * @param nonce
     * @param encrypt
     * @return
     */
    boolean checkSignature(String msgSignature, String tpToken, String timestamp, String nonce, String encrypt);


    /**
     * 获取用户授权页URL（来路URL和成功跳转URL 的域名都需要为三方平台设置的 登录授权的发起页域名）.
     */
    String getPreAuthUrl(String redirectURI);

    /**
     * v2 获取用户授权页URL
     * @param redirectURI
     * @param request com.github.yydzxz.open.api.v2.request.auth.GetPreAuthCodeRequest
     * @return
     */
    default String getPreAuthUrl(String redirectURI, GetPreAuthCodeRequest request){
        throw new RuntimeException("该接口尚未实现");
    }

    /**
     * 使用授权码换取小程序的接口调用凭据.
     * @param authorizationCode
     */
    GetAuthorizerAccessTokenResponse getAuthorizerAccessTokenByAuthorizationCode(String authorizationCode);


    /**
     * 获取小程序的接口调用凭据（令牌）
     * @param appId
     * @param forceRefresh
     * @return
     */
    String getAuthorizerAccessToken(String appId, boolean forceRefresh);

    /**
     * 获取第三方平台 component_access_token
     * 第三方平台 component_access_token 是第三方平台的接口调用凭据，也叫做令牌
     * @param forceRefresh
     * @return
     */
    String getComponentAccessToken(boolean forceRefresh);

    String get(String url);

    <T> T get(String url, Class<T> t);

    <T> T post(String url, Object request, Class<T> t);

    <T> T postWithHeaders(String url, Multimap<String,String> headers, Object request, Class<T> t);
}
