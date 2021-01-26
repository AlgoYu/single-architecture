package cn.machine.geek.test;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: MachineGeek
 * @Description:
 * @Email: 794763733@qq.com
 * @Date: 2021/1/7
 */
@SpringBootTest
@Slf4j
public class DatabaseTest {

    @Test
    public void name(){
        log.info(IdWorker.getIdStr());
    }
}
