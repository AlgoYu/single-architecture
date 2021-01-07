package cn.machine.geek.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId(value = "`id`",type= IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "`name`")
    private String name;

    @TableField(value = "`password`")
    private String password;

    @TableField(value = "`ip`")
    private String ip;

    @TableField(value = "`enable`")
    private Boolean enable;

    @TableField(value = "`version`")
    private Long version;

    @TableField(value = "`lastLogin`")
    private LocalDateTime lastLogin;

    @TableField(value = "`createTime`")
    private LocalDateTime createTime;

    @TableField(value = "`updateTime`")
    private LocalDateTime updateTime;
}