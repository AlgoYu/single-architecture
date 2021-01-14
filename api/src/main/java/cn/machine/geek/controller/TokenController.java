package cn.machine.geek.controller;

import cn.machine.geek.service.AccountService;
import cn.machine.geek.util.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: MachineGeek
 * @Description: Token控制器
 * @Email: 794763733@qq.com
 * @Date: 2021/1/13
 */
//@Api(tags = "账户接口")
//@RestController
//@RequestMapping("/api/token")
public class TokenController {
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private AccountService accountService;
//    private AuthenticationManager authenticationManager;
    
//    @GetMapping(value = "refreshToken")
//    public R refreshToken(@RequestParam(value = "/refreshToken") String refreshToken){
//        if(!StringUtils.isEmpty(refreshToken)){
//            if(tokenManager.hasRefreshToken(refreshToken)){
//                Account account = accountService.getById(tokenManager.getByRefreshToken(refreshToken));
//                if(account != null){
//                    Authentication
//                    authenticationManager.authenticate()
//                }
//            }
//        }
//        return R.fail("未知的Token");
//    }
}
