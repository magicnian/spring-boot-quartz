package com.magicnian.quartz.springbootquartz.aop;

import com.magicnian.quartz.springbootquartz.enums.ResponseCode;
import com.magicnian.quartz.springbootquartz.exception.ServiceException;
import com.magicnian.quartz.springbootquartz.response.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by liunn on 2018/2/2.
 */
@Aspect
@Component
@Slf4j
public class SpiderResponseAop {

    @Pointcut("execution(public * com.magicnian.quartz.springbootquartz.rest.*.*(..))")
    public void responsePointCut() {
    }

    @Before("responsePointCut()")
    public void before(JoinPoint jp) throws Throwable {
        Object[] objects = jp.getArgs();
        if (null == objects) {
            throw new ServiceException(ResponseCode.ILLEGAL_ARGS.getCode(), ResponseCode.ILLEGAL_ARGS.getMsg());
        }
        if (0 != objects.length) {
            for (Object o : objects
                    ) {
                if (null == o) {
                    throw new ServiceException(ResponseCode.ILLEGAL_ARGS.getCode(), ResponseCode.ILLEGAL_ARGS.getMsg());
                }
            }
        }
    }

    @Around("responsePointCut()")
    public Object routeDataSource(ProceedingJoinPoint joinPoint) throws Throwable {
        ResponseModel responseModel = new ResponseModel();
        try {
            return joinPoint.proceed();
        } catch (ServiceException e) {
            responseModel.setCode(e.getCode());
            responseModel.setMsg(e.getMsg());
        } catch (Exception ex) {
            log.warn("unexpected exception happened...", ex);
            responseModel.setCode(ResponseCode.FAIL.getCode());
            responseModel.setMsg(ResponseCode.FAIL.getMsg());
        }

        return responseModel;

    }


}
