package com.microwarp.warden.cloud.service.system.domain.convert;

import com.microwarp.warden.cloud.facade.system.domain.dto.SysDeptDTO;
import com.microwarp.warden.cloud.facade.system.domain.dto.SysPostDTO;
import com.microwarp.warden.cloud.service.system.domain.entity.SysDept;
import com.microwarp.warden.cloud.service.system.domain.entity.SysPost;
import com.microwarp.warden.cloud.service.system.domain.vo.SysPostCreateRequest;
import com.microwarp.warden.cloud.service.system.domain.vo.SysPostUpdateRequest;
import com.microwarp.warden.cloud.service.system.domain.vo.SysPostVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * convert - 岗位
 * @author zhouwenqi
 */
@Mapper
public interface SysPostMapstruct {
    SysPostMapstruct Instance = Mappers.getMapper(SysPostMapstruct.class);

    /** entity - dto */
    SysPostDTO sysPostToSysPostDTO(SysPost sysPost);
    List<SysPostDTO> sysPostsToSysPostsDTOs(List<SysPost> sysPosts);
    SysPost sysPostDtoToSysPost(SysPostDTO sysPostDTO);

    /** vo - dto */
    SysPostVO sysPostDtoToSysPostVO(SysPostDTO sysPostDTO);
    List<SysPostVO> sysPostDtosToSysPostVOs(List<SysPostDTO> sysPostDTOs);
    SysPostDTO sysPostCreateRequestToSysPostDTO(SysPostCreateRequest sysPostRequest);
    SysPostDTO sysPostUpdateRequestToSysPostDTO(SysPostUpdateRequest sysPostRequest);

}
