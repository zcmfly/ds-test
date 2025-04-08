package com.zhou;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Component
@Aspect
public class ServiceLogAspect {

    @Around("execution(* com.zhou.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("time record");

        Object proceed = joinPoint.proceed();
        String point = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName();

        stopWatch.stop();
        //打印任务的耗时统计
        log.info(stopWatch.prettyPrint());
        log.info(stopWatch.shortSummary());

        //任务信息总揽
        log.info(point+" 耗时："+stopWatch.getTotalTimeMillis());
        log.info("任务总数："+stopWatch.getTaskCount());
        return proceed;
    }

}
