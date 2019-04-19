package org.mikolamb.framework.sub.taskmachine.annotation;

import org.mikolamb.framework.sub.taskmachine.selector.MikoLambTaskMachineSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author WangGang
 * @Description //TODO 任务机开关$
 * Created by WangGang on 2019/4/18 0018 13:47
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import(MikoLambTaskMachineSelector.class)
public @interface EnableMikoLambTaskMachine {


}
