package com.power.authority.authorization.controller;

import com.power.authority.authorization.cache.RedisCache;
import com.power.authority.authorization.service.ResourceService;
import com.power.authority.authorization.service.UserService;
import com.power.authority.authorization.util.SecurityConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;

/**
 * @program: authorization
 * @description: 封装初始页面控制器
 * @author: xie ting
 * @create: 2020-05-15 14:00
 */
@RestController
@RequestMapping("/index/")
public class IndexController extends BaseController {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserService userService;

    @Autowired
    private ResourceService resourceService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 登录 无token提交
     * @return
     */
    @RequestMapping(value = "login", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public Object login(String name, String password, String identifier, String verifyCode, HttpServletResponse response) throws Exception {

        Assert.hasText(name, "无效账户名");
        Assert.hasText(password, "无效密码");
        Assert.hasText(identifier, "无效identifier");
        Assert.hasText(verifyCode, "无效验证码");

        String codeKey = MessageFormat.format(SecurityConsts.VERIFY_CODE_FOR_LOGIN, identifier);
        String code = (String) redisCache.get(codeKey);
        Assert.isTrue(verifyCode.equalsIgnoreCase(code), "验证码错误");

        return success(userService.login(name,password,identifier,verifyCode,response));
    }


    /**
     * 获得用户权限树
     * @return
     */
    @RequestMapping(value = "getCurrentAdminMenuList", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public Object getCurrentAdminMenuList(long userId) {
        return  success(resourceService.findResourceTreeByUserId(userId));
    }

}
