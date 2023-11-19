package com.microwarp.warden.cloud.common.forestage.security;

import com.microwarp.warden.cloud.common.core.security.UserType;
import com.microwarp.warden.cloud.common.forestage.util.SecurityUtil;
import com.microwarp.warden.cloud.common.security.authenticator.SecurityAuthority;
import com.microwarp.warden.cloud.facade.user.domain.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * security - user
 * @author zhouwenqi
 */
public class SecurityUser implements UserDetails {
    private static final long serialVersionUID = 8907404746574124539L;
    private String id;
    private String username;
    private String password;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;
    private Boolean enabled;
    private UserType userType;
    private List<SecurityAuthority> authorities = new ArrayList<>();
    private UserDTO user;

    public SecurityUser(UserDTO userDTO){
        if(null != userDTO){
            this.user = userDTO;
            this.credentialsNonExpired = true;
            this.accountNonExpired = true;
            this.accountNonLocked = true;
            this.enabled = !userDTO.getDisabled();
            this.username = userDTO.getUid();
            this.password = userDTO.getPwd();
            this.id = userDTO.getId().toString();
            this.setAuthorities(SecurityUtil.getWardenAuthentication(userDTO.getPermissionValues()));
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
