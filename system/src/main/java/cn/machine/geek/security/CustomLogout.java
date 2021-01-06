package cn.machine.geek.security;

import cn.machine.geek.common.R;
import cn.machine.geek.util.TokenManager;
import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: MachineGeek
 * @Description: 自定义注销
 * @Email: 794763733@qq.com
 * @Date: 2021/1/6
 */
public class CustomLogout implements LogoutSuccessHandler {

    private TokenManager tokenManager;
    private ObjectMapper objectMapper;

    public CustomLogout(TokenManager tokenManager, ObjectMapper objectMapper) {
        this.tokenManager = tokenManager;
        this.objectMapper = objectMapper;
    }

    /**
    * @Author: MachineGeek
    * @Description: 注销删除所有Token
    * @Date: 2021/1/6
     * @param httpServletRequest
     * @param httpServletResponse
     * @param authentication
    * @Return: void
    */
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String accessToken = httpServletRequest.getHeader(TokenManager.ACCESS_TOKEN_KEY);
        String refreshToken = httpServletRequest.getParameter("refreshToken");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter writer = httpServletResponse.getWriter();
        String json = "";
        if(StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(refreshToken) || !tokenManager.hasAccessToken(accessToken) || !tokenManager.hasRefreshToken(refreshToken)){
            json = objectMapper.writeValueAsString(R.fail("注销失败"));
        }else{
            tokenManager.deleteAccessToken(accessToken);
            tokenManager.deleteRefreshToken(refreshToken);
            json = objectMapper.writeValueAsString(R.ok("注销成功"));
        }
        writer.print(json);
        writer.flush();
        writer.close();
    }
}
