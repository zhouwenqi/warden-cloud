package com.microwarp.warden.cloud.facade.user.domain.dto;

import com.microwarp.warden.cloud.common.core.enums.GenderEnum;
import com.microwarp.warden.cloud.common.core.pageing.BasicSearchDTO;

/**
 * dto - 用户查询
 */
public class UserSearchDTO extends BasicSearchDTO {
    /** 性别 */
    private GenderEnum[] genderEnum;
    /** 禁用 */
    private Boolean[] Disabled;

    public GenderEnum[] getGenderEnum() {
        return genderEnum;
    }

    public void setGenderEnum(GenderEnum[] genderEnum) {
        this.genderEnum = genderEnum;
    }

    public Boolean[] getDisabled() {
        return Disabled;
    }

    public void setDisabled(Boolean[] disabled) {
        Disabled = disabled;
    }
}
