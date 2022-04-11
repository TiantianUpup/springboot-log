package com.h2t.study.util;

import com.alibaba.fastjson.JSONObject;
import com.h2t.study.interceptor.OkHttpTraceIdInterceptor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Optional;

/**
 * OkHttp工具类
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2020/03/19 14:28
 */
@Slf4j
public class OkHttpUtil {
    private static OkHttpClient client = new OkHttpClient.Builder()
            .addNetworkInterceptor(new OkHttpTraceIdInterceptor())
            .build();

    /**
     * GET请求
     *
     * @param url 请求地址
     * @return
     */
    public static String doGet(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = client.newCall(request);
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String responseBody = Optional.ofNullable(response).map(Response::body).map(Object::toString).orElse(null);
        log.info("responseBody:{}", responseBody);
        return JSONObject.toJSONString(responseBody);
    }


    /**
     * POST请求
     *
     * @param url  url
     * @param json json
     * @return {@link String}
     */
    public static String doPost(String url, String json) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        final Call call = client.newCall(request);
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String responseBody = Optional.ofNullable(response).map(Response::body).map(Object::toString).orElse(null);
        log.info("responseBody:{}", responseBody);
        return JSONObject.toJSONString(responseBody);
    }
}
