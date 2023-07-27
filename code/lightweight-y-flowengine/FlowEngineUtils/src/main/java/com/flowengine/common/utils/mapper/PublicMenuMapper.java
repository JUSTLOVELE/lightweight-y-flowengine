package com.flowengine.common.utils.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flowengine.common.utils.entity.PublicMenuEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author yangzl 2023.06.29
 * @version 1.00.00
 * @Description:
 * @Copyright:
 * @Company:
 * @history:
 */
@Repository
public interface PublicMenuMapper extends BaseMapper<PublicMenuEntity> {

    /**
     * 查询父id下有几个子节点
     * @param parentId
     * @return
     */
    @Select("select count(*) from public_menu where parent_id = #{parentId}")
    public int queryCountWithParentId(@Param("parentId") String parentId);

    /**
     * 删除超级管理员的某个菜单id
     * @param menuId
     * @param roleId
     * @return
     */
    @Delete("delete from public_role_menu_grant where menu_id = #{menuId} and role_id = #{roleId}")
    public int deleteRoleMenu(@Param("menuId") String menuId,
                              @Param("roleId") String roleId);

    /**
     * 插入菜单角色表
     */
    @Insert("insert into public_role_menu_grant(menu_id, role_id) values (#{menuId}, #{roleId})")
    public int insertRoleMenu(@Param("menuId") String menuId,
                              @Param("roleId") String roleId);
}
