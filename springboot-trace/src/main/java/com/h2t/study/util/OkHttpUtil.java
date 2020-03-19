package com.h2t.study.util;

import com.alibaba.fastjson.JSONObject;
import com.h2t.study.interceptor.OkHttpTraceIdInterceptor;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * OkHttp工具类
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2020/03/19 14:28
 */
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
        return JSONObject.toJSONString(response.body());
    }
}
