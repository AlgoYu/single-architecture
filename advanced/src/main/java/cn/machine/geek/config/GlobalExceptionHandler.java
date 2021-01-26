package cn.machine.geek.config;

import cn.machine.geek.common.R;
import cn.machine.geek.entity.SystemException;
import cn.machine.geek.service.SystemExceptionService;
import cn.machine.geek.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @Author: MachineGeek
 * @Description: 全局异常处理
 * @Date: 2020/10/20
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private SystemExceptionService systemExceptionService;

    /**
    * @Author: MachineGeek
    * @Description: 全局统一异常处理
    * @Date: 12:04 下午
     * @param httpServletRequest
     * @param e
    * @Return: cn.machine.geek.dto.R
    */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public R exceptionHandler(HttpServletRequest httpServletRequest, Exception e, Authentication authentication){
        // 构建异常信息
        SystemException systemException = new SystemException();
        systemException.setUri(httpServletRequest.getRequestURI());
        systemException.setIp(HttpUtil.getIpAddr(httpServletRequest));
        systemException.setMethod(httpServletRequest.getMethod());
        systemException.setExceptionClass(e.getClass().getName());
        systemException.setExceptionMessage(e.getMessage());
        if(httpServletRequest.getMethod().equals("GET") || httpServletRequest.getMethod().equals("DELETE")){
            systemException.setParameter(HttpUtil.getParameter(httpServletRequest));
        }else{
            systemException.setParameter(HttpUtil.getBody(httpServletRequest));
        }
        systemException.setCreateTime(LocalDateTime.now());
        // 插入数据库
        systemExceptionService.save(systemException);
        // 返回异常信息
        return R.fail("系统错误");
    }
}
