package com.microwarp.warden.cloud.service.system.security;

import com.microwarp.warden.cloud.common.core.security.UserType;
import com.microwarp.warden.cloud.common.security.authenticator.SecurityAuthority;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysUserDetailsDTO;
import com.microwarp.warden.cloud.service.system.domain.convert.SysPermissionMapstruct;
import com.microwarp.warden.cloud.service.system.domain.convert.SysRoleMapstruct;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * security - sysuser
 * @author zhouwenqi
 */
public class SecurityUser implements UserDetails {
    private static final long serialVersionUID = 8284715895968142412L;
    private String id;
    private String username;
    private String password;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;
    private Boolean enabled;
    private UserType userType;
    private List<SecurityAuthority> authorities = new ArrayList<>();
    private SysUserDetailsDTO sysUser;

    public SecurityUser(SysUserDetailsDTO userDetailsDTO){
        if(null != userDetailsDTO){
            this.sysUser = userDetailsDTO;
            this.credentialsNonExpired = true;
            this.accountNonExpired = true;
            this.enabled = !userDetailsDTO.getDisabled();
            this.username = userDetailsDTO.getUid();
            this.password = userDetailsDTO.getPwd();
            this.id = userDetailsDTO.getId().toString();
            this.setAuthorities(SysPermissionMapstruct.Instance.toSecurityAuthoritys(sysUser.getPermissions()));
            // 将角色也加入权限例列中，角色值加上ROLE_标识(security角色签权需要)
            List<SecurityAuthority> sas = SysRoleMapstruct.Instance.toSecurityAuthoritys(sysUser.getRoles());
            sas.stream().forEach(s->s.setAuthority("ROLE_"+s.getAuthority()));
            this.getAuthorities().addAll(sas);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public List<SecurityAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<SecurityAuthority> authorities) {
        this.authorities = authorities;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public SysUserDetailsDTO getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUserDetailsDTO sysUser) {
        this.sysUser = sysUser;
    }
}
