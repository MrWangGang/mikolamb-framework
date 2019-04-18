package org.mikolamb.framework.sub.statemachine.machine;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.mikolamb.framework.common.exception.MikoLambEventException;
import org.mikolamb.framework.common.exception.basic.MikoLambGlobalException;
import org.mikolamb.framework.sub.statemachine.container.MikoLambStateMachineContainer;
import org.mikolamb.framework.sub.statemachine.container.MikoLambStateMachineTransition;
import org.mikolamb.framework.sub.statemachine.function.MikoLambStateMachineCurrentStatus;
import org.mikolamb.framework.sub.statemachine.executor.MikoLambStateMachineExecute;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mikolamb.framework.common.enums.MikoLambExceptionEnum.*;


/**
 * @description: 状态机
 * @author: Mr.WangGang
 * @create: 2018-11-21 下午 1:09
 **/
@Component
public class MikoLambStateMachine implements MikoLambStateMachineExecute {

    private Set<MikoLambStateMachineContainer> containers;

    public MikoLambStateMachine load(Set<MikoLambStateMachineContainer> containers){
        if(this.containers!=null){
            if(this.containers.size()!=0){
                this.containers.addAll(containers);
            }else {
                this.containers = containers;
            }
        }else {
            this.containers = containers;
        }
        return this;
    }
    @Override
    public <T>Object execute(MikoLambStateMachineCurrentStatus statusProcess, String event, T data) {
        if(StringUtils.isBlank(event))                                 throw new MikoLambEventException(ES00000033);
        containers = Optional.ofNullable(containers).orElseThrow(()-> new MikoLambEventException(ES00000030));
        MikoLambStateMachineContainer mikoLambStateMachineContainer = containers.stream().filter(e->event.equals(e.getEvent())).findFirst().orElseThrow(()-> new MikoLambEventException(ES00000033));
        if(!mikoLambStateMachineContainer.isInitial())if(statusProcess == null)throw new MikoLambEventException(ES00000032);
        String currentStatus = !mikoLambStateMachineContainer.isInitial()?statusProcess.currentStatus():null;
        if(!mikoLambStateMachineContainer.isInitial())if(StringUtils.isBlank(currentStatus)) throw new MikoLambEventException(ES00000032);
        if(StringUtils.isBlank(mikoLambStateMachineContainer.getTarget())) throw new MikoLambEventException(ES00000030);
        if(StringUtils.isBlank(mikoLambStateMachineContainer.getEvent()))  throw new MikoLambEventException(ES00000030);
        if(StringUtils.isBlank(mikoLambStateMachineContainer.getSource())) throw new MikoLambEventException(ES00000030);
        if(mikoLambStateMachineContainer.getMikoLambGlobalException() == null)     throw new MikoLambEventException(ES00000030);
        if(mikoLambStateMachineContainer.getMethod() == null)              throw new MikoLambEventException(ES00000031);
        if(mikoLambStateMachineContainer.getListener() == null)            throw new MikoLambEventException(ES00000031);
        if(!mikoLambStateMachineContainer.isInitial())if(!currentStatus.equals(mikoLambStateMachineContainer.getSource()))throw mikoLambStateMachineContainer.getMikoLambGlobalException();
        List paramList = Lists.newLinkedList();
        Arrays.stream(mikoLambStateMachineContainer.getMethod().getParameterTypes())
                .forEach((e)->{
                    if(e.equals(MikoLambStateMachineTransition.class)){
                        MikoLambStateMachineTransition transition = new MikoLambStateMachineTransition();
                        transition.setEvent(event);
                        transition.setSource(mikoLambStateMachineContainer.getSource());
                        transition.setTarget(mikoLambStateMachineContainer.getTarget());
                        transition.setData(Optional.ofNullable(data==null?null:data));
                        paramList.add(transition);
                    }else {
                        paramList.add(null);
                    }
                });
        try {
            return mikoLambStateMachineContainer.getMethod().invoke(mikoLambStateMachineContainer.getListener(),paramList.toArray());
        }catch (Exception e) {
            if(e.getCause()!=null){
                if(e.getCause().getClass().getSuperclass() == MikoLambGlobalException.class){
                    throw new MikoLambGlobalException(((MikoLambGlobalException)e.getCause()).getCode(),((MikoLambGlobalException)e.getCause()).getMessage());
                }else {
                    throw new MikoLambEventException(ES00000021);
                }
            }else{
                throw new MikoLambEventException(ES00000021);
            }
        }
    }


}
