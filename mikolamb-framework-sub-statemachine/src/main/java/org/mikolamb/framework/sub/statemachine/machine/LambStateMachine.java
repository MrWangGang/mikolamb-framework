package org.mikolamb.framework.sub.statemachine.machine;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.mikolamb.framework.common.exception.EventException;
import org.mikolamb.framework.common.exception.basic.GlobalException;
import org.mikolamb.framework.sub.statemachine.container.LambStateMachineContainer;
import org.mikolamb.framework.sub.statemachine.container.LambStateMachineTransition;
import org.mikolamb.framework.sub.statemachine.function.LambStateMachineCurrentStatus;
import org.mikolamb.framework.sub.statemachine.executor.LambStateMachineExecute;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mikolamb.framework.common.enums.ExceptionEnum.*;


/**
 * @description: 状态机
 * @author: Mr.WangGang
 * @create: 2018-11-21 下午 1:09
 **/
@Component
public class LambStateMachine implements LambStateMachineExecute {

    private Set<LambStateMachineContainer> containers;

    public LambStateMachine load(Set<LambStateMachineContainer> containers){
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
    public <T>Object execute(LambStateMachineCurrentStatus statusProcess, String event, T data) {
        if(StringUtils.isBlank(event))                                 throw new EventException(ES00000033);
        containers = Optional.ofNullable(containers).orElseThrow(()-> new EventException(ES00000030));
        LambStateMachineContainer lambStateMachineContainer = containers.stream().filter(e->event.equals(e.getEvent())).findFirst().orElseThrow(()-> new EventException(ES00000033));
        if(!lambStateMachineContainer.isInitial())if(statusProcess == null)throw new EventException(ES00000032);
        String currentStatus = !lambStateMachineContainer.isInitial()?statusProcess.currentStatus():null;
        if(!lambStateMachineContainer.isInitial())if(StringUtils.isBlank(currentStatus)) throw new EventException(ES00000032);
        if(StringUtils.isBlank(lambStateMachineContainer.getTarget())) throw new EventException(ES00000030);
        if(StringUtils.isBlank(lambStateMachineContainer.getEvent()))  throw new EventException(ES00000030);
        if(StringUtils.isBlank(lambStateMachineContainer.getSource())) throw new EventException(ES00000030);
        if(lambStateMachineContainer.getGlobalException() == null)     throw new EventException(ES00000030);
        if(lambStateMachineContainer.getMethod() == null)              throw new EventException(ES00000031);
        if(lambStateMachineContainer.getListener() == null)            throw new EventException(ES00000031);
        if(!lambStateMachineContainer.isInitial())if(!currentStatus.equals(lambStateMachineContainer.getSource()))throw lambStateMachineContainer.getGlobalException();
        List paramList = Lists.newLinkedList();
        Arrays.stream(lambStateMachineContainer.getMethod().getParameterTypes())
                .forEach((e)->{
                    if(e.equals(LambStateMachineTransition.class)){
                        LambStateMachineTransition transition = new LambStateMachineTransition();
                        transition.setEvent(event);
                        transition.setSource(lambStateMachineContainer.getSource());
                        transition.setTarget(lambStateMachineContainer.getTarget());
                        transition.setData(Optional.ofNullable(data==null?null:data));
                        paramList.add(transition);
                    }else {
                        paramList.add(null);
                    }
                });
        try {
            return lambStateMachineContainer.getMethod().invoke(lambStateMachineContainer.getListener(),paramList.toArray());
        }catch (Exception e) {
            if(e.getCause()!=null){
                if(e.getCause().getClass().getSuperclass() == GlobalException.class){
                    throw new GlobalException(((GlobalException)e.getCause()).getCode(),((GlobalException)e.getCause()).getMessage());
                }else {
                    throw new EventException(ES00000021);
                }
            }else{
                throw new EventException(ES00000021);
            }
        }
    }


}
