package com.jiuxian.demo.module.uc.service.impl;

import com.jiuxian.demo.module.uc.dao.UcRoleAuthMapMapper;
import com.jiuxian.demo.module.uc.dao.UcRoleMapper;
import com.jiuxian.demo.module.uc.dao.UcUserRoleMapMapper;
import com.jiuxian.demo.module.uc.entity.UcRole;
import com.jiuxian.demo.module.uc.entity.UcUserRoleMap;
import com.jiuxian.demo.module.uc.service.UcRoleService;
import com.jiuxian.framework.util.page.Pager;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by luqingrun on 16/8/23.
 */
@Service("ucRoleService")
public class UcRoleServiceImpl implements UcRoleService {

    @Autowired
    private UcRoleMapper ucRoleMapper;

    @Autowired
    private UcRoleAuthMapMapper ucRoleAuthMapMapper;

    @Autowired
    private UcUserRoleMapMapper ucUserRoleMapMapper;

    @Override
    public UcRole findById(Integer pkid) {
        return ucRoleMapper.selectByPrimaryKey(pkid);
    }

    @Override
    public int update(UcRole ucRole) {
        return ucRoleMapper.updateByPrimaryKeySelective(ucRole);
    }

    @Override
    public int delete(Integer pkid) {
        return ucRoleMapper.deleteByPrimaryKey(pkid);
    }

    @Override
    public Integer insert(UcRole ucRole) {
        return ucRoleMapper.insert(ucRole);
    }

    @Override
    public List<UcRole> findByIds(List<Integer> pkidList) {
        return null;
    }

    @Override
    public Map<Integer, UcRole> findMapByIds(List<Integer> pkidList) {
        return null;
    }

    @Override
    public Pager<UcRole> pageByProperties(Map<String, Object> properties, int page) {
        return null;
    }

    @Override
    public List<UcRole> findByUserPkid(Integer userPkid) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_pkid", userPkid);
        List<UcUserRoleMap> userRoleMapList = ucUserRoleMapMapper.findByProperties(map, 0, 100000);
        List<Integer> rolePkidList = userRoleMapList.stream().map(UcUserRoleMap::getRolePkid).collect(Collectors.toList());
        return ucRoleMapper.selectByPrimaryKeys(rolePkidList);
    }

    @Override
    public List<UcRole> findByUserPkid(Integer userPkid, Integer allot) {
        return null;
    }

    @Override
    public List<Integer> findUserIdsByRoleName(String roleName) {
        return null;
    }
}
