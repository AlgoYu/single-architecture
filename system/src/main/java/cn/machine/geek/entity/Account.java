package cn.machine.geek.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: MachineGeek
 * @Description: 账户
 * @Email: 794763733@qq.com
 * @Date: 2021/1/6
 */
@Data
public class Account{
    private Long id;
    private String name;
    private String password;
    private String ip;
    private Boolean disable;
    private Long version;
    private LocalDateTime lastLogin;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}