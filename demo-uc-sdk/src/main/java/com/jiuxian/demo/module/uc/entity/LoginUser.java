package com.jiuxian.demo.module.uc.entity;


import com.jiuxian.framework.util.json.JsonUtils;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by luqingrun on 16/4/8.
 */
public class LoginUser implements java.io.Serializable {

    private static final long serialVersionUID = 7383109775575577079L;
    /** 当前登录用户 */
    private UcUser ucUser;
    /** 当前登录拥有角色 */
    private List<UcRole> ucRoleList = new ArrayList<>();
    private List<String> roleTags = new ArrayList<>();
    /** 当前登录拥有权限角色 */
    private List<UcAuth> ucAuthList = new ArrayList<>();
    /** 当前登录拥有菜单 */
    private List<UcAuth> menuList = new ArrayList<>();

    public UcUser getUcUser() {
        return ucUser;
    }

    public void setUcUser(UcUser ucUser) {
        this.ucUser = ucUser;
    }

    public List<UcRole> getUcRoleList() {
        return ucRoleList;
    }

    public void setUcRoleList(List<UcRole> ucRoleList) {
        this.ucRoleList = ucRoleList;
        roleTags.clear();
        if (CollectionUtils.isNotEmpty(ucRoleList)) {
            roleTags.addAll(ucRoleList.stream().map(UcRole::getTag).collect(Collectors.toList()));
        }
    }

    public List<String> getRoleTags() {
        return roleTags;
    }

    public List<UcAuth> getUcAuthList() {
        return ucAuthList;
    }

    public void setUcAuthList(List<UcAuth> ucAuthList) {
        this.ucAuthList = ucAuthList;
    }

    public List<UcAuth> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<UcAuth> menuList) {
        this.menuList = menuList;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "ucUser=" + JsonUtils.objectToJson(ucUser) +
                ", ucRoleList=" + JsonUtils.objectToJson(ucRoleList) +
                ", roleTags=" + JsonUtils.objectToJson(roleTags) +
                ", ucAuthList=" + JsonUtils.objectToJson(ucAuthList) +
                ", menuList=" + JsonUtils.objectToJson(menuList) +
                '}';
    }
}
