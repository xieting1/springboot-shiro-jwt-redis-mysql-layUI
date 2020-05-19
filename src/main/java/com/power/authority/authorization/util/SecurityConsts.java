package com.power.authority.authorization.util;

/**
 * @program: authorization
 * @description: 请求封装参数
 * @author: xie ting
 * @create: 2020-05-11 20:41
 */
public class SecurityConsts {

    //登录加密密码盐
    public static final String LOGIN_SALT = "storyweb-bp";
    //request请求头属性
    public static final String REQUEST_AUTH_HEADER="Authentication";

    //JWT-account
    public static final String ACCOUNT = "account";

    //Shiro redis 前缀
    public static final String PREFIX_SHIRO_CACHE = "storyweb-bp:cache:";

    //redis-key-前缀-shiro:refresh_token
    public final static String PREFIX_SHIRO_REFRESH_TOKEN = "storyweb-bp:refresh_token:";

    //JWT-currentTimeMillis
    public final static String CURRENT_TIME_MILLIS = "currentTimeMillis";

    /**
     * 登录图形验证码(五分钟)
     */
    public static final String VERIFY_CODE_FOR_LOGIN = "verifyCodeForLogin:{0}";

    /**
     * 登录随机字符(五分钟)
     */
    public static final String PRIVATE_KEY_FOR_LOGIN = "privateKeyForLogin:{0}";

}
