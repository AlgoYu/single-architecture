package cn.machine.geek.test;

import cn.machine.geek.mapper.DatabaseMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: MachineGeek
 * @Description:
 * @Email: 794763733@qq.com
 * @Date: 2021/1/7
 */
@SpringBootTest
public class DatabaseTest {
    @Autowired
    private DatabaseMapper databaseMapper;

    @Test
    public void name(){
        System.out.println(databaseMapper.getCurrentDatabase());
    }
}
