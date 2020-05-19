package com.power.authority.authorization.config;

import com.power.authority.authorization.cache.RedisManager;
import com.power.authority.authorization.filter.JwtFilter;
import com.power.authority.authorization.filter.KickoutSessionFilter;
import com.power.authority.authorization.filter.SysUserFilter;
import com.power.authority.authorization.shiro.MyRealm;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: authorization
 * @description: shiro解析器、可以xml配置、也可以代码化
 *
 * @RequiresPermissions({"user:update:view"})//检查操作权限
 *
 * @RequiresPermissions(value={"user:add","user:view"},logical=Logical.OR)//两个操作权限其中一个满足条件即可通过检查
 *
 * @RequiresRoles({"admin"})//检查角色
 *
 * @RequiresRoles(value={"debug","admin"},logical=Logical.OR)//两个角色其中一个角色满足条件即可
 *
 * @RequiresAuthentication//检查是否通过shiro认证
 * @RequiresGuest//不需要验证
 * @RequiresUser//检查用户是否是当前系统中的用户
 *
 * @author: xie ting
 * @create: 2020-05-11 10:44
 */
@Configuration
//@ImportResource(locations = {"/spring-shiro.xml"})
//约定优于配置、这里鼓励使用代码实现
public class ShiroConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    /**
//     * 自定义realm容器
//     */
    @Bean
    public MyRealm myShiroRealm() {
        MyRealm myRealm = new MyRealm();
//        //自定义的缓存管理器
//        RedisManager redisManager = new RedisManager();
//
//        //配置是否开启缓存
//        myRealm.setCacheManager(redisManager);
//
//        myRealm.setCachingEnabled(true);
//        myRealm.isAuthenticationCachingEnabled();
//        myRealm.isAuthorizationCachingEnabled();

        return myRealm;
    }


    /**
     *  开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public static DefaultAdvisorAutoProxyCreator getLifecycleBeanPostProcessor() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public DefaultWebSecurityManager  securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();

        securityManager.setRealm(myShiroRealm());

        //关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        //自定义缓存管理
//        securityManager.setCacheManager(shiroCacheManager());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        // 添加jwt过滤器
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt", jwtFilter());
        filterMap.put("logout", new KickoutSessionFilter());
        shiroFilter.setFilters(filterMap);

        //拦截器
        Map<String,String> filterRuleMap = new LinkedHashMap<>();
        //登出
        filterRuleMap.put("/logout", "logout");
        /**
         * 静态资源的加载不需要通过jwt验证
         */
        filterRuleMap.put("/static/**","anon");
        //将所有的请求都给jwt来验证
        filterRuleMap.put("/**", "jwt");
        shiroFilter.setFilterChainDefinitionMap(filterRuleMap);

        return shiroFilter;
    }

    @Bean
    public JwtFilter jwtFilter(){
        return new JwtFilter();
    }

    @Bean
    public RedisManager shiroCacheManager(){
        return new RedisManager();
    }

}
