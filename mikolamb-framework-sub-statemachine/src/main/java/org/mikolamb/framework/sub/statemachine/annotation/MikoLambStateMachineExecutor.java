package org.mikolamb.framework.sub.statemachine.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @Author WangGang
 * @Description //TODO 初始化绑定事件$
 * Created by WangGang on 2019/4/18 0018 13:41
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MikoLambStateMachineExecutor {
    String source() default "";

    String target() default "";

    String event();

    String error();

    String initial() default "";

}
