package com.h2t.study.util;

import com.h2t.study.constant.Constants;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 线程MDC包装类
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2020/03/18 15:18
 */
public class ThreadMdcUtil {
    public static void setTraceIdIfAbsent() {
        if (MDC.get(Constants.TRACE_ID) == null) {
            MDC.put(Constants.TRACE_ID, TraceIdUtil.getTraceId());
        }
    }

    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceIdIfAbsent();
            try {
                return callable.call();
            } catch (Exception e) {
                MDC.clear();
                throw new RuntimeException(e);
            }
        };
    }

    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return new Runnable() {
            @Override
            public void run() {
                if (context == null) {
                    MDC.clear();
                } else {
                    MDC.setContextMap(context);
                }
                setTraceIdIfAbsent();
                try {
                    runnable.run();
                } catch (Exception e) {
                    MDC.clear();
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
