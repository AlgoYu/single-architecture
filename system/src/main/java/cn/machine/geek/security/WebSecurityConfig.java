package cn.machine.geek.security;

import cn.machine.geek.util.TokenManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author: MachineGeek
 * @Description:
 * @Email: 794763733@qq.com
 * @Date: 2021/1/6
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private ObjectMapper objectMapper;
    /**
    * @Author: MachineGeek
    * @Description: 注册密码加密器
    * @Date: 2021/1/6
     * @param
    * @Return: org.springframework.security.crypto.password.PasswordEncoder
    */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
    * @Author: MachineGeek
    * @Description: 配置安全策略
    * @Date: 2021/1/6
     * @param http
    * @Return: void
    */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 创建自定义登录逻辑
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(),tokenManager,objectMapper);
        // 创建自定义的注销逻辑
        CustomLogout customLogout = new CustomLogout(tokenManager,objectMapper);
        // 创建自定义Token拦截
        TokenAuthenticationFilter tokenAuthenticationFilter = new TokenAuthenticationFilter(tokenManager);

        // 设置安全策略
        http
                // 替换自定义登录逻辑
                .addFilterAt(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // 增加Token过滤器
                .addFilterBefore(tokenAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
                // 设置注销路径
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(customLogout)
                .permitAll()
                // 关闭CSRF攻击，开启跨域。
                .and().csrf().disable().cors()
                // 设置验证路径
                .and().authorizeRequests()
                .antMatchers("/login")
                .permitAll()
                .anyRequest().authenticated()
                // 禁止使用Session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}