package com.h2t.study.controller;

import com.h2t.study.annotation.TraceLog;
import com.h2t.study.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 控制层
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2020/03/13 16:14
 */
@RestController
@RequestMapping("/test")
public class TestController {
    private Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Resource
    private TestService testService;

    @GetMapping
    @TraceLog(type = "controller")
    public Object test() throws InterruptedException {
        LOGGER.info("TestController /test invoke");
        Thread.sleep(6000);
        testService.test();
        return "hello";
    }
}
