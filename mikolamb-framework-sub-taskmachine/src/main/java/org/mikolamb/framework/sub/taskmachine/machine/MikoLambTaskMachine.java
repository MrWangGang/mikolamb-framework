package org.mikolamb.framework.sub.taskmachine.machine;

import org.apache.commons.lang3.StringUtils;
import org.mikolamb.framework.common.exception.MikoLambEventException;
import org.mikolamb.framework.sub.taskmachine.container.MikoLambTaskMachineContainer;
import org.mikolamb.framework.sub.taskmachine.machine.function.MikoLambTaskMachineExecute;
import org.mikolamb.framework.sub.taskmachine.queue.MikoLambTaskQueueExecutor;
import org.springframework.stereotype.Component;
import org.w3c.dom.events.EventException;

import java.util.Optional;
import java.util.Set;

import static org.mikolamb.framework.common.enums.MikoLambExceptionEnum.*;

/**
 * @Author WangGang
 * @Description //TODO 任务机$
 * Created by WangGang on 2019/4/19 0019 15:39
 **/
@Component
public class MikoLambTaskMachine<Model> implements MikoLambTaskMachineExecute<Model> {
    private Set<MikoLambTaskMachineContainer<Model>> containers;

    public MikoLambTaskMachine load(Set<MikoLambTaskMachineContainer<Model>> containers){
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

    private <Model>MikoLambTaskMachineContainer<Model> matchContainer(Model model,String taskKey){
        if(StringUtils.isBlank(taskKey))throw new MikoLambEventException(ES00000057);
        Optional.ofNullable(model).orElseThrow(()->new MikoLambEventException(ES00000058));
        Optional.ofNullable(containers).orElseThrow(()->new MikoLambEventException(ES00000059));
        //找到执行器
        MikoLambTaskMachineContainer modelMikoLambTaskMachineContainer = containers.stream().filter((e)->e.getTaskKey().equals(taskKey)).findFirst().orElseThrow(()->new MikoLambEventException(ES00000060));
        return modelMikoLambTaskMachineContainer;
    }


    private <Model>MikoLambTaskQueueExecutor<Model> getExecutor(Model model, String taskKey){
        MikoLambTaskMachineContainer<Model> mikoLambTaskMachineContainer = matchContainer(model,taskKey);
        MikoLambTaskQueueExecutor<Model> executor = mikoLambTaskMachineContainer.getMikoLambTaskQueueExecutor();
        Optional.ofNullable(executor).orElseThrow(()->new MikoLambEventException(ES00000060));
        return executor;
    }

    @Override
    public <Model> void blockPush(Model model, String taskKey) {
        getExecutor(model,taskKey).block().push(model);
    }


    @Override
    public <Model> void unBlockPush(Model model, String taskKey) {
        getExecutor(model,taskKey).unblock().push(model);
    }
}
