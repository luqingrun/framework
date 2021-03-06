package com.jiuxian.demo.module.uc.dao;

import com.jiuxian.demo.module.uc.entity.UcLoginRecord;
import com.jiuxian.demo.module.uc.entity.UcRoleAuthMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UcLoginRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uc_login_record
     *
     * @mbg.generated Tue Aug 23 15:48:40 CST 2016
     */
    int deleteByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uc_login_record
     *
     * @mbg.generated Tue Aug 23 15:48:40 CST 2016
     */
    int insert(UcLoginRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uc_login_record
     *
     * @mbg.generated Tue Aug 23 15:48:40 CST 2016
     */
    int insertSelective(UcLoginRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uc_login_record
     *
     * @mbg.generated Tue Aug 23 15:48:40 CST 2016
     */
    UcLoginRecord selectByPrimaryKey(Integer pkid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uc_login_record
     *
     * @mbg.generated Tue Aug 23 15:48:40 CST 2016
     */
    int updateByPrimaryKeySelective(UcLoginRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uc_login_record
     *
     * @mbg.generated Tue Aug 23 15:48:40 CST 2016
     */
    int updateByPrimaryKey(UcLoginRecord record);

    List<UcLoginRecord> selectByPrimaryKeys(List<Integer> pkidList);

    int countByProperties(@Param("params") Map<String, Object> properties);

    List<UcLoginRecord> findByProperties(@Param("params") Map<String, Object> properties, @Param("startRow") int startRow, @Param("pageSize") int pageSize);
}