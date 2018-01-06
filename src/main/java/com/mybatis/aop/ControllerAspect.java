package com.mybatis.aop;

import com.google.gson.Gson;
import com.mybatis.config.DruidConfig;
import com.mybatis.response.ErrorCode;
import com.mybatis.response.RpcResult;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * aop controller打印请求信息切面
 *
 * Created by yunkai on 2017/6/12.
 */
@Aspect
@Component
public class ControllerAspect {

    private static final Logger logger = LoggerFactory.getLogger(DruidConfig.class);

    @Pointcut("execution(public * com.*.controller.*.*(..))")
    public void apiLog() {}

    @Pointcut("@annotation(com.mybatis.aop.AuthChecker)")
    public void checkAuth(){}

    @Pointcut("within(com.mybatis.service.impl.YunKaiUserServiceImpl)")
    public void timeCount(){}

    /**
     * 运行controller之前打印请求信息
     */
    @Before("apiLog()")
    public void doBefore(JoinPoint jp) {
        RequestAttributes attribute = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servlet = (ServletRequestAttributes) attribute;
        HttpServletRequest request = servlet.getRequest();
        //请求路径
        logger.info("请求路径：                   " + request.getRequestURI().toString());
        //请求方式
        logger.info("请求方式：                   " + request.getMethod());
        //请求ip
        logger.info("请求IP：                     " + request.getRemoteAddr());
        //请求类
        logger.info("请求类：                     " + jp.getSignature().getDeclaringTypeName());
        //请求方法
        logger.info("请求方法：                   " + jp.getSignature().getName());
        //请求参数
        if(request.getMethod().equals("GET")){
            String queryString = request.getQueryString();
            if(!StringUtils.isEmpty(queryString)) {
                String[] stringArr = queryString.split("&");
                if(stringArr != null && stringArr.length > 0) {
                    for(int i = 0; i < stringArr.length; i++) {
                        String[] params = stringArr[i].split("=");
                        logger.info("请求参数名：     " + params[0] + "         请求参数值：     " + params[1]);
                    }
                }
            }
        }else {
            Object[] valueParams = jp.getArgs();
            Signature signature = jp.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            String[] stringParams = methodSignature.getParameterNames();
            if(stringParams != null && stringParams.length > 0 ) {
                for(int i =0; i < stringParams.length; i++) {
                    logger.info("请求参数名：     " + stringParams[i] + "         请求参数值：     " + valueParams[i]);
                }
            }
        }
    }

    //方法权限验证，是否有权限执行
    @Around("checkAuth()")
    public Object checkAuth(ProceedingJoinPoint jp){
        try {
            RequestAttributes attribute = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes servlet = (ServletRequestAttributes) attribute;
            HttpServletRequest request = servlet.getRequest();
            String X_userToken = getXUserToken(request);
            if(StringUtils.isEmpty(X_userToken)){
                logger.info("权限不合法");
                return RpcResult.ofError(ErrorCode.BIZ_ERROR.getCode(), ErrorCode.BIZ_ERROR.getMsg("权限不合法"));
            }
            return jp.proceed();
        }catch (Throwable e){
            logger.error("aop check auth error : {}", e);
            return RpcResult.ofError(ErrorCode.BIZ_ERROR.getCode(), ErrorCode.BIZ_ERROR.getMsg("错误"));
        }
    }

    private String getXUserToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            return "";
        }
        for(Cookie cookie : cookies){
            if(cookie.getName().equalsIgnoreCase("authToken")){
                logger.info("cookie value = {}", cookie.getValue());
                return cookie.getValue();
            }
        }
        return "";
    }

    @Around("timeCount()")
    public Object timeCount(ProceedingJoinPoint jp){
        try{
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            Object retVal = jp.proceed();
            stopWatch.stop();
            logger.info(" aop time count = {}", stopWatch.getTotalTimeMillis());
            return retVal;
        } catch (Throwable e) {
            logger.info("aop time count error : {}", e);
            return RpcResult.ofError(ErrorCode.BIZ_ERROR.getCode(), ErrorCode.BIZ_ERROR.getMsg("统计调用耗时错误"));
        }
    }
}
