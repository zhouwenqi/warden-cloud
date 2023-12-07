package com.microwarp.warden.cloud.service.system.dao.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.microwarp.warden.cloud.common.database.domain.BaseDaoImpl;
import com.microwarp.warden.cloud.service.system.dao.SysUserBlipDao;
import com.microwarp.warden.cloud.service.system.domain.entity.SysUserBlip;
import com.microwarp.warden.cloud.service.system.mapper.SysUserBlipMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * dao - 系统用户标记 - impl
 * @author zhouwenqi
 */
@Repository
public class SysUserBlipDaoImpl extends BaseDaoImpl<SysUserBlipMapper,SysUserBlip> implements SysUserBlipDao {
    /**
     * 查询一条用户标记信息
     * @param userId 用户id
     * @param ip ip地址
     * @return
     */
    public SysUserBlip find(Long userId,String ip){
        if(null == userId || StringUtils.isBlank(ip)){
            return null;
        }
        QueryWrapper<SysUserBlip> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("ip",ip);
        queryWrapper.last("limit 1");
        return baseMapper.selectOne(queryWrapper);
    }
}
