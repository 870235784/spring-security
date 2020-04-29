package com.tca.security.core.auth.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tca.security.core.auth.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author zhouan123
 * @since 2020-04-16
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 批量插入role-permission关联关系
     * @param roleId
     * @param perIds
     */
    void insertBatchRolePermissionRel(@Param("roleId") Long roleId, @Param("perIds") List<Long> perIds);

    /**
     * 根据roleId删除role-permission关联关系
     * @param roleId
     */
    void deleteRolePermissionRelByRoleId(@Param("roleId") Long roleId);

}
