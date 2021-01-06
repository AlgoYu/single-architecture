package cn.machine.geek.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @Author: MachineGeek
 * @Description: 账户
 * @Email: 794763733@qq.com
 * @Date: 2021/1/6
 */
@Data
public class Account implements UserDetails {
    private Long id;
    private String name;
    private String password;
    private String ip;
    private Boolean disable;
    private Long version;
    private LocalDateTime lastLogin;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Collection<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !disable;
    }
}