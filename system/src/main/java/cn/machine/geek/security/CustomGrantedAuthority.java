package cn.machine.geek.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * @Author: MachineGeek
 * @Description: 自定义权力
 * @Email: 794763733@qq.com
 * @Date: 2021/1/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomGrantedAuthority implements GrantedAuthority {
    private String name;
    private String authority;
    private String uri;
    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof CustomGrantedAuthority) {
            return authority.equals(((CustomGrantedAuthority) obj).authority);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return authority.hashCode();
    }

    @Override
    public String toString() {
        return authority;
    }
}
