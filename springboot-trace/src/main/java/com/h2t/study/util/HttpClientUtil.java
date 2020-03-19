package com.h2t.study.util;

import com.alibaba.fastjson.JSONObject;
import com.h2t.study.interceptor.HttpClientTraceIdInterceptor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * HttpClient工具类
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2020/03/19 15:42
 */
public class HttpClientUtil {
    private static CloseableHttpClient httpClient = HttpClientBuilder.create()
            .addInterceptorFirst(new HttpClientTraceIdInterceptor())
            .build();

    /**
     * GET请求
     *
     * @param url 请求地址
     * @return
     */
    public static String doGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = null;
        try {
            result = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(result);
    }
}
