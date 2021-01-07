package cn.machine.geek.test;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: MachineGeek
 * @Description:
 * @Email: 794763733@qq.com
 * @Date: 2021/1/7
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
        log.info(IdWorker.getIdStr());
    }
}
