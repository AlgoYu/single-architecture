package cn.machine.geek.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: MachineGeek
 * @Description: Token工具类
 * @Email: 794763733@qq.com
 * @Date: 2021/1/6
 */
@Component
public class TokenManager {
    // AccessToken 过期时间 默认8小时
    @Value(value = "${token.accessTokenExpire:28800}")
    private long accessTokenExpire;
    // RefreshToken 过期时间 默认3天
    @Value(value = "${token.refreshTokenExpire:259200}")
    private long refreshTokenExpire;

    public static final String TOKEN_HEAD = "Token";
    public static final String ACCESS_TOKEN_KEY = "AccessToken";
    public static final String REFRESH_TOKEN_KEY = "RefreshToken";
    // Redis
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
    * @Author: MachineGeek
    * @Description: 判断是否有访问Token
    * @Date: 2021/1/6
     * @param token
    * @Return: boolean
    */
    public boolean hasAccessToken(String token){
        return redisTemplate.hasKey(ACCESS_TOKEN_KEY + token);
    }

    /**
    * @Author: MachineGeek
    * @Description: 判断是否有刷新Token
    * @Date: 2021/1/6
     * @param token
    * @Return: boolean
    */
    public boolean hasRefreshToken(String token){
        return redisTemplate.hasKey(REFRESH_TOKEN_KEY + token);
    }

    /**
    * @Author: MachineGeek
    * @Description: 创建访问Token
    * @Date: 2021/1/6
     * @param userDetails
    * @Return: java.lang.String
    */
    public String createAccessToken(UserDetails userDetails){
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(ACCESS_TOKEN_KEY + token,userDetails,accessTokenExpire, TimeUnit.SECONDS);
        return token;
    }

    /**
    * @Author: MachineGeek
    * @Description: 创建刷新Token
    * @Date: 2021/1/6
     * @param
    * @Return: java.lang.String
    */
    public String createRefreshToken(UserDetails userDetails){
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(REFRESH_TOKEN_KEY + token, userDetails, refreshTokenExpire, TimeUnit.SECONDS);
        return token;
    }

    /**
    * @Author: MachineGeek
    * @Description: 删除访问Token
    * @Date: 2021/1/6
     * @param token
    * @Return: void
    */
    public void deleteAccessToken(String token){
        redisTemplate.delete(ACCESS_TOKEN_KEY + token);
    }

    /**
    * @Author: MachineGeek
    * @Description: 删除访问Token
    * @Date: 2021/1/6
     * @param token
    * @Return: void
    */
    public void deleteRefreshToken(String token){
        redisTemplate.delete(REFRESH_TOKEN_KEY + token);
    }

    /**
    * @Author: MachineGeek
    * @Description: 获取信息通过访问
    * @Date: 2021/1/6
     * @param token
    * @Return: org.springframework.security.core.userdetails.UserDetails
    */
    public UserDetails getByAccessToken(String token){
        return (UserDetails) redisTemplate.opsForValue().get(ACCESS_TOKEN_KEY + token);
    }

    /**
     * @Author: MachineGeek
     * @Description: 获取ID通过刷新Token
     * @Date: 2021/1/6
     * @param token
     * @Return: org.springframework.security.core.userdetails.UserDetails
     */
    public UserDetails getByRefreshToken(String token){
        return (UserDetails) redisTemplate.opsForValue().get(REFRESH_TOKEN_KEY + token);
    }
}