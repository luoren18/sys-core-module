package top.luoren.basis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import top.luoren.basis.entity.User;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * AccessDecisionManager是由AbstractSecurityInterceptor调用的，它负责鉴定用户是否有访问对应资源（方法或URL）的权限。
 *
 * @author luoren
 * @date 2019-04-28 15:57
 */
@Slf4j
@Component
public class CustomAccessDecisionManager implements AccessDecisionManager {
    /**
     * 通过传递的参数来决定用户是否有访问对应受保护对象的权限
     * decide这个方法没有任何的返回值，需要在没有通过授权时抛出AccessDeniedException
     *
     * @param authentication   当前正在请求受包含对象的Authentication
     * @param object           受保护对象，其可以是一个MethodInvocation、JoinPoint或FilterInvocation。
     * @param configAttributes 与正在请求的受保护对象相关联的配置属性
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        User user = (User) authentication.getPrincipal();
        log.info("访问资源的用户为：" + user.getUsername());
        // 如果访问资源不需要任何权限则直接通过
        if (CollectionUtils.isEmpty(configAttributes)) {
            return;
        }
        Set<String> userRoles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        while (iterator.hasNext()) {
            ConfigAttribute ca = iterator.next();
            String needRole = ca.getAttribute();
            //当用户没有当前访问所需的权限
            if (!userRoles.contains(needRole)) {
                throw new AccessDeniedException("权限不足!");
            }
        }

    }

    /**
     * 表示当前AccessDecisionManager是否支持对应的ConfigAttribute
     *
     * @param attribute
     * @return
     */
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    /**
     * 表示当前AccessDecisionManager是否支持对应的受保护对象类型
     *
     * @param clazz
     * @return
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
