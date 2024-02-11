package com.zgy.hjy_community.system.domain.dto;

import com.zgy.hjy_community.system.domain.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/2/5 20:59
 * @description LoginUserDto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto implements UserDetails {
    SysUser user;

    /**
     * 用户角色
     */
    List<String> roles;

    /**
     * 用户权限
     */
    List<String> perms;

    public LoginUserDto(SysUser loginUser) {
        this.user = loginUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUserName();
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
        return true;
    }
}
