package com.jiuxian.framework.mvc.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by luqingrun on 16/8/23.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

public @interface Login {
    /**
     * 是否需要登录
     * @return
     */
    boolean value() default true;

}
