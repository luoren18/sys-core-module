package top.luoren.basis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.luoren.basis.entity.Role;
import top.luoren.basis.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author luoren
 * @date 2019-04-23 15:53
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 分页查询所有User
     *
     * @param page   分页对象
     * @param params 查询参数
     * @return
     */
    List<User> listUser(Page<User> page, Map<String, String> params);

    /**
     * 根据User - id 获取用户的权限
     *
     * @param id
     * @return
     */
    List<Role> getRolesByUserId(int id);

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    User loadUserByUsername(String username);

    /**
     * 用户注册，必须字段
     * @param username
     * @param password
     * @return
     */
    int userReg(String username,String password);
}
