package com.hy.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Log {
    public Object log(ProceedingJoinPoint point) throws Throwable {
        System.out.println("---前置自定义切面");
        Object proceed = point.proceed();//执行业务逻辑，获取需要返回的数据
        //return null 需要返回的数据相当于被拦截，不能返回，如果有需要数据返回必须设 return proceed；
        return proceed;
    }

}
