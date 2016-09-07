package com.jiuxian.demo.module.uc.service;


import com.jiuxian.demo.module.uc.entity.UcRole;
import com.jiuxian.framework.util.page.Pager;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UcRoleService {

    UcRole findById(Integer pkid);

    int update(UcRole ucRole);

    int delete(Integer pkid);

    Integer insert(UcRole ucRole);

    List<UcRole> findByIds(List<Integer> pkidList);

    default Map<Integer, UcRole> findMapByIds(List<Integer> pkidList){
        Map<Integer, UcRole> map = new HashMap<>();
        List<UcRole> ucRoles = findByIds(pkidList);
        for(UcRole ucRole : ucRoles){
            map.put(ucRole.getPkid(), ucRole);
        }
        return map;
    }

    Pager<UcRole> pageByProperties(Map<String, Object> properties, int page);

    List<UcRole> findByUserPkid(Integer userPkid);

    List<UcRole> findByUserPkid(Integer userPkid, Integer allot);

    List<Integer> findUserIdsByRoleName(String roleName);
}