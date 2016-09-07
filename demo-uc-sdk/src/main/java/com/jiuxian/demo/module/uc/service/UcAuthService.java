package com.jiuxian.demo.module.uc.service;

import com.jiuxian.demo.module.uc.entity.UcAuth;
import com.jiuxian.demo.module.uc.entity.UcLoginRecord;
import com.jiuxian.demo.module.uc.entity.UcRole;
import com.jiuxian.framework.util.page.Pager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UcAuthService {

    UcAuth findById(Integer pkid);

    int update(UcAuth ucAuth);

    int delete(Integer pkid);

    Integer insert(UcAuth ucAuth);

    List<UcAuth> findByIds(List<Integer> pkidList);

    default Map<Integer, UcAuth> findMapByIds(List<Integer> pkidList){
        Map<Integer, UcAuth> map = new HashMap();
        List<UcAuth> ucAuths = findByIds(pkidList);
        for(UcAuth ucAuth : ucAuths){
            map.put(ucAuth.getPkid(), ucAuth);
        }
        return map;
    }

    Pager<UcAuth> pageByProperties(Map<String, Object> properties, int page);

    List<UcAuth> findByRolePkid(List<Integer> rolePkid);
}