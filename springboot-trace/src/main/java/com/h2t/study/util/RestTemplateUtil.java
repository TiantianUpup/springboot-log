package com.h2t.study.util;

import com.alibaba.fastjson.JSONObject;
import com.h2t.study.interceptor.RestTemplateTraceIdInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * RestTemplate工具类
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2020/03/19 16:11
 */
public class RestTemplateUtil {
    private static RestTemplate restTemplate = new RestTemplate();

    /**
     * GET请求
     *
     * @param url 请求地址
     * @return
     */
    public static String doGet(String url) {
        restTemplate.setInterceptors(Arrays.asList(new RestTemplateTraceIdInterceptor()));
        return JSONObject.toJSONString(restTemplate.getForObject(url, String.class));
    }
}
