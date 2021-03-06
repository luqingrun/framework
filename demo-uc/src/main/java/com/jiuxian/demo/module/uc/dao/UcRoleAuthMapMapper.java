package com.jiuxian.demo.module.uc.dao;

import com.jiuxian.demo.module.uc.entity.UcRole;
import com.jiuxian.demo.module.uc.entity.UcRoleAuthMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UcRoleAuthMapMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uc_role_auth_map
     *
     * @mbg.generated Tue Aug 23 15:48:40 CST 2016
     */
    int deleteByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uc_role_auth_map
     *
     * @mbg.generated Tue Aug 23 15:48:40 CST 2016
     */
    int insert(UcRoleAuthMap record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uc_role_auth_map
     *
     * @mbg.generated Tue Aug 23 15:48:40 CST 2016
     */
    int insertSelective(UcRoleAuthMap record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uc_role_auth_map
     *
     * @mbg.generated Tue Aug 23 15:48:40 CST 2016
     */
    UcRoleAuthMap selectByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uc_role_auth_map
     *
     * @mbg.generated Tue Aug 23 15:48:40 CST 2016
     */
    int updateByPrimaryKeySelective(UcRoleAuthMap record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uc_role_auth_map
     *
     * @mbg.generated Tue Aug 23 15:48:40 CST 2016
     */
    int updateByPrimaryKey(UcRoleAuthMap record);

    List<UcRoleAuthMap> selectByPrimaryKeys(List<Integer> pkidList);

    int countByProperties(@Param("params") Map<String, Object> properties);

    List<UcRoleAuthMap> findByProperties(@Param("params") Map<String, Object> properties, @Param("startRow") int startRow, @Param("pageSize") int pageSize);
}