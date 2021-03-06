package top.luoren.basis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import top.luoren.basis.entity.User;
import top.luoren.basis.exception.CustomException;
import top.luoren.basis.mapper.UserMapper;
import top.luoren.basis.service.RedisService;
import top.luoren.basis.service.UserService;
import top.luoren.basis.util.JwtTokenUtil;
import top.luoren.basis.util.Query;

import java.util.List;
import java.util.Map;

/**
 * @author luoren
 * @date 2019-04-23 16:27
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    //设置用户数据在Redis中的key的前缀
    private static final String REDIS_KEY_PREFIX = "USER:USERNAME:";
    //设置Redis中用户数据的有效时间
    private static final long USER_EXPIRE_TIME = 7200L;

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenUtil tokenUtil;
    @Autowired
    RedisService<String, User> userRedisService;

    @Override
    public List<User> listUser(Map<String, String> params) {
        Page<User> page = new Query<User>(params).getPage();
        return baseMapper.listUser(page, params);
    }

    @Override
    public int userReg(String username, String password) {
        if (baseMapper.loadUserByUsername(username) != null) {
            return -1;
        }
        password = encoder.encode(password);
        return baseMapper.userReg(username, password);
    }

    @Override
    public String userLogin(String username, String password) {
        final Authentication authentication =authentication(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String token = tokenUtil.generateToken(userDetails);
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return token;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        boolean isExistInRedis = true;
        if (!StringUtils.isEmpty(username)) {
            user = userRedisService.get(REDIS_KEY_PREFIX + username);
            if (ObjectUtils.isEmpty(user)) {
                isExistInRedis = false;
                user = baseMapper.loadUserByUsername(username);
            }
        }
        if (ObjectUtils.isEmpty(user)) {
            log.info("用户：" + username + " 不存在.");
            throw new UsernameNotFoundException("用户不存在");
        }
        if (!isExistInRedis) {
            userRedisService.set(REDIS_KEY_PREFIX + username, user, USER_EXPIRE_TIME);
        }
        return user;
    }


    private Authentication authentication(String username, String password) {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new CustomException("账户未激活");
        } catch (BadCredentialsException e) {
            throw new CustomException("用户名或密码错误");
        } catch (LockedException e) {
            throw new CustomException("账户被锁定");
        }
    }
}
