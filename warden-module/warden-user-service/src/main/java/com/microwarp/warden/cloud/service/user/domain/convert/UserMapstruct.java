package com.microwarp.warden.cloud.service.user.domain.convert;

import com.microwarp.warden.cloud.facade.user.domain.dto.UserDTO;
import com.microwarp.warden.cloud.facade.user.domain.dto.UserDetailsDTO;
import com.microwarp.warden.cloud.service.user.domain.entity.User;
import com.microwarp.warden.cloud.service.user.domain.vo.UserRegisterRequest;
import com.microwarp.warden.cloud.service.user.domain.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * convert - 用户
 * @author zhouwenqi
 */
@Mapper
public interface UserMapstruct {
    UserMapstruct Instance = Mappers.getMapper(UserMapstruct.class);

    /** entity - dto */
    UserDTO userToUserDto(User user);
    User userDtoToUser(UserDTO userDTO);

    /** vo - dto */
    UserVO userToUserVo(User user);
    UserVO userDtoToUserVo(UserDTO userDTO);
    UserDTO userRegisterRequestToSysUserDTO(UserRegisterRequest userRegisterRequest);

}
