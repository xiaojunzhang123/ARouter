package com.zxj.arouter_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 路由的路径值必须大于两级，如/xx/xx
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Route {

    /**
     * 路由的路径
     * @return
     */
    String path();

    /**
     * 路由的分组
     * @return
     */
    String group() default "";
}