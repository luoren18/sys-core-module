package top.luoren.basis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import top.luoren.basis.entity.User;
import top.luoren.basis.service.UserService;

/**
 * 自定义用户信息验证
 *
 * @author luoren
 * @date 2019-04-29 16:53
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDetails userDetails = userService.loadUserByUsername(username);
        //校验密码是否正确
        if (!userDetails.getPassword().equals(passwordEncoder.encode(password))) {
            throw new DisabledException("username or password error!");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
