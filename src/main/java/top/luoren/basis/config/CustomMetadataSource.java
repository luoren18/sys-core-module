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
 * 返回可以访问该接口的角色
 *
 * @author luoren
 * @date 2019-05-09 14:48
 */
@Component
public class CustomMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    MenuMapper menuMapper;
    private final AntPathMatcher antPathMatcher=new AntPathMatcher();
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation invocation= (FilterInvocation) object;
        String path=invocation.getRequestUrl();
        Set<Menu> menus=  menuMapper.getAllMenu();
        for (Menu menu:menus){
            if (antPathMatcher.match(menu.getUrl(),path)&& menu.getRoles().size()>0){
                return SecurityConfig.createList(menu.getRoles().stream().map(Role::getName).toArray(String[]::new));
            }
        }
        return SecurityConfig.createList();
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
