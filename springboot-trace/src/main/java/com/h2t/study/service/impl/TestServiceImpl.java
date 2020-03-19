package com.h2t.study.service.impl;

import com.h2t.study.service.TestService;
import com.h2t.study.util.OkHttpUtil;
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
    public void test() {
        ThreadPoolExecutorMdcWrapper threadPoolExecutorMdcWrapper = new ThreadPoolExecutorMdcWrapper(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>());
        threadPoolExecutorMdcWrapper.execute(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("Another thread running");
            }
        });

        System.out.println("From third service, response: " + OkHttpUtil.doGet("http://localhost:8082/test"));
    }
}
