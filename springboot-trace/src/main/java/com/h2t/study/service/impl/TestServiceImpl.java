package com.h2t.study.service.impl;

import com.h2t.study.annotation.TraceLog;
import com.h2t.study.service.TestService;
import com.h2t.study.wrapper.ThreadPoolExecutorMdcWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 业务类接口实现类
 *
 * @author hetiantian
 * @version 1.0
 * @Date 2020/03/13 16:14
 */
@Service
public class TestServiceImpl implements TestService {
    private Logger LOGGER = LoggerFactory.getLogger(TestServiceImpl.class);

    @Override
    @TraceLog(level = "warn",type = "service")
    public void test() throws InterruptedException {
        ThreadPoolExecutorMdcWrapper threadPoolExecutorMdcWrapper = new ThreadPoolExecutorMdcWrapper(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>());
        Thread.sleep(2000);
        threadPoolExecutorMdcWrapper.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LOGGER.info("Another thread1 running");
            }
        });

        threadPoolExecutorMdcWrapper.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LOGGER.info("Another thread2 running");
            }
        });

//        OkHttpUtil.doGet("http://localhost:8082/test");
//        HttpClientUtil.doGet("http://localhost:8082/test");
//        RestTemplateUtil.doGet("http://localhost:8082/test");
    }
}
