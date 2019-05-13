package top.luoren.basis.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.luoren.basis.util.RespBody;

/**
 * @author luoren
 * @date 2019-04-29 08:51
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("hello")
    @PreAuthorize("hasRole('ROLE_admin')")
    public RespBody sayHello() {
        return RespBody.ok();
    }

    @PostMapping("/hello2")
    public RespBody hello2(){
        return RespBody.error("其实已经访问成功！");
    }
}
