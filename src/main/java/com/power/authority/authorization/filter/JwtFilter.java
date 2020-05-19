package com.power.authority.authorization.filter;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.power.authority.authorization.cache.RedisCache;
import com.power.authority.authorization.context.UserContext;
import com.power.authority.authorization.entity.LoginEntity;
import com.power.authority.authorization.jwt.JwtProperties;
import com.power.authority.authorization.jwt.JwtToken;
import com.power.authority.authorization.util.JwtUtils;
import com.power.authority.authorization.util.SecurityConsts;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: authorization
 * @description: 自定义JWT拦截器
 * @author: xie ting
 * @create: 2020-05-11 20:23
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RedisCache redisCache;
    @Autowired
    JwtProperties jwtProperties;

    /**
     * 检测Header里Authorization字段
     * 判断是否登录
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader(SecurityConsts.REQUEST_AUTH_HEADER);
        return authorization != null;
    }

    /**
     * 登录验证
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader(SecurityConsts.REQUEST_AUTH_HEADER);

        JwtToken token = new JwtToken(authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);

        // 绑定上下文
        String account = JwtUtils.getClaim(authorization, SecurityConsts.ACCOUNT);
        UserContext userContext= new UserContext(new LoginEntity(account));

        // 如果没有抛出异常则代表登录成功，返回true
        return true;
    }

    /**
     * 刷新AccessToken，进行判断RefreshToken是否过期，未过期就返回新的AccessToken且继续正常访问
     */
    private boolean refreshToken(ServletRequest request, ServletResponse response) {
        // 获取AccessToken(Shiro中getAuthzHeader方法已经实现)
        String token = this.getAuthzHeader(request);
        // 获取当前Token的帐号信息
        String account = JwtUtils.getClaim(token, SecurityConsts.ACCOUNT);
        String refreshTokenCacheKey = SecurityConsts.PREFIX_SHIRO_REFRESH_TOKEN + account;
        try {
            // 判断Redis中RefreshToken是否存在
            if (redisCache.exists(refreshTokenCacheKey)) {
                // 获取RefreshToken时间戳,及AccessToken中的时间戳
                // 相比如果一致，进行AccessToken刷新
                String currentTimeMillisRedis = (String) redisCache.get(refreshTokenCacheKey);
                String tokenMillis = JwtUtils.getClaim(token, SecurityConsts.CURRENT_TIME_MILLIS);

                if (tokenMillis.equals(currentTimeMillisRedis)) {

                    // 设置RefreshToken中的时间戳为当前最新时间戳
                    String currentTimeMillis = String.valueOf(System.currentTimeMillis());
                    Integer refreshTokenExpireTime = jwtProperties.refreshTokenExpireTime;
                    redisCache.set(refreshTokenCacheKey, currentTimeMillis, refreshTokenExpireTime * 60l);

                    // 刷新AccessToken，为当前最新时间戳

                    token = JwtUtils.sign(account, currentTimeMillis);


                    // 使用AccessToken 再次提交给ShiroRealm进行认证，如果没有抛出异常则登入成功，返回true
                    JwtToken jwtToken = new JwtToken(token);
                    this.getSubject(request, response).login(jwtToken);

                    // 设置响应的Header头新Token
                    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                    httpServletResponse.setHeader(SecurityConsts.REQUEST_AUTH_HEADER, token);
                    httpServletResponse.setHeader("Access-Control-Expose-Headers", SecurityConsts.REQUEST_AUTH_HEADER);
                    return true;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 是否允许访问
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                this.executeLogin(request, response);
            } catch (Exception e) {
                String msg = e.getMessage();
                Throwable throwable = e.getCause();
                if (throwable != null && throwable instanceof SignatureVerificationException) {
                    msg = "Token或者密钥不正确(" + throwable.getMessage() + ")";
                } else if (throwable != null && throwable instanceof TokenExpiredException) {
                    // AccessToken已过期
                    if (this.refreshToken(request, response)) {
                        return true;
                    } else {
                        msg = "Token已过期(" + throwable.getMessage() + ")";
                    }
                } else {
                    if (throwable != null) {
                        msg = throwable.getMessage();
                    }
                }
                this.response401(request, response, msg);
                return false;
            }
        }
        return true;
    }

    /**
     * 401非法请求
     * @param req
     * @param resp
     */
    private void response401(ServletRequest req, ServletResponse resp,String msg) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = httpServletResponse.getWriter();

            Map<String,Object> map = new HashMap<String,Object>();
//            Result result = new Result();
//            result.setResult(false);
//            result.setCode(Constants.PASSWORD_CHECK_INVALID);
//            result.setMessage(msg);
            map.put("code","299");
            map.put("message",msg);
            map.put("result",false);
            out.append(JSON.toJSONString(map));
        } catch (IOException e) {
            logger.error("返回Response信息出现IOException异常:" + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
