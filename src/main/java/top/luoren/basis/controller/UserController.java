package top.luoren.basis.controller;

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
    public RespBody sayHello() {
        return RespBody.ok();
    }

}
