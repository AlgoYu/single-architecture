package cn.machine.geek.entity;

import lombok.Data;

/**
 * @Author: MachineGeek
 * @Description: 权力
 * @Email: 794763733@qq.com
 * @Date: 2021/1/6
 */
@Data
public class Authority {
    private Long id;
    private String name;
    private String key;
    private Long parentId;
}
