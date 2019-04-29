package top.luoren.basis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import top.luoren.basis.entity.Menu;
import top.luoren.basis.entity.Role;
import top.luoren.basis.mapper.MenuMapper;

import java.util.Collection;
import java.util.Set;

/**
 * FilterInvocationSecurityMetadataSource有一个默认的
 * 实现类DefaultFilterInvocationSecurityMetadataSource，
 * 该类的主要功能就是通过当前的请求地址，获取该地址需要的用户角色。
 *
 * @author luoren
 * @date 2019-04-28 10:15
 */
@Component
public class CustomMetadataSource implements FilterInvocationSecurityMetadataSource {

    AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Autowired
    MenuMapper menuMapper;

    /**
     * 该方法的功能就是获取当前请求所需的权限
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        Set<Menu> allMenu = menuMapper.getAllMenu();
        for (Menu menu : allMenu) {
            if (antPathMatcher.match(menu.getUrl(), requestUrl) && menu.getRoles().size() > 0) {
                String[] roleNameArr = (String[]) menu.getRoles().stream().map(Role::getName).toArray();
                return SecurityConfig.createList(roleNameArr);
            }
        }
        //所有请求都需要先登录
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
