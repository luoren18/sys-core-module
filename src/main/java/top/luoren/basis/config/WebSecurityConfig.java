package top.luoren.basis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.luoren.basis.service.UserService;

/**
 * @author luoren
 * @date 2019-04-26 17:35
 *
 * ## 笔记：
 * 1.每个继承 WebSecurityConfigurerAdapter 的类定义一条新的 Filter Chain
 * 2.Spring Security默认是禁用注解的，要想开启注解，需要在继承WebSecurityConfigurerAdapter
 *   的类上加@EnableGlobalMethodSecurity注解，来判断用户对某个控制层的方法是否具有访问权限
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    LoginSuccessHandler loginSuccessHandler;
    @Autowired
    LoginFailureHandler loginFailureHandler;
    @Autowired
    AuthenticationProvider authenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        //自定义用户校验规则
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/reg", "/login_page");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login_page").loginProcessingUrl("/login")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                .and().csrf().disable();
    }

    /**
     * 使用BCrypt的强散列哈希加密实现，并可以由客户端指定加密的强度strength，强度越高安全性自然就越高，默认为10.
     *
     * @return
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
