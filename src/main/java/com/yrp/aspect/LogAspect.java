package com.yrp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.hibernate.mapping.Join;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Auther: 南迪叶先生:https://www.cnblogs.com/ye888/
 * @Date: 2019/9/26
 * @Description: com.yrp.aspect
 * @version: 1.0
 */
@Aspect
@Component
public class LogAspect {

     private  final Logger logger = LoggerFactory.getLogger(this.getClass());

     //拦截所要控制器
     @Pointcut("execution(* com.yrp.controller.*.*(..))")
     public void log(){}


     @Before("log()")
     public void doBefore(JoinPoint joinPoint){
          // 获取request对象
          ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
          HttpServletRequest request = attributes.getRequest();
          // URL
          String url = request.getRequestURL().toString();
          // IP
          String ip = request.getRemoteAddr();
          // 方法全名 = 类名.方法名
          String classMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
          // 请求对象
          Object[] args  = joinPoint.getArgs();
          RequestLog requestLog = new RequestLog(url,ip,classMethod,args);

          logger.info("Request : {}",requestLog);
     }

     @After("log()")
     public void doAfter(){
//          logger.info("-------doafter-----------");
     }

     @AfterReturning(returning = "result",pointcut = "log()")
     public void doAfterReturn(Object result){
          logger.info("控制器方法名称Result : {}",result);
     }

     /**
      * 内部类:用于存放请求信息,用日志的方式将其记录下来
      */
     private class RequestLog{
          /**
           * 请求地址
           */
          private String url;
          /**
           * 请求ip
           */
          private String ip;
          /**
           * 请求方法
           */
          private String classMethod;
          /**
           * 请求参数集合
           */
          private Object[] args;

          public RequestLog(String url, String ip, String classMethod, Object[] args) {
               this.url = url;
               this.ip = ip;
               this.classMethod = classMethod;
               this.args = args;
          }

          @Override
          public String toString() {
               return "RequestLog{" +
                       "url='" + url + '\'' +
                       ", ip='" + ip + '\'' +
                       ", classMethod='" + classMethod + '\'' +
                       ", args=" + Arrays.toString(args) +
                       '}';
          }
     }


}
