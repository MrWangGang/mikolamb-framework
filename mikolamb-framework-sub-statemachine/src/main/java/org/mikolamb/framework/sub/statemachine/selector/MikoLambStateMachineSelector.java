package org.mikolamb.framework.sub.statemachine.selector;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.mikolamb.framework.common.exception.MikoLambEventException;
import org.mikolamb.framework.common.exception.basic.MikoLambGlobalException;
import org.mikolamb.framework.sub.statemachine.annotation.MikoLambStateMachineExecutor;
import org.mikolamb.framework.sub.statemachine.annotation.MikoLambStateMachineListener;
import org.mikolamb.framework.sub.statemachine.container.MikoLambStateMachineContainer;
import org.mikolamb.framework.sub.statemachine.machine.MikoLambStateMachine;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static org.mikolamb.framework.common.enums.MikoLambExceptionEnum.ES00000030;
import static org.mikolamb.framework.common.enums.MikoLambExceptionEnum.ES00000034;

/**
 * @description: 获取LambStateMachineListener注解的bean
 * @author: Mr.WangGang
 * @create: 2018-11-22 下午 1:30
 **/
public class MikoLambStateMachineSelector implements BeanPostProcessor {

    @Resource
    private MikoLambStateMachine mikoLambStateMachine;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Optional<MikoLambStateMachineListener> mikoLambStateMachineListener = Optional.ofNullable(bean.getClass().getDeclaredAnnotation(MikoLambStateMachineListener.class));
        if(mikoLambStateMachineListener.isPresent()){
            Method[] method = bean.getClass().getDeclaredMethods();
            Set<MikoLambStateMachineContainer> containers = Sets.newHashSet();
            Arrays.stream(method).filter(e->Optional.ofNullable(e.getDeclaredAnnotation(MikoLambStateMachineExecutor.class)).isPresent())
                    .forEach((e)->{
                        Optional<MikoLambStateMachineExecutor> mikoLambStateMachineExecutor = Optional.ofNullable(e.getDeclaredAnnotation(MikoLambStateMachineExecutor.class));
                        if(mikoLambStateMachineExecutor.isPresent()){
                            MikoLambStateMachineExecutor annotation = mikoLambStateMachineExecutor.get();
                            String initial = annotation.initial();
                            String source = annotation.source();
                            String target = annotation.target();
                            String event = annotation.event();
                            String error = annotation.error();
                            if(StringUtils.isNotBlank(initial)){
                                source = initial;
                                target = initial;
                            }
                            if(StringUtils.isBlank(source)) throw new MikoLambEventException(ES00000030);
                            if(StringUtils.isBlank(target)) throw new MikoLambEventException(ES00000030);
                            if(StringUtils.isBlank(event))  throw new MikoLambEventException(ES00000030);
                            MikoLambStateMachineContainer container = new MikoLambStateMachineContainer();
                            container.setSource(source);
                            container.setTarget(target);
                            container.setEvent(event);
                            container.setMikoLambGlobalException(StringUtils.isBlank(error)?new MikoLambEventException(ES00000034):new MikoLambGlobalException(ES00000034.getCode(),error));
                            container.setMethod(e);
                            container.setListener(bean);
                            if(StringUtils.isNotBlank(initial)){
                                container.setInitial(true);
                            }
                            containers.add(container);
                            }
                    });
            if(containers.size()!=0){
                mikoLambStateMachine.load(containers);
            }
        }
        return bean;
    }
}
