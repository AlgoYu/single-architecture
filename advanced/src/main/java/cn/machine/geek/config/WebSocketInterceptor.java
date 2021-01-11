package cn.machine.geek.config;

import cn.machine.geek.security.CustomUserDetail;
import cn.machine.geek.util.TokenManager;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @Author: MachineGeek
 * @Description: WebSocket拦截器
 * @Email: 794763733@qq.com
 * @Date: 2020/11/11
 */
@Slf4j
@Component
public class WebSocketInterceptor implements HandshakeInterceptor {
    @Autowired
    private TokenManager tokenManager;

    /**
    * @Author: MachineGeek
    * @Description: 检查Token判断是否允许连接
    * @Date: 2021/1/11
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
    * @Return: boolean
    */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
        String token = servletServerHttpRequest.getServletRequest().getParameter(TokenManager.TOKEN_HEAD);
        if (!StringUtil.isNullOrEmpty(token)) {
            UserDetails userDetails = tokenManager.getByAccessToken(token);
            if(userDetails != null){
                attributes.put("id",((CustomUserDetail)userDetails).getId());
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
