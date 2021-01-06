package cn.machine.geek.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();
        customAuthenticationFilter.setFilterProcessesUrl("/login");
        try {
            customAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
        http
                // 关闭CSRF攻击，开启跨域。
                .csrf().disable().cors().and()
                // 设置注销路径
                .logout()
                .logoutUrl("/loginout")
                .and()
                // 设置验证路径
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                // 替换自定义登录逻辑
                .addFilterAfter(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}