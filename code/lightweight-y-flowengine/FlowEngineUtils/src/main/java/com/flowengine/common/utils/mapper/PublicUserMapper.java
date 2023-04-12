package com.flowengine.common.utils.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flowengine.common.utils.entity.PublicUserEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author yangzl 2021.07.05
 * @version 1.00.00
 * @Description:
 * @Copyright:
 * @Company:
 * @history:
 */
@Repository
public interface PublicUserMapper extends BaseMapper<PublicUserEntity> {

    /**
     * 根据accessToken获取signToken
     * @param accessToken
     * @return
     */
    @Select("select a.sign_token signToken from public_user_tbl a where a.access_token = #{accessToken}")
    public String getSignTokenByAccessToken(@Param("accessToken") String accessToken);

    /**
     * 根据主键获取用户名
     * @param userOpId
     * @return
     */
    @Select("select a.user_name from public_user_tbl a where a.op_id = #{userOpId}")
    public String getUserNameWithOpId(@Param("userOpId") String userOpId);

    /**
     *
     * @return
     */
    @Select("select a.op_id value, a.user_name label from public_user_tbl a where a.is_stop = 1 and a.user_category in(2,3,4)")
    public List<Map<String, Object>> getUserCombox();

    /**
     * 根据userOpId删除角色关系表数据
     * @param userOpId
     * @return
     */
    @Delete("delete from public_user_role_grant where user_op_id=#{userOpId} ")
    public int deleteUserRoleGrant(@Param("userOpId") String userOpId);

    /**
     * 插入用户角色管理表的数据
     * @param userOpId
     * @param roleId
     * @return
     */
    @Insert("insert into public_user_role_grant(user_op_id, role_id) values(#{userOpId}, #{roleId})")
    public int insertUserRoleGrant(@Param("userOpId") String userOpId, @Param("roleId") String roleId);

    /**
     * 根据用户opId查询角色id
     * @param opId
     * @return
     */
    @Select("SELECT a.role_id \"roleId\" FROM public_user_role_grant a  where a.user_op_id = #{opId}")
    public List<Map<String, Object>> getRoleIds(@Param("opId") String opId);
}
