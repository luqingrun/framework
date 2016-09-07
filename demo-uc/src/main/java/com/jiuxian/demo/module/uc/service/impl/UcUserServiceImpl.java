package com.jiuxian.demo.module.uc.service.impl;

import com.jiuxian.demo.module.uc.dao.UcUserMapper;
import com.jiuxian.demo.module.uc.entity.LoginUser;
import com.jiuxian.demo.module.uc.entity.UcAuth;
import com.jiuxian.demo.module.uc.entity.UcRole;
import com.jiuxian.demo.module.uc.entity.UcUser;
import com.jiuxian.demo.module.uc.service.UcAuthService;
import com.jiuxian.demo.module.uc.service.UcRoleService;
import com.jiuxian.demo.module.uc.service.UcUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by luqingrun on 16/8/23.
 */
@Service("ucUserService")
public class UcUserServiceImpl implements UcUserService {

    @Autowired
    private UcUserMapper userMapper;

    @Autowired
    private UcRoleService ucRoleService;

    @Autowired
    private UcAuthService ucAuthService;

    @Override
    public UcUser findById(Integer pkid) {
        return userMapper.selectByPrimaryKey(pkid);
    }

    @Override
    public int update(UcUser ucUser) {
        return userMapper.updateByPrimaryKeySelective(ucUser);
    }

    @Override
    public int delete(Integer pkid) {
        return userMapper.deleteByPrimaryKey(pkid);
    }

    @Override
    public Integer insert(UcUser ucUser) {
        return userMapper.insert(ucUser);
    }

    @Override
    public List<UcUser> findByIds(List<Integer> pkidList) {
        return userMapper.selectByPrimaryKeys(pkidList);
    }

    @Override
    public LoginUser findLoginUserByPkid(Integer pkid) {
        if(pkid == null){
            return null;
        }
        UcUser ucUser = findById(pkid);
        if(ucUser == null){
            return null;
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setUcUser(ucUser);

        List<UcRole> ucRoleList = ucRoleService.findByUserPkid(pkid);
        loginUser.setUcRoleList(ucRoleList);

        List<Integer> rolePkidList = ucRoleList.stream().map(UcRole::getPkid).collect(Collectors.toList());

        List<UcAuth> ucAuthList = ucAuthService.findByRolePkid(rolePkidList);
        loginUser.setUcAuthList(ucAuthList);

        List<UcAuth> menuList = ucAuthList.stream().filter(ucAuth -> ucAuth.getMenu() == 1).collect(Collectors.toList());
        loginUser.setMenuList(menuList);
        return loginUser;
    }

    @Override
    public int countByProperties(Map<String, Object> properties) {
        return userMapper.countByProperties(properties);
    }

    @Override
    public List<UcUser> findByProperties(Map<String, Object> properties, int startRow, int pageSize) {
        return userMapper.findByProperties(properties, startRow, pageSize);
    }
}
