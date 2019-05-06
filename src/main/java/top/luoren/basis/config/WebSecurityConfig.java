package top.luoren.basis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.luoren.basis.filter.VaptchaFilter;
import top.luoren.basis.util.RespBody;

import java.io.PrintWriter;

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
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    VaptchaFilter vaptchaFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.addFilterBefore(vaptchaFilter, UsernamePasswordAuthenticationFilter.class);
        http.formLogin()//表单登录
                .loginPage("/login_page")
                .loginProcessingUrl("/login")
                .successHandler((request, response, authentication) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    RespBody respBody = RespBody.ok("登录成功");
                    out.print(respBody);
                    out.flush();
                    out.close();
                })
                .failureHandler((request, response, exception) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    RespBody respBody = RespBody.error(exception.getMessage());
                    out.print(respBody);
                    out.flush();
                    out.close();
                })
                .and()
                .authorizeRequests()//权限配置
                .antMatchers("/captcha", "/reg", "/login_page", "/code/image", "/login.html").permitAll()
                .anyRequest()//所有请求
                .authenticated()//都需要认证
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
