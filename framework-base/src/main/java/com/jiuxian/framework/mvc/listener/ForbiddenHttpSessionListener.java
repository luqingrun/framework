package com.jiuxian.framework.mvc.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 禁用HttpSession
 */
public class ForbiddenHttpSessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
       //se.getSession().invalidate();
        throw new RuntimeException("forbidden use httpsession, please use cache !");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }
}
