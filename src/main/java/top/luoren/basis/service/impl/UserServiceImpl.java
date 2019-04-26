package top.luoren.basis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.luoren.basis.entity.User;
import top.luoren.basis.mapper.UserMapper;
import top.luoren.basis.service.UserService;
import top.luoren.basis.util.Query;

import java.util.List;
import java.util.Map;

/**
 * @author luoren
 * @date 2019-04-23 16:27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public List<User> listUser(Map<String, String> params) {
        Page<User> page = new Query<User>(params).getPage();
        return baseMapper.listUser(page, params);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
