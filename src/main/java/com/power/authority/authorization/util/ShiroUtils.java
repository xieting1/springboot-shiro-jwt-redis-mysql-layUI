package com.power.authority.authorization.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @program: authorization
 * @description: shiro工具类、加解密
 * @author: xie ting
 * @create: 2020-05-13 19:46
 */
public class ShiroUtils {
    public final static String hashAlgorithmName = "MD5";
    //循环次数
    public final static int hashIterations = 1024;
    /**
     * shiro密码加密工具类
     *
     * @param credentials 密码
     * @param saltSource 密码盐
     * @return
     */
    public static String md5(String credentials, String saltSource) {
        ByteSource salt = new Md5Hash(saltSource);
        return new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations).toString();
    }

    public static void main(String[] args) {
        System.out.println(md5("123456",SecurityConsts.LOGIN_SALT));
    }
}
