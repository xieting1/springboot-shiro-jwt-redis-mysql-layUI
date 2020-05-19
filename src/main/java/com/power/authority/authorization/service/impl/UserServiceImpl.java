package com.power.authority.authorization.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.power.authority.authorization.cache.RedisCache;
import com.power.authority.authorization.constant.ResultCode;
import com.power.authority.authorization.entity.UserEntity;
import com.power.authority.authorization.jwt.JwtProperties;
import com.power.authority.authorization.mapper.UserMapper;
import com.power.authority.authorization.service.UserService;
import com.power.authority.authorization.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: authorization
 * @description: 用户信息操作接口实现类
 * @author: xie ting
 * @create: 2020-05-13 09:49
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private RedisCache redisCache;


    @Override
    public UserEntity findUserByAccount(String account) {
        return userMapper.findByAccount(account);
    }

    @Override
    public Object login(String account, String password, String identifier, String verifyCode, HttpServletResponse response) throws Exception {

        Map<String,Object> map = new HashMap<String,Object>();
        UserEntity user = userMapper.findByAccount(account);


        if(user==null){
            map.put("code", ResultCode.SYSTEM_ERROR);
            map.put("msg",ResultCode.MSG_SYSTEM_ERROR);
            return map;
        }

        //获取私钥值
        String privateKey = (String) redisCache.get(MessageFormat.format(SecurityConsts.PRIVATE_KEY_FOR_LOGIN, identifier));
        Assert.isTrue(StringUtils.isNotBlank(user.getPassword()) && user.getPassword().equals(RSAUtil.decrypt(password, privateKey)), "用户名或密码错误");

        //删除当前登录的随机秘钥
        redisCache.remove(MessageFormat.format(SecurityConsts.PRIVATE_KEY_FOR_LOGIN, identifier));

//        if(!user.getPassword().equals(ShiroUtils.md5(password,SecurityConsts.LOGIN_SALT))){
//            map.put("code", ResultCode.SYSTEM_ERROR);
//            map.put("msg",ResultCode.MSG_NO_PERMISSION);
//            return map;
//        }
        map.put("code",ResultCode.SUCCESS);
        map.put("msg",ResultCode.MSG_SUCCESS);

        //获得token
       String token = (String) this.loginSuccess(account,response);
        map.put("userId",user.getId());
        map.put("userName",account);
        map.put("token",token);
        return map;
    }

    @Override
    public Object loginSuccess(String account, HttpServletResponse response) {
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());

        // 清除可能存在的Shiro权限信息缓存
        String tokenKey= SecurityConsts.PREFIX_SHIRO_CACHE + account;
        if (redisCache.exists(tokenKey)) {
            redisCache.remove(tokenKey);
        }

        //更新RefreshToken缓存的时间戳
        String refreshTokenKey= SecurityConsts.PREFIX_SHIRO_REFRESH_TOKEN + account;
        if (redisCache.exists(refreshTokenKey)) {
            redisCache.set(refreshTokenKey, currentTimeMillis, JwtProperties.refreshTokenExpireTime*60*60l);
        }else{
            redisCache.set(refreshTokenKey, currentTimeMillis, JwtProperties.refreshTokenExpireTime*60*60l);
        }

        Map<String,Object> map = new HashMap<String,Object>();
        //生成token
//        JSONObject json = new JSONObject();
//            json.put("token",token );
////            //写入header
////            response.setHeader(SecurityConsts.REQUEST_AUTH_HEADER, token);
////            response.setHeader("Access-Control-Expose-Headers", SecurityConsts.REQUEST_AUTH_HEADER);
        String token = "";
        try {
            token = JwtUtils.sign(account, currentTimeMillis);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //将token传到前端、写入页面缓存中
        return token;

    }

    @Override
    public IPage getForPagination(Page<Object> page, String keyword, Integer type, Long roleId) {
        return userMapper.getForPagination(page,keyword,type,roleId);
    }


}
