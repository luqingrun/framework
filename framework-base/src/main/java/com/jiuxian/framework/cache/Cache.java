package com.jiuxian.framework.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
    /**
     * 失效时间,以秒为单位, 默认是24小时
     * @return
     */
    int value() default 24 * 60 * 60;
}
