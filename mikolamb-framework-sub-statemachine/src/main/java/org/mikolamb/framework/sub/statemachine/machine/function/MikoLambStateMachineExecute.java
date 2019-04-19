package org.mikolamb.framework.sub.statemachine.machine.function;


/**
 * @description: 暴露接口
 * @author: Mr.WangGang
 * @create: 2018-12-07 上午 11:59
 **/
public interface MikoLambStateMachineExecute {
    public <T>Object execute(MikoLambStateMachineCurrentStatus statusProcess, String event, T data);
}
