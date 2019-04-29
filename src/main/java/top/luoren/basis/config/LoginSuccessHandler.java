package top.luoren.basis.config;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import top.luoren.basis.entity.User;
import top.luoren.basis.entity.vo.UserVO;
import top.luoren.basis.util.JwtTokenUtil;
import top.luoren.basis.util.RespBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author luoren
 * @date 2019-04-29 15:09
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        RespBody respBody = RespBody.ok("登录成功");
        User user = (User) authentication.getPrincipal();
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        respBody.put("data", userVO);
        out.print(respBody);
        out.flush();
        out.close();
    }
}
