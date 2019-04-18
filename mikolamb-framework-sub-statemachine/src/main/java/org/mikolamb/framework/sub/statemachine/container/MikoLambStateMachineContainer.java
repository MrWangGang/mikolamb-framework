package org.mikolamb.framework.sub.statemachine.container;

import lombok.Data;
import org.mikolamb.framework.common.exception.basic.MikoLambGlobalException;

import java.lang.reflect.Method;

/**
 * @description: 状态机扭转容器
 * @author: Mr.WangGang
 * @create: 2018-11-21 下午 1:12
 **/
@Data
public class MikoLambStateMachineContainer {
    private String source;

    private String target;

    private String event;

    private MikoLambGlobalException mikoLambGlobalException;

    private Method method;

    private Object listener;

    private boolean initial = false;

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(this == obj) return true;
        if(obj instanceof MikoLambStateMachineContainer){
            MikoLambStateMachineContainer mikoLambStateMachineContainer =(MikoLambStateMachineContainer)obj;
          if(mikoLambStateMachineContainer.event == mikoLambStateMachineContainer.event) return true; // 只比较event
        }
        return false;
    }

    @Override
    public int hashCode() {
        return event.hashCode();
    }
}
