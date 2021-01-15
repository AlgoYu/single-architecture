package cn.machine.geek.controller;

import cn.machine.geek.common.R;
import cn.machine.geek.util.TokenManager;
import com.alibaba.druid.util.StringUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: MachineGeek
 * @Description: Token控制器
 * @Email: 794763733@qq.com
 * @Date: 2021/1/13
 */
@Api(tags = "账户接口")
@RestController
@RequestMapping("/api/token")
public class TokenController {
    @Autowired
    private TokenManager tokenManager;

    @GetMapping(value = "refreshToken")
    public R refreshToken(@RequestParam(value = "/refreshToken") String refreshToken){
        if(!StringUtils.isEmpty(refreshToken)){
            UserDetails userDetails = tokenManager.getByRefreshToken(refreshToken);
            if(userDetails != null){
                tokenManager.deleteRefreshToken(refreshToken);
                Map<String,Object> map = new HashMap<>();
                map.put("accessToken",tokenManager.createAccessToken(userDetails));
                map.put("refreshToken",tokenManager.createRefreshToken(userDetails));
                return R.ok(map);
            }
        }
        return R.fail("未知的Token");
    }
}
