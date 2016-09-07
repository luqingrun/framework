package com.jiuxian.demo.mvc.interceptor;


import com.jiuxian.demo.constant.CacheConstant;
import com.jiuxian.demo.constant.WebConstant;
import com.jiuxian.demo.module.uc.entity.LoginUser;
import com.jiuxian.demo.module.uc.service.UcUserService;
import com.jiuxian.framework.cache.CacheService;
import com.jiuxian.framework.mvc.annotation.Login;
import com.jiuxian.framework.util.UrlUtils;
import com.jiuxian.framework.util.json.JsonUtils;
import com.jiuxian.framework.util.page.AjaxResponseData;
import com.jiuxian.framework.util.web.CookieUtils;
import com.jiuxian.framework.util.web.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.SortedSet;
import java.util.TreeSet;


public class LoginInterceptor extends HandlerInterceptorAdapter {
    private static Logger log = Logger.getLogger(LoginInterceptor.class);

    public static ThreadLocal<LoginUser> threadLocal = new ThreadLocal<>();

    private SortedSet<String> exceptPath;

    @Autowired
    private CacheService cacheService;
    @Autowired
    private UcUserService ucUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String requestId = WebUtils.getRequestId();
        String ip = WebUtils.getIpAddr(request);
        String uri = request.getRequestURI();
        String para = WebUtils.getParaStr(request);
        String contextPath = request.getContextPath();
        String path = request.getContextPath() + request.getServletPath();


        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Login methodAnnotation = handlerMethod.getMethodAnnotation(Login.class);
            if(methodAnnotation!=null && !methodAnnotation.value()){
                return true;
            }
        }

        if (UrlUtils.urlMatch(exceptPath, path)) {
            return true;
        }



        //log.info("access_log, requestId=" + requestId + ", ip=" + ip + ", uri=" + uri + ", para=(" + para + "), handler=" + handler);
        String ticket = CookieUtils.getCookieValue(WebConstant.COOKIE_LOGIN_TICKET, request);
        AjaxResponseData data = new AjaxResponseData();
        data.setCode(403);
        data.setMsg("请重新登陆");
        if(StringUtils.isBlank(ticket)){
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JsonUtils.objectToJson(data));
            return false;
        }
        LoginUser loginUser = (LoginUser) cacheService.get(CacheConstant.LOGIN_TICKET + ticket);
        if(loginUser == null){
            loginUser = ucUserService.findLoginUserByTicket(ticket);
        }
        if(loginUser == null){
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JsonUtils.objectToJson(data));
            return false;
        }

        cacheService.set(CacheConstant.LOGIN_TICKET + ticket, loginUser);
        request.setAttribute(WebConstant.REQ_ATTR_LOGIN_USER, loginUser);
        request.setAttribute(WebConstant.REQ_ATTR_LOGIN_USER_PKID, loginUser.getUcUser().getPkid());
        threadLocal.set(loginUser);
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
