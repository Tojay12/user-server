package com.wang.authentication;

/**
 * @author: jyw
 * @date: 2022/6/7 11:06
 * @description:
 */
public final class CacheConstants {

    /**
     * 令牌自定义标识
     */
    public static final String HEADER = "Authorization";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 权限缓存前缀
     */
    public final static String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 用户ID字段
     */
    public static final String DETAILS_USER_ID = "userId";

    /**
     * 用户名字段
     */
    public static final String DETAILS_USERNAME = "username";

    /**
     * 用户数据权限类型字段
     */
    public static final String USER_DATA_SCOPE = "dataScopeType";

    /**
     * 用户部门前缀字段
     */
    public static final String USER_OFFICE_PREFIX = "officePrefix";

    /**
     * 登录配置
     */
    public static final String LOGIN_CONFIG = "login_config";

    /**
     * 密码错误登录失败缓存前缀
     */
    public static final String LOGIN_ERROR = "login_error:";

    /**
     * 动态验证码前缀
     */
    public static final String DYNAMIC_CAPTCHA_PREFIX = "dynamic_captcha:";

    /**
     * 越权访问缓存队列key
     */
    public static final String INVALID_ACCESS_SEND_EMAIL = "report_invalid_access_send_email";

    /**
     * 越权访问通知失败重试key
     */
    public static final String INVALID_ACCESS_ERROR_RETRY_COUNT = "report_invalid_access_error_retry_count:";

    public static final Long EXPIRATION_TIMEOUT = 60L;
    public static final String UTF8 = "UTF-8";
}
