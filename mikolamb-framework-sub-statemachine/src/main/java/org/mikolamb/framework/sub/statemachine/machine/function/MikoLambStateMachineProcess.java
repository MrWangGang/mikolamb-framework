package org.mikolamb.framework.sub.statemachine.machine.function;

/**
 * @description: 执行流程
 * @author: Mr.WangGang
 * @create: 2018-11-21 下午 3:09
 **/
public interface MikoLambStateMachineProcess<T> {
    public <T>T process(String targetState);
}
