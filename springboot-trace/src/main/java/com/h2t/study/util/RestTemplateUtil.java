package com.h2t.study.util;

import com.alibaba.fastjson.JSONObject;
import com.h2t.study.interceptor.RestTemplateTraceIdInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * RestTemplate工具类
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2020/03/19 16:11
 */
@Slf4j
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
        String response = restTemplate.getForObject(url, String.class);
        log.info("GET请求返回结果：{}", response);
        return JSONObject.toJSONString(response);
    }

    /**
     * POST请求
     *
     * @param url 请求地址
     * @param json 请求参数
     * @return
     */
    public static String doPost(String url, String json) {
        restTemplate.setInterceptors(Arrays.asList(new RestTemplateTraceIdInterceptor()));
        String response = restTemplate.postForObject(url, json, String.class);
        log.info("POST请求返回结果：{}", response);
        return JSONObject.toJSONString(response);
    }
}
