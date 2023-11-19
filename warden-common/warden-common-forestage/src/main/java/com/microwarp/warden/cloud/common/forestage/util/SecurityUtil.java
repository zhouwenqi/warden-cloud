package com.microwarp.warden.cloud.common.forestage.util;

import com.microwarp.warden.cloud.common.security.authenticator.SecurityAuthority;
import com.microwarp.warden.cloud.common.security.authenticator.WardenAuthentication;
import com.microwarp.warden.cloud.facade.user.domain.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Security - util
 */
public class SecurityUtil {
    public static List<SecurityAuthority> getWardenAuthentication(List<String> permissionValues){
        List<SecurityAuthority> list = new ArrayList<>();
        for(String value:permissionValues){
            SecurityAuthority securityAuthority = new SecurityAuthority();
            securityAuthority.setAuthority(value);
            list.add(securityAuthority);
        }
        return list;
    }
    /**
     * 获取当前security认证系统用户
     * @return
     */
    public static UserDTO getCurrentUser(){
        WardenAuthentication wardenAuthentication = getAuthentication();
        return null != wardenAuthentication ? (UserDTO)wardenAuthentication.getPrincipal(): null;
    }

    /**
     * 获取当前security认证
     * @return
     */
    public static WardenAuthentication getAuthentication(){
        return (WardenAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取当前security认证权限列表
     * @return
     */
    public static Collection<GrantedAuthority> getAuthoritys(){
        WardenAuthentication wardenAuthentication = getAuthentication();
        return null != wardenAuthentication ? wardenAuthentication.getAuthorities() : new ArrayList<>();
    }

    /**
     * 获取当前security认证权限值数组
     * @return
     */
    public static String[] getAuthorityArray(Collection<GrantedAuthority> authoritys){
        if(null == authoritys){
            return new String[0];
        }
        String[] arr = new String[authoritys.size()];
        Set<String> list = authoritys.stream().map(s -> s.getAuthority()).collect(Collectors.toSet());
        return list.toArray(arr);
    }

    /**
     * 获取当前security认证权限值数组
     * @return
     */
    public static String[] getAuthorityArray(){
        return getAuthorityArray(getAuthoritys());
    }

    /**
     * 权限匹配(只需匹配一个)
     * @param authoritys 权限值列表
     * @param hasAuthoritys 需要匹配的权限值列表
     * @return
     */
    public static boolean hasAnyAuthority(Set<String> authoritys, List<String> hasAuthoritys){
        if(null == authoritys || authoritys.size() < 1 || hasAuthoritys.size() < 1){
            return false;
        }
        Set<String> list = new HashSet<>(authoritys);
        for(String authority:hasAuthoritys){
            if(list.contains(authority)){
                return true;
            }
        }
        return false;
    }

    /**
     * 权限匹配(只需匹配一个)
     * @param authoritys 权限值列表
     * @param authority 需要匹配的权限值数组
     * @return
     */
    public static boolean hasAnyAuthority(Set<String> authoritys, String... authority){
        List<String> list = Arrays.asList(authority);
        return hasAnyAuthority(authoritys,list);
    }

    /**
     * 权限匹配(只需匹配一个)
     * @param authoritys 权限列表
     * @param authority 需要匹配的权限值数组
     * @return
     */
    public static boolean hasAnyAuthority(Collection<GrantedAuthority> authoritys, String... authority){
        Set<String> list = authoritys.stream().map(s -> s.getAuthority()).collect(Collectors.toSet());
        return hasAnyAuthority(list,authority);
    }

    /**
     * 当前权限匹配(只需匹配一个)
     * @param authority 需要匹配的权限值数组
     * @return
     */
    public static boolean hasAnyAuthority(String... authority){
        return hasAnyAuthority(getAuthoritys(),authority);
    }

    /**
     * 权限匹配(只需匹配一个)
     * @param permissionValues 权限数据列表
     * @param authority 需要匹配的权限值数组
     * @return
     */
    public static boolean hasAnyAuthority(List<String> permissionValues, String... authority){
        Set<String> list = new HashSet<>(permissionValues);
        return hasAnyAuthority(list,authority);
    }

    /**
     * 权限匹配
     * @param authoritys 权限值列表
     * @param authority 需要匹配的权限值数组
     * @return
     */
    public static boolean hasAuthority(Set<String> authoritys, String... authority){
        if(null == authoritys || authoritys.size() < 1 || authority.length < 1){
            return false;
        }
        Set<String> list = new HashSet<>(Arrays.asList(authority));
        int ex = 0;
        for(String auth:list){
            if(authoritys.contains(auth)){
                ex ++;
            }
        }

        return ex == list.size();
    }

    /**
     * 当前权限匹配
     * @param authoritys 权限列表
     * @param authority 需要匹配的权限值数组
     * @return
     */
    public static boolean hasAuthority(Collection<GrantedAuthority> authoritys, String... authority){
        Set<String> list = authoritys.stream().map(s -> s.getAuthority()).collect(Collectors.toSet());
        return hasAuthority(list,authority);
    }

    /**
     * 权限匹配
     * @param authority 需要匹配的权限值数组
     * @return
     */
    public static boolean hasAuthority(String... authority){
        return hasAuthority(getAuthoritys(),authority);
    }

    /**
     * 权限匹配
     * @param permissionValues 权限数据列表
     * @param authority 需要匹配的权限值数组
     * @return
     */
    public static boolean hasAuthority(List<String> permissionValues, String... authority){
        Set<String> list = new HashSet<>(permissionValues);
        return hasAuthority(list,authority);
    }

    /**
     * 根据用户id判断是否是当前授权用户
     * @param userId 用户id
     * @return
     */
    public static boolean isCurrentUser(Long userId){
        UserDTO sysUserDetailsDTO = getCurrentUser();
        if(null != sysUserDetailsDTO && null != userId){
            return sysUserDetailsDTO.getId() == userId;
        }
        return false;
    }
}
