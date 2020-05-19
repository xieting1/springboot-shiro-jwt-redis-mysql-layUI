package com.power.authority.authorization.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: authorization
 * @description: jwt设置的一些参数信息
 * @author: xie ting
 * @create: 2020-05-11 20:29
 */
@Component
public class JwtProperties {
    //token过期时间，单位分钟
    public static Integer tokenExpireTime = 30 ;
    //刷新Token过期时间，单位分钟
    public static Integer refreshTokenExpireTime = 30; //用户在线的情况下，30分钟刷新一次
    //Shiro缓存有效期，单位分钟
    public static Integer shiroCacheExpireTime = 120;
    //token加密密钥
    public static String secretKey = "Xt6090152";

}
