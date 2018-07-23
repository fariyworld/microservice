package com.mace.microservice.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;

/**
 * description:
 * <br />
 * Created by mace on 13:46 2018/7/17.
 */
@Configuration
@EnableWebSecurity//开启 WEB 保护
@EnableGlobalMethodSecurity(prePostEnabled = true)// 开启方法级别的保护
// @PreAuthorize("hasRole('ADMIN')")  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
// @PreAuthorize("hasAnyRole('ADMIN','USER')") @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private DataSource dataSource;


    /**
     * description: 采用SHA-256 +随机盐+密钥对密码进行加密。SHA系列是Hash算法,其过程是不可逆的
     * <br /><br />
     * create by mace on 2018/7/17 14:15.
     * @param
     * @return: org.springframework.security.crypto.password.PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * description: 登录成功处理器 日志展示用户信息
     * <br /><br />
     * create by mace on 2018/7/18 11:57.
     * @param
     * @return: com.mace.microservice.oauth2.config.LoginSuccessHandler
     */
    @Bean
    public LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler();
    }


    /**
     * description: 注销成功后的处理器 日志展示用户信息
     * <br /><br />
     * create by mace on 2018/7/18 14:11.
     * @param
     * @return: com.mace.microservice.oauth2.config.LogoutHandler
     */
    @Bean
    public MyLogoutHandler myLogoutHandler(){
        return new MyLogoutHandler();
    }


    /**
     * description: 数据库实现记住我
     * <br /><br />
     * create by mace on 2018/7/19 9:02.
     * @param
     * @return: org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl
     */
    @Bean
    public JdbcTokenRepositoryImpl tokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }


    /**
     * description: 配置哪些请求需要安全验证
     * <br /><br />
     * create by mace on 2018/7/17 14:04.
     * @param http
     * @return: void
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .csrf().disable();
        // 问题：为了帮助保护在此网站中输入的信息的安全，此内容的发布者不允许在框架中显示该信息。
        // 原因：spring security安全框架设置了默认的X-Frame-Options为DENY,更改spring security配置
        // 解决代码
        http.headers().frameOptions().disable();

        // 关闭csrf
        http.csrf().disable();

        http
                .authorizeRequests()
                    .antMatchers("/css/**","/index","/","/js/**").permitAll()//无需登录认证权限
                    .antMatchers("/user/**").hasAnyRole("USER","ADMIN")
                    .antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                .formLogin()//基于form表单登录验证
                    .loginPage("/login").permitAll()//指定登录页是”/login”
                    .defaultSuccessUrl("/")
                    .successHandler(loginSuccessHandler())
                    /*.failureUrl("/login-error")*/
                .and()
                .logout()
                    /*.logoutUrl("logoutUrl")*///触发注销操作的url，默认是/logout
                    /*.logoutSuccessUrl("/login")*///注销操作发生后重定向到的url，默认为 /login?logout
                    /*.logoutSuccessHandler()*///指定自定义的 LogoutSuccessHandler。如果指定了， logoutSuccessUrl() 将会被忽略 在 LogoutFilter成功执行之后被调用，来重定向或者转发到合适的目的地上
                    .invalidateHttpSession(true)//指定在注销的时候是否销毁 HttpSession 。默认为True
                    .addLogoutHandler(myLogoutHandler())//添加一个 LogoutHandler。默认情况下， SecurityContextLogoutHandler 被作为最后一个 LogoutHandler
                    /*.deleteCookies("")*///允许指定当注销成功时要移除的cookie的名称
                .and()
                .rememberMe()//登录后记住用户，下次自动登录,数据库中必须存在名为persistent_logins的表
                .tokenValiditySeconds(60*60*24*7)//一周
                .tokenRepository(tokenRepository())//指定记住登录信息所使用的数据源
                .and()
                .exceptionHandling().accessDeniedPage("/401");

        // session管理
        // session失效后跳转
        http.sessionManagement().invalidSessionUrl("/login");

        // 只允许一个用户登录,如果同一个账户两次登录,那么第一个账户将被踢下线,跳转到登录页面
        http.sessionManagement().maximumSessions(1).expiredUrl("/login");
    }


    /**
     * description: 配置验证的用户信息源和密码加密的策略
     * 在 oauth2 中，只有配置了 AuthenticationManager，密码验证才会开启
     * <br /><br />
     * create by mace on 2018/7/17 14:06.
     * @param auth
     * @return: void
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 自定义UserDetailsService,设置加密算法
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

        // 不删除凭据，以便记住用户
        auth.eraseCredentials(false);
    }


    /**
     * description: 配置了验证管理的 Bean
     * <br /><br />
     * create by mace on 2018/7/17 14:09.
     * @param
     * @return: org.springframework.security.authentication.AuthenticationManager
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
