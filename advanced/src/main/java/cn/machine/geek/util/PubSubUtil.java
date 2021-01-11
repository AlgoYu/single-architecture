package cn.machine.geek.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: MachineGeek
 * @Description: Redis订阅发布实现类
 * @Email: 794763733@qq.com
 * @Date: 2020/11/12
 */
@Component
public class PubSubUtil {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
    * @Author: MachineGeek
    * @Description: 向Redis频道发布信息
    * @Date: 2020/11/12
     * @param content
    * @Return: void
    */
    public void publish(String key,String content) {
        redisTemplate.convertAndSend(key,content);
    }
}
