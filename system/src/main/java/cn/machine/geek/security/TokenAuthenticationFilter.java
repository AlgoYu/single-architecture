package cn.machine.geek.security;

import cn.machine.geek.util.TokenManager;
import io.netty.util.internal.StringUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: MachineGeek
 * @Description: Token拦截器
 * @Date: 2021/1/6
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private TokenManager tokenManager;

    public TokenAuthenticationFilter(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    /**
     * @param request
     * @param response
     * @param filterChain
     * @Author: MachineGeek
     * @Description: 拦截并从Redis获取Token数据
     * @Date: 2020/10/17
     * @Return void
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(TokenManager.TOKEN_HEAD);
        if (!StringUtil.isNullOrEmpty(token)) {
            UserDetails userDetails = tokenManager.getByAccessToken(token);
            if(userDetails != null){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
