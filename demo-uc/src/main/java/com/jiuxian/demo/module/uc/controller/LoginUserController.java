package com.jiuxian.demo.module.uc.controller;

import com.jiuxian.demo.constant.WebConstant;
import com.jiuxian.demo.module.uc.entity.UcUser;
import com.jiuxian.demo.module.uc.service.UcUserService;
import com.jiuxian.framework.cache.CacheService;
import com.jiuxian.framework.captcha.Captcha;
import com.jiuxian.framework.captcha.CaptchaUtils;
import com.jiuxian.framework.mvc.annotation.Auth;
import com.jiuxian.framework.mvc.annotation.Login;
import com.jiuxian.framework.util.page.AjaxResponseData;
import com.jiuxian.framework.util.web.CookieUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LoginUserController {
    private static Log log = LogFactory.getLog(LoginUserController.class);


    @Autowired
    private CacheService cacheService;

    @Autowired
    private UcUserService ucUserService;


    @Login(false)
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("login");
        request.setAttribute("redirectUrl", request.getParameter("redirectUrl"));
        mv.addObject("loginName", StringUtils.trimToEmpty(request.getParameter("loginName")));
        return mv;
    }

    @Auth(false)
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }

    @Login(false)
    @RequestMapping(value = "/vcode")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {

        String randomKey = RandomStringUtils.randomAlphanumeric(32);
        Captcha captcha = CaptchaUtils.getImageValidateCode(4, 100, 40);

        ServletOutputStream so = null;
        try {
            so = response.getOutputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(captcha.getImage(), "PNG", bos);
            byte[] buf = bos.toByteArray();
            so.write(buf);
            cacheService.setString(randomKey, captcha.getText(), 5 * 60);
            CookieUtils.saveCookie(response, WebConstant.COOKIE_VCODE, randomKey, WebConstant.COOKIE_DOMAIN);
            log.info("captcha=" + captcha.getText() + "| randomkey : " + randomKey);
        } catch (IOException e) {
            log.error("验证码response错误", e);
        } finally {
            if (null != so) {
                try {
                    so.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Login(false)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object loginSubmit(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = true) String loginName, @RequestParam(required = true) String passwd, @RequestParam(required = true) String vcode) {
        AjaxResponseData data = new AjaxResponseData();
        String vcodeKey = CookieUtils.getCookieValue(WebConstant.COOKIE_VCODE, request);
        String cacheVcode = cacheService.getString(vcodeKey);
        if(!vcode.equalsIgnoreCase(cacheVcode)){
            //验证码错误
            data.setMsg("验证码错误");
            return data;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("login_name", loginName);
        List<UcUser> ucUserList = ucUserService.findByProperties(map, 0, 10);
        if(ucUserList !=null && ucUserList.size() > 0){
            UcUser ucUser = ucUserList.get(0);
            String ticket = ucUser.getTicket();
            CookieUtils.saveCookie(response, WebConstant.COOKIE_LOGIN_TICKET, ticket, WebConstant.COOKIE_DOMAIN);
            return new RedirectView(request.getContextPath()+"/index");
        }else{
            //验证码错误
            data.setMsg("用户名不存在");
            return data;
        }
    }

}