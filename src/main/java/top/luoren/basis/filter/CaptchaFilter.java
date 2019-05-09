package top.luoren.basis.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import top.luoren.basis.entity.ImageCode;
import top.luoren.basis.exception.ImageCodeException;
import top.luoren.basis.service.ImageCodeService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录验证码验证逻辑
 *
 * @author luoren
 * @date 2019-05-06 15:06
 */
//@Component
public class CaptchaFilter extends OncePerRequestFilter {
    private static final String LOGIN_URI = "/login";
    private static final String METHOD_POST = "post";
    @Autowired
    ImageCodeService imageCodeService;




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (LOGIN_URI.equalsIgnoreCase(request.getRequestURI()) && METHOD_POST.equalsIgnoreCase(request.getMethod())) {
            String imageCode = request.getParameter("imageCode");
            String codeId = request.getParameter("CODE_ID");
            try {
                validateImageCode(imageCode, codeId);
            } catch (ImageCodeException e) {
                e.printStackTrace();
            }
            filterChain.doFilter(request, response);
        }
    }


    private void validateImageCode(String code, String codeID) {
        if (StringUtils.isEmpty(code)) {
            throw new ImageCodeException("验证码不能为空");
        }
        if (StringUtils.isEmpty(codeID)) {
            throw new ImageCodeException("无效的验证码,不存在 CODE_ID");
        }
        ImageCode imageCode = imageCodeService.getById(codeID);
        if (ObjectUtils.isEmpty(imageCode)) {
            throw new ImageCodeException("无效的验证码");
        }
        if (imageCode.isExpire()) {
            //删除过期的验证码
            imageCodeService.removeById(codeID);
            throw new ImageCodeException("验证码已过期");
        }
        if (!imageCode.getCode().equalsIgnoreCase(code)) {
            throw new ImageCodeException("错误的验证码");
        }
    }


}
