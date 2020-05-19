package com.power.authority.authorization.controller;

import com.power.authority.authorization.cache.RedisCache;
import com.power.authority.authorization.util.RSAUtil;
import com.power.authority.authorization.util.SecurityConsts;
import com.power.authority.authorization.util.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

/**
 * @program: authorization
 * @description: 公共管理控制器
 * @author: xie ting
 * @create: 2020-05-15 11:29
 */
@RestController
@RequestMapping("/common")
public class CommonController extends BaseController {

    @Autowired
    private RedisCache redisCache;

    /**
     * 获取验证码
     * @param identifier
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/getVerifyCode",method = {RequestMethod.GET},produces = "application/json;charset=UTF-8")
    public void getVerifyCode(String identifier, HttpServletResponse response) throws Exception {
        Assert.hasText(identifier, "无效参数");
        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        String code = CommonUtil.drawVerifyCode(4, output);
        String code = VerifyCodeUtils.outputVerifyImage(180, 76, output, 4);
        String identifierKey =MessageFormat.format(SecurityConsts.VERIFY_CODE_FOR_LOGIN, identifier);
        redisCache.set(identifierKey, code,5 * 60);
        ServletOutputStream out = response.getOutputStream();
        output.writeTo(out);
    }

    /**
     * 获取登录数据(公钥)
     * @param identifier
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPasswordSalt", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public Object getPasswordSalt(String identifier) throws Exception {
        Assert.hasText(identifier, "无效参数");
        String[] keyPair = RSAUtil.genKeyPair(1024);
        String passwordKey = MessageFormat.format(SecurityConsts.PRIVATE_KEY_FOR_LOGIN, identifier);
        redisCache.set(passwordKey,keyPair[1],5*60);
        return success(keyPair[0]);
    }

}
