package top.luoren.basis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.luoren.basis.annotation.Log;
import top.luoren.basis.entity.ImageCode;
import top.luoren.basis.entity.User;
import top.luoren.basis.service.ImageCodeService;
import top.luoren.basis.service.UserService;
import top.luoren.basis.util.ImageCodeUtil;
import top.luoren.basis.util.RespBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author luoren
 * @date 2019-04-29 10:24
 */
@RestController
@RequestMapping("/authentication")
public class LoginRegController {

    @Autowired
    UserService userService;
    @Autowired
    ImageCodeService imageCodeService;

    @Log("注册接口")
    @RequestMapping("/reg")
    public RespBody userReg(@RequestBody User user) {

        int code = userService.userReg(user.getUsername(), user.getPassword());
        if (code == -1) {
            return RespBody.error("用户名已存在,注册失败");
        } else if (code == 1) {
//            RespBody respBody=RespBody.ok("注册成功");
//            respBody.put()
            return RespBody.ok("注册成功");
        }
        return RespBody.error("注册失败");
    }


    @RequestMapping("/login")
    public RespBody login(@RequestBody User user) {
        String token =userService.userLogin(user.getUsername(), user.getPassword());
        RespBody respBody=RespBody.ok();
        respBody.put("token",token);
        return respBody;
    }

    /**
     * 获取验证码
     *
     * @param uuid
     * @param response
     * @throws IOException
     */
    @RequestMapping("/captcha")
    public void captcha(String uuid, HttpServletResponse response) throws IOException {
        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        ImageCode imageCode = ImageCodeUtil.creatImage(150, 40, 5);
        imageCode.setId(uuid);
        imageCodeService.saveOrUpdate(imageCode);
        ImageIO.write(imageCode.getImage(), "jpeg", response.getOutputStream());
    }

}
