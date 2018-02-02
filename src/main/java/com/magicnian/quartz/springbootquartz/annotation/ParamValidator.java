package com.magicnian.quartz.springbootquartz.annotation;

import java.lang.annotation.*;

/**
 * Created by liunn on 2018/2/2.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamValidator {

    /**
     * 字段名称
     * @return
     */
    String name() default "";

    /**
     * 是否必填
     * @return
     */
    boolean required() default false;
}
