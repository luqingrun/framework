package com.jiuxian.framework.util.http;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jiuxian.framework.monitor.HttpClientMonitor;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


public class HttpClientUtil {

    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();


    private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(15000)
            .setConnectTimeout(15000)
            .setConnectionRequestTimeout(15000)
            .build();

    /**
     * 发送 post请求
     *
     * @param httpUrl 地址
     */
    public static String sendHttpPost(String httpUrl) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        return sendHttpBase(httpPost);
    }

    /**
     * 发送 post请求
     *
     * @param httpUrl 地址
     * @param params  参数(格式:key1=value1&key2=value2)
     */
    public static String sendHttpPost(String httpUrl, String params) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            //设置参数
            StringEntity stringEntity = new StringEntity(params, "UTF-8");
            stringEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(stringEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpBase(httpPost);
    }




    /**
     * 发送 post请求
     *
     * @param httpUrl 地址
     * @param maps    参数
     */
    public static String sendHttpPost(String httpUrl, Map<String, String> maps) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (String key : maps.keySet()) {
            nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpBase(httpPost);
    }


    /**
     * 发送 post请求（带文件）
     *
     * @param httpUrl   地址
     * @param maps      参数
     * @param fileLists 附件
     */
    public static String sendHttpPost(String httpUrl, Map<String, String> maps, List<File> fileLists) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
        if(maps!=null) {
            for (String key : maps.keySet()) {
                meBuilder.addPart(key, new StringBody(maps.get(key), ContentType.TEXT_PLAIN));
            }
        }
        if(fileLists!=null) {
            for (File file : fileLists) {
                FileBody fileBody = new FileBody(file);
                meBuilder.addPart("files", fileBody);
            }
        }
        HttpEntity reqEntity = meBuilder.build();
        httpPost.setEntity(reqEntity);
        return sendHttpBase(httpPost);
    }


    /**
     * 发送 get请求
     *
     * @param httpUrl
     */
    public static String sendHttpGet(String httpUrl) {
        HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求
        return sendHttpBase(httpGet);
    }

    /**
     * 发送 get请求Https
     *
     * @param httpUrl
     */
    public static String sendHttpsGet(String httpUrl) {
        HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求
        return sendHttpsGet(httpGet);
    }

    /**
     * 发送请求
     *
     * @param httpBase
     * @return
     */
    private static String sendHttpBase(HttpRequestBase httpBase) {
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            httpBase.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpBase);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(httpClient, response);
        }
        return responseContent;
    }

    /**
     * 发送Get请求Https
     *
     * @param httpGet
     * @return
     */
    private static String sendHttpsGet(HttpGet httpGet) {
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.load(new URL(httpGet.getURI().toString()));
            DefaultHostnameVerifier hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher);
            httpClient = HttpClients.custom().setSSLHostnameVerifier(hostnameVerifier).build();
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(httpClient, response);
        }
        return responseContent;
    }

    private static CloseableHttpClient getHttpClient() {
        // 创建默认的httpClient实例.
        return HttpClients.custom()
                .addInterceptorLast(HttpClientMonitor.requestInterceptor)
                .addInterceptorLast(HttpClientMonitor.responseInterceptor)
                .build();
    }

    private static void close(CloseableHttpClient httpClient, CloseableHttpResponse response) {
        if (response != null) {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
            String content = HttpClientUtil.sendHttpPost("http://list.jiuxian.com/selectSearchDefaultKey.htm?t=1472798665506");
            System.out.println(content);
                }
            };
            thread.start();
        }

        System.in.read();
    }
}
