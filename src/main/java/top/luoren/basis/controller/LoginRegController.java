package top.luoren.basis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.luoren.basis.annotation.Log;
import top.luoren.basis.service.UserService;
import top.luoren.basis.util.RespBody;

import java.awt.image.BufferedImage;

/**
 * @author luoren
 * @date 2019-04-29 10:24
 */
@Controller
public class LoginRegController {

    @Autowired
    UserService userService;

    @Log("注册接口")
    @RequestMapping("/reg")
    @ResponseBody
    public RespBody userReg(String username, String password) {
        int code = userService.userReg(username, password);
        if (code == -1) {
            return RespBody.error("用户名已存在,注册失败");
        } else if (code == 1) {
            return RespBody.ok("注册成功");
        }
        return RespBody.error("注册失败");
    }

    @RequestMapping("/login_page")
    public String loginPage() {
        return "login";
    }


    @RequestMapping("/login")
    @ResponseBody
    public RespBody login() {
        return RespBody.ok();
    }


    @RequestMapping("/captcha")
    public BufferedImage captcha(String uuid) {
        return null;
    }
}
