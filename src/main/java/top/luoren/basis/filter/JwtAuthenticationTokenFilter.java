package top.luoren.basis.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import top.luoren.basis.config.CustomAuthenticationEntryPoint;
import top.luoren.basis.entity.constant.JwtConst;
import top.luoren.basis.exception.IdentityAuthException;
import top.luoren.basis.service.UserService;
import top.luoren.basis.util.JwtTokenUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author luoren
 * @date 2019-05-08 15:33
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    JwtTokenUtil tokenUtil;
    @Autowired
    UserService userService;
    @Autowired
    CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = httpServletRequest.getHeader(JwtConst.HEADER_STRING);
        try {
            validateToken(token);
        } catch (IdentityAuthException e) {
            authenticationEntryPoint.commence(httpServletRequest, httpServletResponse, e);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String username = tokenUtil.getUsernameFromToken(token);
            UserDetails userDetails = userService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                    httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            httpServletRequest.setAttribute("username", username);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


    private void validateToken(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new IdentityAuthException("token 不存在");
        }
        if (tokenUtil.getUsernameFromToken(token) == null) {
            throw new IdentityAuthException("无效的token");
        }
        if (tokenUtil.isTokenExpired(token)) {
            throw new IdentityAuthException("过期的token");
        }
    }
}
