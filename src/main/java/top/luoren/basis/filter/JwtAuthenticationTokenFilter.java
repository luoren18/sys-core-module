package top.luoren.basis.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
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
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Value("${jwt.header}")
    private String token_header;
    @Value("${jwt.token-head}")
    String tokenStart;
    @Autowired
    JwtTokenUtil tokenUtil;
    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader(token_header);
        if (!StringUtils.isEmpty(token)) {
            String username = tokenUtil.getUsernameFromToken(token);
            if (StringUtils.isEmpty(username)) {
                throw new UsernameNotFoundException("无效的token");
            }
            if (tokenUtil.isTokenExpired(token)) {
                throw new NonceExpiredException("token已过期");
            }
            if (SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails=userService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken();
                UsernamePasswordAuthenticationFilter
            }
        }


    }
}
