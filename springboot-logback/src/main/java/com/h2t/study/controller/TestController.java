package com.h2t.study.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制层
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2019/08/09 9:31
 */
@RestController
public class TestController {
    Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/api/test")
    public Object hello() {
        logger.trace("【TestController.class】trace level log input");
        System.out.println("【TestController.class】trace level log input");
        logger.debug("【TestController.class】debug level log input");
        System.out.println("【TestController.class】debug level log input");
        logger.info("【TestController.class】info level log input");
        System.out.println("【TestController.class】info level log input");
        logger.warn("【TestController.class】warn level log input");
        System.out.println("【TestController.class】warn level log input");
        logger.error("【TestController.class】error level log input");
        System.out.println("【TestController.class】error level log input");
        return "hello world";
    }
}
