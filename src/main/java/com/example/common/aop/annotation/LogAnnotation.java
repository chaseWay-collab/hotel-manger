package com.example.common.aop.annotation;

import java.lang.annotation.*;

/**
 * @ClassName: LogAnnotation
 * @Description: 自定义日志注解
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Target(ElementType.METHOD)  // 定义注解的作用目标方法
@Retention(RetentionPolicy.RUNTIME)  // 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Documented  // 说明该注解将被包含在javadoc中
public @interface LogAnnotation {
    /* 模块 */
    String title() default "";

    /* 功能 */
    String action() default "";
}


/*@Documented//说明该注解将被包含在javadoc中
//@Retention: 定义注解的保留策略,
@Retention(RUNTIME)// 注解会在class字节码文件中存在，在运行时可以通过反射获取到
//@Target：定义注解的作用目标
@Target({METHOD,PARAMETER})// 方法和方法参数
@Inherited//说明子类可以继承父类中的该注解
public @interface LogAnnotation {

     * 如果注解只有一个属性，那么肯定是赋值给该属性。
     * 如果注解有多个属性，而且前提是这多个属性都有默认值，那么你不写注解名赋值，会赋值给名字为“value”这属性。
     * 如果注解有多个属性，其中有没有设置默认值的属性，那么当你不写属性名进行赋值的时候，是会报错的。

    //@AliasFor("")起别名
    int value() default 000;
}*/
