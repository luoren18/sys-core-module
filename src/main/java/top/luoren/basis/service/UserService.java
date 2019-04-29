package top.luoren.basis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;
import top.luoren.basis.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author luoren
 * @date 2019-04-23 16:27
 */
public interface UserService extends IService<User>, UserDetailsService {
    /**
     * 查询用户数据，可添加分页字段
     * @param params
     * @return
     */
    List<User> listUser(Map<String, String> params);

    /**
     * 用户注册，必须字段
     * @param username
     * @param password
     * @return
     */
    int userReg(String username,String password);
}
