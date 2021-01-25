package cn.machine.geek.security;

import cn.machine.geek.util.TokenManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
    // 静态资源忽略路径
    private final String[] uris = new String[]{"/upload/**",
            "/static/**",
            "/doc.html",
            "/favicon.ico",
            "/webjars/**",
            "/swagger-resources",
            "/v2/api-docs",
            "/druid/**",
            "/websocket/**"
    };

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
    * @Description: 注册认证管理器
    * @Date: 2021/1/15
     * @param
    * @Return: org.springframework.security.authentication.AuthenticationManager
    */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
    * @Author: MachineGeek
    * @Description: 静态资源URI不拦截
    * @Date: 2021/1/7
     * @param web
    * @Return: void
    */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(uris);
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
        CustomTokenAuthenticationFilter customTokenAuthenticationFilter = new CustomTokenAuthenticationFilter(tokenManager);
        // 创建自定义未登录或身份过期处理
        CustomAuthenticationEntryPoint customAuthenticationEntryPoint = new CustomAuthenticationEntryPoint(objectMapper);
        // 创建自定义拒绝访问处理
        CustomAccessDeniedHandler customAccessDeniedHandler = new CustomAccessDeniedHandler(objectMapper);

        // 设置安全策略
        http
                .cors().and().csrf().disable()
                // 替换自定义登录逻辑
                .addFilterAt(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // 增加Token过滤器
                .addFilterBefore(customTokenAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                // 增加自定义未登录处理
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
                // 设置注销路径
                .and()
                .logout()
                .logoutUrl("/logout")
                // 设置注销处理
                .logoutSuccessHandler(customLogout)
                .permitAll()
                // 设置验证路径
                .and().authorizeRequests()
                .antMatchers("/login","/api/**")
                .permitAll()
                .anyRequest().authenticated()
                // 禁止使用Session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}