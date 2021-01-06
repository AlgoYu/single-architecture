package cn.machine.geek.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: MachineGeek
 * @Description: 角色
 * @Email: 794763733@qq.com
 * @Date: 2021/1/6
 */
@Data
public class Role {
    private Long id;
    private String name;
    private String key;
    private Long version;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
