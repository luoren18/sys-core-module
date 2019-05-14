package top.luoren.basis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.luoren.basis.entity.User;
import top.luoren.basis.filter.CaptchaFilter;
import top.luoren.basis.filter.JwtAuthenticationTokenFilter;
import top.luoren.basis.service.UserService;

/**
 * @author luoren
 * @date 2019-04-26 17:35
 * <p>
 * ## 笔记：
 * 1.每个继承 WebSecurityConfigurerAdapter 的类定义一条新的 Filter Chain
 * 2.Spring Security默认是禁用注解的，要想开启注解，需要在继承WebSecurityConfigurerAdapter
 * 的类上加@EnableGlobalMethodSecurity注解，来判断用户对某个控制层的方法是否具有访问权限
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;
    @Autowired
    CustomAccessDeniedHandler customAccessDeniedHandler;
    @Autowired
    CustomAuthenticationEntryPoint unauthorizedHandler;
    @Autowired
    CaptchaFilter captchaFilter;
    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    CustomMetadataSource metadataSource;
    @Autowired
    CustomAccessDecisionManager accessDecisionManager;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
            /**
             * 自定义用户权限处理
             * @param object
             * @param <O>
             * @return
             */
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                object.setSecurityMetadataSource(metadataSource);
                object.setAccessDecisionManager(accessDecisionManager);
                return object;
            }
        });
        //
        http.exceptionHandling()
                //认证授权异常处理
                .accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(unauthorizedHandler)
                .and().csrf().disable()//关闭csrf
                //前后端分离，JWT 认证，关闭session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()//权限配置
                .antMatchers("/authentication/**").permitAll()
                .anyRequest()//所有请求
                .authenticated()//都需要认证
                .and().headers().cacheControl();// 禁用缓存
        //添加验证码过滤器，JWT 过滤器
        http.addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }


    /**
     * 使用BCrypt的强散列哈希加密实现，并可以由客户端指定加密的强度strength，强度越高安全性自然就越高，默认为10.
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
