package com.jiuxian.framework.mvc;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;


public class WebBindingInitializer implements org.springframework.web.bind.support.WebBindingInitializer {
    @Override
    public void initBinder(WebDataBinder binder, WebRequest request){
        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }
}
