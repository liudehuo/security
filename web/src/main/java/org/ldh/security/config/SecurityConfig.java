package org.ldh.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * 使用配置类方式为Security创建用户信息
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    // 注入数据源
    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // 自动创建表
        // jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    /**
     * 对密码加密时需要用到该对象
     * There is no PasswordEncoder mapped for the id "null"
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证
     * @param auth the {@link AuthenticationManagerBuilder} to use
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 自定义实现类方式设置用户信息
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * 授权
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 注销登录
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login.html").permitAll();
        // 没有权限自定义页面
        http.exceptionHandling().accessDeniedPage("/403.html");
        // 自定义登录页面
        http.formLogin()
            // 设置登录页面
            .loginPage("/login.html")
            // 登录访问路径
            .loginProcessingUrl("/login")
            // 登录成功后跳转的路径
            .defaultSuccessUrl("/user.html").permitAll()
            .and().authorizeRequests()
            // 可以直接访问不需要认证的路径
            .antMatchers("/","/index","/index.html").permitAll()
            // 拥有指定权限才能访问
            .antMatchers("/add","remove","update").hasAuthority("CRUD")
            // 拥有指定角色才能访问
            .antMatchers("/bak").hasRole("manager")
            .anyRequest().authenticated()

            // 配置自动登录
            .and().rememberMe().tokenRepository(persistentTokenRepository())
            // 设置有效时长
            .tokenValiditySeconds(60)
            .userDetailsService(userDetailsService)

            // 关闭csrf保护
            .and().csrf().disable();
    }
}


