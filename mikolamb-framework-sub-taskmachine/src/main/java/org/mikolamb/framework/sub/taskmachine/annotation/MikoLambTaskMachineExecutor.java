package org.mikolamb.framework.sub.taskmachine.annotation;

import org.mikolamb.framework.sub.taskmachine.enums.ConsumerTypeEnum;

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
public @interface MikoLambTaskMachineExecutor {

    /*任务标识*/
    String taskKey();

    /*最大工作线程数量*/
    int maxExecuteThreadNum();

    /*最大工作队列容量*/
    int maxExecuteQueueSize();

    /*每个任务最大耗时时间*/
    int maxTaskExecuteTime();

    boolean fair();

    ConsumerTypeEnum consumerType();
}
