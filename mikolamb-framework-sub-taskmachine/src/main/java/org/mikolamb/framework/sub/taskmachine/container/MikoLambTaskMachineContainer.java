package org.mikolamb.framework.sub.taskmachine.container;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.mikolamb.framework.sub.taskmachine.queue.MikoLambTaskQueueExecutor;

import java.lang.reflect.Method;

/**
 * @Author WangGang
 * @Description //TODO 任务机容器$
 * Created by WangGang on 2019/4/19 0019 14:41
 **/
@Data
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
public class MikoLambTaskMachineContainer<Model> {

    /*队列实例*/
    private MikoLambTaskQueueExecutor<Model> mikoLambTaskQueueExecutor;

    /*任务标识*/
    private String taskKey;

    /*最大工作线程数量*/
    private int maxExecuteThreadNum;

    /*最大工作队列容量*/
    private int maxExecuteQueueSize;

    /*每个任务最大耗时时间*/
    private int maxTaskExecuteTime;

    private Method method;

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(this == obj) return true;
        if(obj instanceof MikoLambTaskMachineContainer){
            MikoLambTaskMachineContainer mikoLambTaskMachineContainer =(MikoLambTaskMachineContainer)obj;
            if(taskKey.equals(mikoLambTaskMachineContainer.taskKey)) return true; // 只比较event
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((StringUtils.isBlank(taskKey)) ? 0 : taskKey.hashCode());
        return result;
    }

}

