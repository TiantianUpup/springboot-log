package com.h2t.study.util;

/**
 * traceId生成工具类
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2020/03/18 15:19
 */
public class TraceIdUtil {
    public static String getTraceId() {
        return java.util.UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }
}
