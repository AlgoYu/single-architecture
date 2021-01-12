package cn.machine.geek.security;

import cn.machine.geek.common.R;
import cn.machine.geek.util.TokenManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: MachineGeek
 * @Description: 自定义验证逻辑
 * @Email: 794763733@qq.com
 * @Date: 2021/1/6
 */
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private TokenManager tokenManager;
    private ObjectMapper objectMapper;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, TokenManager tokenManager, ObjectMapper objectMapper) {
        super(authenticationManager);
        this.tokenManager = tokenManager;
        this.objectMapper = objectMapper;
        setFilterProcessesUrl("/login");
    }

    /**
    * @Author: MachineGeek
    * @Description: 从请求中获取数据并创建认证对象
    * @Date: 2021/1/6
     * @param request
     * @param response
    * @Return: org.springframework.security.core.Authentication
    */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 判断是否是JSON
        if(MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())){
            // 构建认证对象
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;
            try {
                // 从请求中获取数据并转换为JSON
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
                StringBuilder stringBuilder = new StringBuilder();
                String temp = null;
                while ((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp);
                }
                JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                // 赋值认证对象
                usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(jsonObject.getString("username"),jsonObject.getString("password"));
                return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
            } catch (Exception e) {
                e.printStackTrace();
                // 赋值出错则为空
            }
        }
        // 不是JSON调用父类FORM表单
        return super.attemptAuthentication(request, response);
    }

    /**
    * @Author: MachineGeek
    * @Description: 认证成功回调
    * @Date: 2021/1/6
     * @param request
     * @param response
     * @param chain
     * @param authResult
    * @Return: void
    */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        CustomUserDetail CustomUserDetail = (CustomUserDetail) authResult.getPrincipal();
        CustomUserDetail.setPassword(null);
        Map<String,Object> map = new HashMap<>();
        map.put("id",CustomUserDetail.getId());
        map.put("username",CustomUserDetail.getUsername());
        map.put("accessToken",tokenManager.createAccessToken(CustomUserDetail));
        map.put("refreshToken",tokenManager.createRefreshToken(CustomUserDetail.getId()));
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        String json = objectMapper.writeValueAsString(R.ok(map));
        writer.print(json);
        writer.flush();
        writer.close();
    }

    /**
    * @Author: MachineGeek
    * @Description: 认证失败回调
    * @Date: 2021/1/6
     * @param request
     * @param response
     * @param failed
    * @Return: void
    */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        String json = objectMapper.writeValueAsString(R.fail(failed.getMessage()));
        writer.print(json);
        writer.flush();
        writer.close();
    }
}
