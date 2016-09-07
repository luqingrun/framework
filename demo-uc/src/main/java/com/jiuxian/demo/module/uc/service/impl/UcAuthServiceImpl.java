package com.jiuxian.demo.module.uc.service.impl;

import com.jiuxian.demo.module.uc.dao.UcAuthMapper;
import com.jiuxian.demo.module.uc.dao.UcRoleAuthMapMapper;
import com.jiuxian.demo.module.uc.entity.UcAuth;
import com.jiuxian.demo.module.uc.entity.UcRoleAuthMap;
import com.jiuxian.demo.module.uc.service.UcAuthService;
import com.jiuxian.framework.util.page.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("ucAuthService")
public class UcAuthServiceImpl implements UcAuthService {

    @Autowired
    private UcAuthMapper ucAuthMapper;

    @Autowired
    private UcRoleAuthMapMapper ucRoleAuthMapMapper;

    @Override
    public UcAuth findById(Integer pkid) {
        return null;
    }

    @Override
    public int update(UcAuth ucAuth) {
        return 0;
    }

    @Override
    public int delete(Integer pkid) {
        return 0;
    }

    @Override
    public Integer insert(UcAuth ucAuth) {
        return null;
    }

    @Override
    public List<UcAuth> findByIds(List<Integer> pkidList) {
        return null;
    }

    @Override
    public Map<Integer, UcAuth> findMapByIds(List<Integer> pkidList) {
        return null;
    }

    @Override
    public Pager<UcAuth> pageByProperties(Map<String, Object> properties, int page) {
        return null;
    }

    @Override
    public List<UcAuth> findByRolePkid(List<Integer> rolePkid) {
        Map<String, Object> map = new HashMap();
        map.put("role_pkid", rolePkid);
        List<UcRoleAuthMap> roleAuthMapList = ucRoleAuthMapMapper.findByProperties(map, 0, 100000);
        List<Integer> authPkidList = roleAuthMapList.stream().map(UcRoleAuthMap::getAuthPkid).collect(Collectors.toList());
        return ucAuthMapper.selectByPrimaryKeys(authPkidList);
    }
}
