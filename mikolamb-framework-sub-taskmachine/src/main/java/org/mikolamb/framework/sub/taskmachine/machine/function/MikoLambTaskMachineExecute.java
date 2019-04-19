package org.mikolamb.framework.sub.taskmachine.machine.function;


/**
 * @description: 暴露接口
 * @author: Mr.WangGang
 * @create: 2018-12-07 上午 11:59
 **/
public interface MikoLambTaskMachineExecute<Model> {
    /*阻塞推送*/
    public <Model>void blockPush(Model model,String taskKey);

    /*非阻塞推送*/
    public <Model>void unBlockPush(Model model,String taskKey);

}
