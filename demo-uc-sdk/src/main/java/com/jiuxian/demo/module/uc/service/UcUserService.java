package com.jiuxian.demo.module.uc.service;

import com.jiuxian.demo.module.uc.entity.LoginUser;
import com.jiuxian.demo.module.uc.entity.UcUser;
import com.jiuxian.framework.util.page.Pager;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UcUserService {

    UcUser findById(Integer pkid);

    int update(UcUser ucUser);

    int delete(Integer pkid);

    Integer insert(UcUser ucUser);

    List<UcUser> findByIds(List<Integer> pkidList);

    default Map<Integer, UcUser> findMapByIds(List<Integer> pkidList){
        Map<Integer, UcUser> map = new HashMap();
        List<UcUser> ucUsers = findByIds(pkidList);
        for(UcUser ucUser : ucUsers){
            map.put(ucUser.getPkid(), ucUser);
        }
        return map;
    }

    default Pager<UcUser> pageByProperties(Map<String, Object> properties, int page) {
        int totalRows = countByProperties(properties);
        Pager<UcUser> pager = new Pager<>(totalRows, page);
        List<UcUser> ucUserList = findByProperties(properties, pager.getStartRow(), pager.getPageSize());
        pager.setList(ucUserList);
        return pager;
    }

    LoginUser findLoginUserByPkid(Integer pkid);

    default LoginUser findLoginUserByTicket(String ticket){
        Map<String, Object> map = new HashMap<>();
        map.put("ticket", ticket);
        Pager<UcUser> pager = pageByProperties(map, 1);
        if(pager == null || pager.getList()==null || pager.getList().size()==0){
            return null;
        }
        return findLoginUserByPkid(pager.getList().get(0).getPkid());
    }


    int countByProperties(Map<String, Object> properties);

    List<UcUser> findByProperties(Map<String, Object> properties, int startRow, int pageSize);

}
