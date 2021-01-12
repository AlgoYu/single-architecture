package cn.machine.geek.controller;

import cn.machine.geek.common.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

    @GetMapping(value = "/get")
    public R get(){
        log.info("访问!");
        Map<String,Object> map = new HashMap<>();
        map.put("name","haha");
        map.put("power",123456l);
        map.put("age",5);
        map.put("date", LocalDateTime.now());
        return R.ok(map);
    }
}
