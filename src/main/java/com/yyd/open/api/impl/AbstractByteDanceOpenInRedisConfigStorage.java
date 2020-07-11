package com.yyd.open.api.impl;

import com.yyd.open.api.IByteDanceOpenConfigStorage;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yangyidian
 * @date 2020/06/23
 **/
@Data
public abstract class AbstractByteDanceOpenInRedisConfigStorage implements IByteDanceOpenConfigStorage {

    protected final static String COMPONENT_VERIFY_TICKET_KEY = "bytedance_component_verify_ticket:";
    protected final static String COMPONENT_ACCESS_TOKEN_KEY = "bytedance_component_access_token:";

    protected final static String AUTHORIZER_REFRESH_TOKEN_KEY = "bytedance_authorizer_refresh_token:";
    protected final static String AUTHORIZER_ACCESS_TOKEN_KEY = "bytedance_authorizer_access_token:";

    protected final static String LOCK_KEY = "bytedance_lock:";


    /**
     * redis 存储的 key 的前缀，可为空
     */
    protected String keyPrefix;
    protected String componentVerifyTicketKey;
    protected String componentAccessTokenKey;
    protected String authorizerRefreshTokenKey;
    protected String authorizerAccessTokenKey;
    protected String lockKey;


    private String componentAppId;
    private String componentAppSecret;
    private String componentToken;
    private String componentAesKey;
    private String componentVerifyTicket;
    private String componentAccessToken;
    private long componentExpiresTime;

    private Map<String, Token> authorizerRefreshTokens = new ConcurrentHashMap<>();
    private Map<String, Token> authorizerAccessTokens = new ConcurrentHashMap<>();
    private Map<String, Lock> locks = new ConcurrentHashMap<>();

    @Data
    private static class Token {
        private String token;
        private Long expiresTime;
    }

    @Override
    public void setComponentAppId(String componentAppId) {
        this.componentAppId = componentAppId;
        String prefix = StringUtils.isBlank(keyPrefix) ? "" :
            (StringUtils.endsWith(keyPrefix, ":") ? keyPrefix : (keyPrefix + ":"));
        componentVerifyTicketKey = prefix + COMPONENT_VERIFY_TICKET_KEY.concat(componentAppId);
        componentAccessTokenKey = prefix + COMPONENT_ACCESS_TOKEN_KEY.concat(componentAppId);
        authorizerRefreshTokenKey = prefix + AUTHORIZER_REFRESH_TOKEN_KEY.concat(componentAppId);
        authorizerAccessTokenKey = prefix + AUTHORIZER_ACCESS_TOKEN_KEY.concat(componentAppId);
        lockKey = prefix + LOCK_KEY.concat(componentAppId);
    }

    protected String getKey(String prefix, String appId) {
        return prefix.endsWith(":") ? prefix.concat(appId) : prefix.concat(":").concat(appId);
    }
}
