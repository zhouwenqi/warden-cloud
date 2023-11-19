package com.microwarp.warden.cloud.service.user.domain.convert;

import com.microwarp.warden.cloud.facade.user.domain.dto.CreateUserDTO;
import com.microwarp.warden.cloud.facade.user.domain.dto.UpdateUserDTO;
import com.microwarp.warden.cloud.facade.user.domain.dto.UserDTO;
import com.microwarp.warden.cloud.facade.user.domain.dto.UserPasswordDTO;
import com.microwarp.warden.cloud.service.user.domain.entity.User;
import com.microwarp.warden.cloud.service.user.domain.vo.RegisterUserRequest;
import com.microwarp.warden.cloud.service.user.domain.vo.UpdateUserRequest;
import com.microwarp.warden.cloud.service.user.domain.vo.UserPasswordRequest;
import com.microwarp.warden.cloud.service.user.domain.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

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
    List<UserDTO> usersToUserDTOs(List<User> users);
    User createUserDtoToUser(CreateUserDTO createUserDTO);
    User updateUserDtoToUser(UpdateUserDTO updateUserDTO);

    /** vo - dto */
    UserVO userToUserVo(User user);
    UserVO userDtoToUserVo(UserDTO userDTO);
    CreateUserDTO registerUserRequestToCreateUserDTO(RegisterUserRequest registerUserRequest);
    UpdateUserDTO updateUserRequestToUpdateUserDTO(UpdateUserRequest updateUserRequest);
    UserPasswordDTO userPasswordRequestToUserPasswordDTO(UserPasswordRequest userPasswordRequest);
}
