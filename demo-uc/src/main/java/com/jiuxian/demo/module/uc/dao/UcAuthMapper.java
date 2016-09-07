package com.jiuxian.demo.module.uc.dao;

import com.jiuxian.demo.module.uc.entity.UcAuth;
import com.jiuxian.demo.module.uc.entity.UcLoginRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UcAuthMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uc_auth
     *
     * @mbg.generated Tue Aug 23 15:48:40 CST 2016
     */
    int deleteByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uc_auth
     *
     * @mbg.generated Tue Aug 23 15:48:40 CST 2016
     */
    int insert(UcAuth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uc_auth
     *
     * @mbg.generated Tue Aug 23 15:48:40 CST 2016
     */
    int insertSelective(UcAuth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uc_auth
     *
     * @mbg.generated Tue Aug 23 15:48:40 CST 2016
     */
    UcAuth selectByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uc_auth
     *
     * @mbg.generated Tue Aug 23 15:48:40 CST 2016
     */
    int updateByPrimaryKeySelective(UcAuth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uc_auth
     *
     * @mbg.generated Tue Aug 23 15:48:40 CST 2016
     */
    int updateByPrimaryKey(UcAuth record);

    List<UcAuth> selectByPrimaryKeys(List<Integer> pkidList);

    int countByProperties(@Param("params") Map<String, Object> properties);

    List<UcAuth> findByProperties(@Param("params") Map<String, Object> properties, @Param("startRow") int startRow, @Param("pageSize") int pageSize);
}