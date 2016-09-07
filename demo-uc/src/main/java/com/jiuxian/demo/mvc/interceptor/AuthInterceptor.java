package com.jiuxian.demo.mvc.interceptor;


import com.jiuxian.demo.constant.WebConstant;
import com.jiuxian.demo.module.uc.entity.LoginUser;
import com.jiuxian.demo.module.uc.entity.UcAuth;
import com.jiuxian.framework.cache.CacheService;
import com.jiuxian.framework.mvc.annotation.Auth;
import com.jiuxian.framework.mvc.annotation.Login;
import com.jiuxian.framework.util.UrlUtils;
import com.jiuxian.framework.util.json.JsonUtils;
import com.jiuxian.framework.util.page.AjaxResponseData;
import com.jiuxian.framework.util.web.WebUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;


public class AuthInterceptor extends HandlerInterceptorAdapter {
    private static Logger log = Logger.getLogger(AuthInterceptor.class);

    private SortedSet<String> exceptPath;

    @Autowired
    private CacheService cacheService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String path = request.getContextPath() + request.getServletPath();
        String ip = WebUtils.getIpAddr(request);

        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Auth authAnnotation = handlerMethod.getMethodAnnotation(Auth.class);
            Login loginAnnotation = handlerMethod.getMethodAnnotation(Login.class);
            if((loginAnnotation!=null &&!loginAnnotation.value()) || (authAnnotation!=null && !authAnnotation.value())){
                return true;
            }
        }
        // 是否需权限验证
        if (UrlUtils.urlMatch(exceptPath, path)) {
            return true;
        }

        AjaxResponseData data = new AjaxResponseData();
        data.setCode(403);
        data.setMsg("forbidden");
        LoginUser loginUser = (LoginUser) request.getAttribute(WebConstant.REQ_ATTR_LOGIN_USER);
        if (null == loginUser) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JsonUtils.objectToJson(data));
            return false;
        }
        List<UcAuth> urlList = loginUser.getUcAuthList();
        boolean isPass = false;
        if (CollectionUtils.isNotEmpty(urlList)) {
            SortedSet<String> dirtySet = new TreeSet<String>();
            for (UcAuth ucAuth : urlList) {
                dirtySet.add(ucAuth.getPath() + "*");
            }
            isPass = UrlUtils.urlMatch(dirtySet, path);
        }

        if (!isPass) {
            log.info("############################ip[" + ip + "],user[" + loginUser.getUcUser().getPkid() + "],path[" + path + "],forbidden################################");
            data.setMsg("forbidden");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JsonUtils.objectToJson(data));
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    @Override
    public void afterConcurrentHandlingStarted(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    }

    public void setExceptPath(SortedSet<String> exceptPath) {
        if (null == exceptPath) {
            this.exceptPath = new TreeSet<>();
        } else {
            this.exceptPath = exceptPath;
        }
    }
}