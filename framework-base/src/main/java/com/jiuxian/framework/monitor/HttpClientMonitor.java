package com.jiuxian.framework.monitor;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

public class HttpClientMonitor {
    private static Log log = LogFactory.getLog(HttpClientMonitor.class);

    private HttpClientMonitor() {

    }

    public static org.apache.http.HttpRequestInterceptor requestInterceptor = new HttpRequestInterceptor();
    public static org.apache.http.HttpResponseInterceptor responseInterceptor = new HttpResponseInterceptor();

    /**
     * 请求Interceptor
     */
    static class HttpRequestInterceptor implements org.apache.http.HttpRequestInterceptor{
        @Override
        public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
            httpContext.setAttribute("start", System.currentTimeMillis());
            String uri = getHttpRequestUri(httpRequest, 0)+httpRequest.getRequestLine().getUri();
            httpContext.setAttribute("uri", uri);
            MonitorExecute.getConcurrent(uri).incrementAndGet(); // 并发计数
        }
    }

    /**
     * 重试最大次数
     */
    private static final int RETRY_TIMES = 3;

    /**
     * 获得方位的URI
     * @param httpRequest
     * @param times
     * @return
     */
    private static String getHttpRequestUri(HttpRequest httpRequest, int times){
        if(httpRequest == null){
            return null;
        }
        if(httpRequest instanceof HttpRequestWrapper){
            HttpRequestWrapper wrapper = (HttpRequestWrapper) httpRequest;
            HttpHost target = wrapper.getTarget();
            if(target == null){
                if(times > RETRY_TIMES){
                    return null;
                }
                return getHttpRequestUri(wrapper.getOriginal(), times++);
            }else{
                return target.toURI();
            }
        }else if(httpRequest instanceof HttpRequestBase){
            HttpRequestBase httpRequestBase = (HttpRequestBase) httpRequest;
            return httpRequestBase.getURI().toString();
        }
        return null;
    }

    /**
     * 响应Interceptor
     */
    static class HttpResponseInterceptor implements org.apache.http.HttpResponseInterceptor{

        @Override
        public void process(HttpResponse response, HttpContext httpContext) throws HttpException, IOException {
            long start = (Long)(httpContext.getAttribute("start"));
            int statusCode = response.getStatusLine().getStatusCode();
            String uri = (String)httpContext.getAttribute("uri");
            log.info("collect=" + uri+", "+statusCode);
            boolean error = true;
            if(200 <= statusCode && statusCode <= 299){
                error = false;
            }else{
                error =true;
            }
            long elapsed = System.currentTimeMillis() - start; // 计算调用耗时
            MonitorExecute.collect(uri, "httpclient", elapsed, error);
            MonitorExecute.getConcurrent(uri).decrementAndGet();
        }
    }
}
