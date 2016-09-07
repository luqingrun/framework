package com.jiuxian.demo.mvc.resolver;

import com.jiuxian.demo.constant.WebConstant;
import com.jiuxian.demo.module.uc.entity.LoginUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        if (clazz.equals(LoginUser.class)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Class<?> clazz = parameter.getParameterType();
        if (clazz.equals(LoginUser.class)) {
            LoginUser user = (LoginUser) webRequest.getAttribute(WebConstant.REQ_ATTR_LOGIN_USER,
                    RequestAttributes.SCOPE_REQUEST);
//            if(user == null){
//                user = new LoginUser();
//                UcUser ucUser = new UcUser();
//                ucUser.setPkid(10);
//                ucUser.setAddTime(new Date());
//                user.setUcUser(ucUser);
//            }

            return user;
        }
        return null;
    }
}
