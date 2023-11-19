package com.microwarp.warden.cloud.facade.user.domain.dto;

import com.microwarp.warden.cloud.common.core.enums.GenderEnum;

import java.util.List;

/**
 * dto - user
 */
public class CreateUserDTO  {
    /** 帐号 */
    private String uid;
    /** 密码 */
    private String pwd;
    /** 昵称 */
    private String nickName;
    /** 姓名 */
    private String realName;
    /** 性别 */
    private GenderEnum gender;
    /** 头像 */
    private String face;
    /** 手机号 */
    private String mobile;
    /** 电子邮箱 */
    private String email;
    /** 禁用 */
    private Boolean disabled;
    /** 权限 */
    private List<String> permissionValues;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public List<String> getPermissionValues() {
        return permissionValues;
    }

    public void setPermissionValues(List<String> permissionValues) {
        this.permissionValues = permissionValues;
    }
}
