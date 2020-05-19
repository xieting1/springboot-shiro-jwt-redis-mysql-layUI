package com.power.authority.authorization.shiro;

import com.power.authority.authorization.cache.RedisCache;
import com.power.authority.authorization.entity.ResourceEntity;
import com.power.authority.authorization.entity.RoleEntity;
import com.power.authority.authorization.entity.UserEntity;
import com.power.authority.authorization.jwt.JwtToken;
import com.power.authority.authorization.service.ResourceService;
import com.power.authority.authorization.service.RoleService;
import com.power.authority.authorization.service.UserService;
import com.power.authority.authorization.util.JwtUtils;
import com.power.authority.authorization.util.SecurityConsts;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * @program: authorization
 * @description: shiro权限自定义 查询用户的角色和权限信息并保存到权限管理器
 * @author: xie ting
 * @create: 2020-05-11 10:58
 */
@Service
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 授权信息
     * 只有需要验证权限时才会调用, 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     * 在配有缓存的情况下，只加载一次.
     * 不然只有在调用判断权限的方法时，才会触发这个方法
     * 这里因启用了JWT拦截，所以不再注入shiro的缓存
     * subject.hasRole();
     * subject.checkPermission();
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        String account = JwtUtils.getClaim(principals.toString(), SecurityConsts.ACCOUNT);
        UserEntity userInfo = userService.findUserByAccount(account);

        //获取用户角色
        long userId = userInfo.getId();
        RoleEntity roleEntity = roleService.findRoleByUserId(userId);
        if(roleEntity!=null){
            //获取权限
            long rId = roleEntity.getrId();
            //获得角色
            authorizationInfo.addRole(roleEntity.getrKey());
            List<Map> resourceList = resourceService.findByRoleId(rId);

            for(Map map : resourceList){
                //获得权限树
                authorizationInfo.addStringPermission(map.get("sSourceKey").toString());
//            authorizationInfo.addRole(role.getrName());
//            for(Object auth: authorityList){
//                authorizationInfo.addStringPermission(auth.toString());
//            }
            }
            return authorizationInfo;
        }
        return null;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {

        String token = (String)auth.getPrincipal();
        String account  = JwtUtils.getClaim(token, SecurityConsts.ACCOUNT);

        if (account == null) {
            throw new AuthenticationException("token is invalid.");
        }

        UserEntity userInfo = userService.findUserByAccount(account);
        if (userInfo == null) {
            throw new AuthenticationException("user is not existed!");
        }

        //当前的用户在token校验通过之后
        //应该考虑是否需要将用户信息放置在缓存中
        //此处应不再建议使用session
        String refreshTokenCacheKey = SecurityConsts.PREFIX_SHIRO_REFRESH_TOKEN + account;
        try{
            if (JwtUtils.verify(token) && redisCache.exists(refreshTokenCacheKey)) {
                String currentTimeMillisRedis = (String) redisCache.get(refreshTokenCacheKey);
                // 获取AccessToken时间戳，与RefreshToken的时间戳对比
                if (JwtUtils.getClaim(token, SecurityConsts.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
                    return new SimpleAuthenticationInfo(token, token, "myRealm");
                }
            }
        }catch(UnsupportedEncodingException e){
            throw new AuthenticationException("Token checked error.");
        }

        throw new AuthenticationException("Token expired or incorrect.");
    }


}
