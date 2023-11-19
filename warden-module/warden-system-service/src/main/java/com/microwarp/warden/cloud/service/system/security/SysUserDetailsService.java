package com.microwarp.warden.cloud.service.system.security;

import com.microwarp.warden.cloud.common.security.util.WebUtil;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysRoleDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysUserDetailsDTO;
import com.microwarp.warden.cloud.service.system.constant.SecurityConstant;
import com.microwarp.warden.cloud.service.system.service.SysPermissionService;
import com.microwarp.warden.cloud.service.system.service.SysUserLockService;
import com.microwarp.warden.cloud.service.system.service.SysUserService;
import com.microwarp.warden.cloud.service.system.util.SysSecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * security - user-service
 * @author zhouwenqi
 */
@Service
public class SysUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(SysUserDetailsService.class);
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private SysUserLockService sysUserLockService;

    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserDetailsDTO sysUserDetailsDTO = sysUserService.findDetailsByUid(username);
        String ip = WebUtil.getIpAddr();
        if(null == sysUserDetailsDTO){
            logger.error("用户不存在：{}",username);
            return null;
        }
        // “保留超管用户” 或 “超管角色”
        Set<String> roleValues = sysUserDetailsDTO.getRoles().stream().map(SysRoleDTO::getValue).collect(Collectors.toSet());
        if(sysUserDetailsDTO.getUid().equals(SecurityConstant.RESERVE_ROOT_USER_NAME) || SysSecurityUtil.hasAnyAuthority(roleValues, SecurityConstant.ROLE_ROOT_VALUE)){
            sysUserDetailsDTO.setPermissions(new HashSet<>(sysPermissionService.findAll()));
        }
        SecurityUser securityUser = new SecurityUser(sysUserDetailsDTO);
        securityUser.setAccountNonLocked(!sysUserLockService.isLocked(sysUserDetailsDTO.getId(),ip));
        return securityUser;
    }
}
