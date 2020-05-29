package study.springboot.shiro.auth;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShiroCfg {

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
//        manager.setRealm(myRealm());
        return manager;
    }

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        //没有登陆的json返回
        factoryBean.setLoginUrl("/unlogin");
        //没有权限的json返回
        factoryBean.setUnauthorizedUrl("/no");

        Map<String, String> filterChainMap = Maps.newLinkedHashMap();
        //配置不会被拦截的链接 顺序判断
        filterChainMap.put("/static/**", "anon");
        filterChainMap.put("/ajaxLogin", "anon");
        filterChainMap.put("/map", "anon");
        filterChainMap.put("/login", "anon");
        //注意,如果roles[admin,guest]是用户需要同时包含两者角色才可以访问,是且的关系;
        //如果想改为或的关系,请继承AuthorizationFilter并加入过滤连,perm资源也是一样,需要继承PermissionsAuthorizationFilter加入过滤链;
        filterChainMap.put("/test", "authc,roles[admin]");
        filterChainMap.put("/**", "authc");
        factoryBean.setFilterChainDefinitionMap(filterChainMap);
        factoryBean.setSecurityManager(securityManager);
        return factoryBean;
    }
}
