package com.wzb.support.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者 文泽彪
 * 时间 2016/5/20 0020.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {
    /**
     * 数据表名称注解，默认值为类名称
     *
     * @return
     */
    public String tableName() default "className";
}
