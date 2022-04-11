package com.h2t.study.aop;

import com.alibaba.fastjson.JSON;
import com.h2t.study.annotation.TraceLog;
import com.h2t.study.constant.Constants;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Aspect
@Component
@Slf4j
public class TraceLogAop {

    //controller pointcut
    @Pointcut("@annotation(com.h2t.study.annotation.TraceLog)")
    public void controllerPointCut() {
    }


    @Around("controllerPointCut()")
    public Object doAroundInControllerPointCut(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        TraceLog traceLog = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod().getAnnotation(TraceLog.class);
        RequestInfo requestInfo = new RequestInfo();
        long start = System.currentTimeMillis();
        if (StringUtils.equals(traceLog.type(), Constants.LOG_TYPE_CONTROLLER)) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            Optional.ofNullable(attributes).ifPresent(attr -> {
                        HttpServletRequest request = attr.getRequest();
                        requestInfo.setIp(request.getRemoteAddr());
                        requestInfo.setUrl(request.getRequestURL().toString());
                        requestInfo.setHttpMethod(request.getMethod());
                    }
            );
        }
        requestInfo.setClassMethod(String.format("%s.%s", proceedingJoinPoint.getSignature().getDeclaringTypeName(),
                proceedingJoinPoint.getSignature().getName()));
        requestInfo.setRequestParams(getRequestParamsByProceedingJoinPoint(proceedingJoinPoint));

        Object result = proceedingJoinPoint.proceed();
        String traceId = MDC.get(Constants.TRACE_ID);
        requestInfo.setResult(result);
        long timeCost = System.currentTimeMillis() - start;
        requestInfo.setTimeCost(timeCost);
        //thread history stack
        addThreadCallStack(requestInfo, traceId);
        if(timeCost>Constants.MAX_TIME_COST){
            log.warn("time cost is too long,time cost:{},threadStack:{}",timeCost,MDC.get(traceId));
        }

        switch (traceLog.level()) {
            case Constants.LOG_LEVEL_INFO:
                log.info("Request Info      : {}", JSON.toJSONString(requestInfo));
                break;
            case Constants.LOG_LEVEL_WARN:
                log.warn("Request Info      : {}", JSON.toJSONString(requestInfo));
                break;
            case Constants.LOG_LEVEL_ERROR:
                log.error("Request Info      : {}", JSON.toJSONString(requestInfo));
                break;
            default:
        }
        log.info("Request Info      : {}", JSON.toJSONString(requestInfo));
        return result;
    }

    private void addThreadCallStack(RequestInfo requestInfo, String traceId) {
        String threadHistory = MDC.get(traceId);
        if (StringUtils.isBlank(threadHistory)) {
            MDC.put(traceId, requestInfo.getClassMethod() + ":" + requestInfo.getTimeCost());
            return;
        }
        MDC.put(traceId, threadHistory + "," + requestInfo.getClassMethod() + ":" + requestInfo.getTimeCost());
    }



    @AfterThrowing(pointcut = "controllerPointCut()", throwing = "e")
    public void doAfterThrowInControllerPointCut(JoinPoint joinPoint, RuntimeException e) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        RequestErrorInfo requestErrorInfo = new RequestErrorInfo();
        requestErrorInfo.setIp(request.getRemoteAddr());
        requestErrorInfo.setUrl(request.getRequestURL().toString());
        requestErrorInfo.setHttpMethod(request.getMethod());
        requestErrorInfo.setClassMethod(String.format("%s.%s", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName()));
        requestErrorInfo.setRequestParams(getRequestParamsByJoinPoint(joinPoint));
        requestErrorInfo.setException(e);
        log.info("Error Request Info      : {}", JSON.toJSONString(requestErrorInfo));
    }


    /**
     * 获取入参
     *
     * @param proceedingJoinPoint
     * @return
     */
    private Map<String, Object> getRequestParamsByProceedingJoinPoint(ProceedingJoinPoint proceedingJoinPoint) {
        //参数名
        String[] paramNames = ((MethodSignature) proceedingJoinPoint.getSignature()).getParameterNames();
        //参数值
        Object[] paramValues = proceedingJoinPoint.getArgs();

        return buildRequestParam(paramNames, paramValues);
    }

    private Map<String, Object> getRequestParamsByJoinPoint(JoinPoint joinPoint) {
        //参数名
        String[] paramNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        //参数值
        Object[] paramValues = joinPoint.getArgs();

        return buildRequestParam(paramNames, paramValues);
    }

    private Map<String, Object> buildRequestParam(String[] paramNames, Object[] paramValues) {
        Map<String, Object> requestParams = new HashMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            Object value = paramValues[i];

            //如果是文件对象
            if (value instanceof MultipartFile) {
                MultipartFile file = (MultipartFile) value;
                value = file.getOriginalFilename();
            }

            //如果是批量文件上传
            if (value instanceof List) {
                try {
                    List<MultipartFile> multipartFiles = castList(value, MultipartFile.class);
                    if (multipartFiles != null) {
                        List<String> fileNames = new ArrayList<>();
                        for (MultipartFile file : multipartFiles) {
                            fileNames.add(file.getOriginalFilename());
                        }

                        requestParams.put(paramNames[i], fileNames);
                        break;
                    }
                } catch (ClassCastException e) {
                    //忽略不是文件类型的List
                }
            }

            requestParams.put(paramNames[i], value);
        }

        return requestParams;
    }

    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }

    @Data
    public class RequestInfo {
        private String ip;
        private String url;
        private String httpMethod;
        private String classMethod;
        private Object requestParams;
        private Object result;
        private Long timeCost;
    }

    @Data
    public class RequestErrorInfo {
        private String ip;
        private String url;
        private String httpMethod;
        private String classMethod;
        private Object requestParams;
        private RuntimeException exception;
    }


}
