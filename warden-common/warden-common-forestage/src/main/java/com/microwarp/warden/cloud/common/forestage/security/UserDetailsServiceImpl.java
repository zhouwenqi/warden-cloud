package com.microwarp.warden.cloud.common.forestage.security;

import com.microwarp.warden.cloud.facade.user.domain.dto.UserDTO;
import com.microwarp.warden.cloud.facade.user.domain.dto.UserDetailsDTO;
import com.microwarp.warden.cloud.facade.user.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 * security - user-service
 * @author zhouwenqi
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);
    @Autowired
    private IUserService iUserService;

    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = iUserService.findByUid(username);
        if(null == userDTO){
            logger.error("用户不存在：{}",username);
            return null;
        }
        return new SecurityUser(userDTO);
    }
}
