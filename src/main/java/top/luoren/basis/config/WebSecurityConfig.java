package top.luoren.basis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author luoren
 * @date 2019-04-26 17:35
 *
 * ## 笔记：
 * 1.每个继承 WebSecurityConfigurerAdapter 的类定义一条新的 Filter Chain
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
}
