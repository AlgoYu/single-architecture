package cn.machine.geek.controller;

import cn.machine.geek.common.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: MachineGeek
 * @Description:
 * @Email: 794763733@qq.com
 * @Date: 2021/1/7
 */
@Slf4j
@RequestMapping(value = "/api/test")
@RestController
public class TestController {
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(value = "/receiveJSON")
    public R receiveJSON(@RequestBody Object object){
        try {
            log.info(objectMapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return R.ok();
    }
}
