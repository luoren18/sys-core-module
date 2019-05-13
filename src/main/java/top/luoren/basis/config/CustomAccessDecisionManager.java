package top.luoren.basis.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * 判断用户是否拥有当前请求所需要的权限
 *
 * @author luoren
 * @date 2019-05-13 14:19
 */
@Component
public class CustomAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (CollectionUtils.isEmpty(configAttributes)) {
            return;
        }
        Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
        for (ConfigAttribute configAttribute:configAttributes){
            boolean flag=grantedAuthorities.stream().map(GrantedAuthority::getAuthority).anyMatch(a -> a.equalsIgnoreCase(configAttribute.getAttribute()));
            if (flag){
                return;
            }
        }
        throw new AccessDeniedException("不允许访问");

    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
