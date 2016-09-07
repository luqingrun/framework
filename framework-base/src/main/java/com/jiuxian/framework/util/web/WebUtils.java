package com.jiuxian.framework.util.web;

import com.jiuxian.framework.util.RegexUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public abstract class WebUtils {

    public static final String JSON_SUFFIX = ".json";

    public static final String REQUEST_METHOD_POST = "post";

    public static final String REQUEST_METHOD_GET = "get";


    private static final String IGNORE_IPS[] = {"10.", "192.168."};

    /** 系统的内网IP地址 */
    public static final String LOCAL_IP;

    /** 系统的本地服务器名 */
    public static final String HOST_NAME;


    static {
        String ip = null;
        String hostName = null;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
            InetAddress ipAddr[] = InetAddress.getAllByName(hostName);
            for (int i = 0; i < ipAddr.length; i++) {
                ip = ipAddr[i].getHostAddress();
                for(String innerIp : IGNORE_IPS) {
                    if (ip.startsWith(innerIp)) {
                        break;
                    }
                }
            }
            if (ip == null) {
                ip = ipAddr[0].getHostAddress();
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        LOCAL_IP = ip;
        HOST_NAME = hostName;

    }


    public static String getParaStr(HttpServletRequest request) {
        StringBuffer para = new StringBuffer("");
        Enumeration parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = (String) parameterNames.nextElement();
            String value = Arrays.toString(request.getParameterValues(key));
            if (para.length() == 0) {
                para.append(key + "=" + value);
            } else {
                para.append("|" + key + "=" + value);
            }
        }
        return para.toString();
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = "";
        Set<String> ipSet = getIpAddrSet(request);
        if (!ipSet.isEmpty()) {
            ip = ipSet.iterator().next();
        }
        return ip;
    }

    private static final ThreadLocal<String> requestIdThreadLocal = new ThreadLocal<>();

    public static String getRequestId() {
        String requestId = requestIdThreadLocal.get();
        if (StringUtils.isBlank(requestId)) {
            requestId = UUID.randomUUID().toString();
            requestIdThreadLocal.set(requestId);
        }
        return requestId;
    }




    private static boolean ignoreIps(String ip) {
        if (StringUtils.isBlank(ip) || !RegexUtils.isIpv4(ip)) {
            return true;
        }
        for (String ignoreIp : IGNORE_IPS) {
            if (ip.startsWith(ignoreIp)) {
                return true;
            }
        }
        return false;
    }

    //获取非内网的真实和代理的所有ip
    public static Set<String> getIpAddrSet(HttpServletRequest request) {
        Set<String> ipSet = new HashSet<String>();
        Set<String> sourceIps = new HashSet<String>();

        sourceIps.add(request.getHeader("RemoteIp"));
        sourceIps.add(request.getHeader("X-Real-IP"));
        sourceIps.add(request.getHeader("x-forwarded-for"));
        sourceIps.add(request.getHeader("Proxy-Client-IP"));
        sourceIps.add(request.getHeader("WL-Proxy-Client-IP"));


        for (String sourceIp : sourceIps) {
            if (StringUtils.isBlank(sourceIp)) {
                continue;
            }
            String[] ipArr = sourceIp.split("[ |,|\\|;]");
            if (ipArr != null && ipArr.length > 0) {
                for (int i = 0; i < ipArr.length; i++) {
                    String ip = StringUtils.trim(ipArr[i]);
                    if (!ignoreIps(ip)) {
                        ipSet.add(ip);
                    }
                }
            }
        }
        if (ipSet.size() == 0) {
            ipSet.add(request.getRemoteAddr());
        }
        return ipSet;
    }


    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    @SuppressWarnings("unchecked")
    public static String getRequestPath(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder(request.getRequestURI());
        Enumeration<String> enumeration = request.getParameterNames();
        if (enumeration.hasMoreElements()) {
            sb.append("?");
        }
        while (enumeration.hasMoreElements()) {
            Object object = enumeration.nextElement();
            sb.append(object);
            sb.append("=");
            sb.append(request.getParameter(object.toString()));
            sb.append("&");
        }
        String requesturi = "";
        String contextPath = request.getContextPath();
        if (sb.indexOf("&") != -1) {
            requesturi = sb.substring(0, sb.lastIndexOf("&"));
        } else {
            requesturi = sb.toString();
        }
        requesturi = requesturi.substring(requesturi.indexOf(contextPath) + contextPath.length());
        return requesturi;
    }

    /**
     * json数据返回
     *
     * @param response
     * @param str
     */
    public static void printWrite(HttpServletResponse response, String str) {
        if (StringUtils.isBlank(str)) {
            throw new RuntimeException("return str is null");
        }
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }

    /**
     * 是否为post请求
     *
     * @param request
     * @return
     */
    public static boolean isPost(HttpServletRequest request) {
        return StringUtils.equalsIgnoreCase(REQUEST_METHOD_POST, request.getMethod());
    }

    /**
     * 是否为get请求
     *
     * @param request
     * @return
     */
    public static boolean isGet(HttpServletRequest request) {
        return StringUtils.equalsIgnoreCase(REQUEST_METHOD_GET, request.getMethod());
    }

    /**
     * 是否为json请求
     *
     * @param request
     * @return
     */
    public static boolean isJsonRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return StringUtils.endsWith(uri, JSON_SUFFIX);
    }

    public static boolean isAjax( HttpServletRequest request ) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With")) ? true : false;
    }
}
