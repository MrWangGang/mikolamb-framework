package org.mikolamb.framework.sub.taskmachine.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author WangGang
 * @Description //TODO 任务监听$
 * Created by WangGang on 2019/4/18 0018 13:41
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
public @interface MikoLambTaskMachineListener {
}
