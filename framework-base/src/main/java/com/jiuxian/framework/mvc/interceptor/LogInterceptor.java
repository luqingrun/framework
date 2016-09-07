package com.jiuxian.framework.mvc.interceptor;

import com.jiuxian.framework.util.web.CookieUtils;
import com.jiuxian.framework.util.web.WebUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogInterceptor extends HandlerInterceptorAdapter {
    private static Logger log = Logger.getLogger(LogInterceptor.class);


    private String ticketName;

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String requestId = WebUtils.getRequestId();
        String ip = WebUtils.getIpAddr(request);
        String uri = request.getRequestURI();
        String para = WebUtils.getParaStr(request);
        String ticket = CookieUtils.getCookieValue(ticketName, request);
        log.info("access_log, ticket="+ ticket +", requestId=" + requestId + ", ip=" + ip + ", uri=" + uri + ", para=(" + para + "), handler=" + handler);
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
}
