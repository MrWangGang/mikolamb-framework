package org.mikolamb.framework.sub.taskmachine.selector;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.mikolamb.framework.common.exception.MikoLambEventException;
import org.mikolamb.framework.sub.taskmachine.annotation.MikoLambTaskMachineExecutor;
import org.mikolamb.framework.sub.taskmachine.annotation.MikoLambTaskMachineListener;
import org.mikolamb.framework.sub.taskmachine.container.MikoLambTaskMachineContainer;
import org.mikolamb.framework.sub.taskmachine.machine.MikoLambTaskMachine;
import org.mikolamb.framework.sub.taskmachine.queue.MikoLambTaskQueueExecutor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static org.mikolamb.framework.common.enums.MikoLambExceptionEnum.*;

/**
 * @Author WangGang
 * @Description //TODO 任务机加载器$
 * Created by WangGang on 2019/4/18 0018 13:40
 **/
public class MikoLambTaskMachineSelector implements BeanPostProcessor {


    @Resource
    private MikoLambTaskMachine mikoLambTaskMachine;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Optional<MikoLambTaskMachineListener> mikoLambTaskMachineListener = Optional.ofNullable(bean.getClass().getDeclaredAnnotation(MikoLambTaskMachineListener.class));
        if(mikoLambTaskMachineListener.isPresent()){
            //次类为任务机监听类,读取数据,进行队列绑定
            Method[] method = bean.getClass().getDeclaredMethods();
            Set<MikoLambTaskMachineContainer> containers = Sets.newHashSet();
            Arrays.stream(method).filter(e->Optional.ofNullable(e.getDeclaredAnnotation(MikoLambTaskMachineExecutor.class)).isPresent()) .forEach((e)->{
                Optional<MikoLambTaskMachineExecutor> mikoLambTaskMachineExecutor = Optional.ofNullable(e.getDeclaredAnnotation(MikoLambTaskMachineExecutor.class));
                if(mikoLambTaskMachineExecutor.isPresent()){
                    MikoLambTaskMachineExecutor annotation = mikoLambTaskMachineExecutor.get();
                    String taskKey = annotation.taskKey();
                    int maxExecuteThreadNum = annotation.maxExecuteThreadNum();
                    int maxExecuteQueueSize = annotation.maxExecuteQueueSize();
                    int maxTaskExecuteTime = annotation.maxTaskExecuteTime();
                    if(StringUtils.isBlank(taskKey))throw new MikoLambEventException(ES00000053);
                    if(maxExecuteThreadNum<=0 && maxExecuteThreadNum%2!=0)throw new MikoLambEventException(ES00000054);
                    if(maxExecuteQueueSize<=0)throw new MikoLambEventException(ES00000055);
                    if(maxTaskExecuteTime<=1000)throw new MikoLambEventException(ES00000056);
                    if(annotation.consumerType()==null)throw new MikoLambEventException(ES00000062);
                    MikoLambTaskQueueExecutor mikoLambTaskQueueExecutor =  MikoLambTaskQueueExecutor.builder()
                            .taskKey(taskKey)
                            .consumer(e)
                            .fair(annotation.fair())
                            .maxExecuteQueueSize(annotation.maxExecuteQueueSize())
                            .maxExecuteThreadNum(annotation.maxExecuteThreadNum())
                            .maxTaskExecuteTime(annotation.maxTaskExecuteTime())
                            .build();
                    mikoLambTaskQueueExecutor.load();
                    MikoLambTaskMachineContainer container =  MikoLambTaskMachineContainer.builder().build();
                    container.setTaskKey(taskKey);
                    container.setMaxExecuteQueueSize(maxExecuteQueueSize);
                    container.setMaxExecuteThreadNum(maxExecuteThreadNum);
                    container.setMaxTaskExecuteTime(maxTaskExecuteTime);
                    container.setMikoLambTaskQueueExecutor(mikoLambTaskQueueExecutor);
                    container.setMethod(e);
                    containers.add(container);
                }
            });
            if(containers.size()!=0){
                mikoLambTaskMachine.load(containers);
            }
        }
        return bean;
    }
}
