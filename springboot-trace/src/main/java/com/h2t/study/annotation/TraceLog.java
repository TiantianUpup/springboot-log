package com.h2t.study.annotation;

import java.lang.annotation.*;

/**
 * 跟踪日志
 *
 * @author wayne
 * @date 2022/04/11
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TraceLog {
    /**
     * 日志打印级别
     *
     * @see com.h2t.study.constant.Constants
     * @return {@link String}
     */
    String level() default "info";

    /**
     * 日志打印类型
     * @see com.h2t.study.constant.Constants
     * @return {@link String}
     */
    String type() default "service";
}
