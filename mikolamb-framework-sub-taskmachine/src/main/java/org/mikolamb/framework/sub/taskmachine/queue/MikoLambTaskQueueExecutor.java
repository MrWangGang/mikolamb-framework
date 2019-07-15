package org.mikolamb.framework.sub.taskmachine.queue;

import lombok.*;
import org.mikolamb.framework.common.exception.MikoLambEventException;
import org.mikolamb.framework.sub.taskmachine.enums.ConsumerTypeEnum;
import org.mikolamb.framework.sub.taskmachine.queue.function.MikoLambTaskQueueBlockOperation;
import org.mikolamb.framework.sub.taskmachine.queue.function.MikoLambTaskQueueUnBlockOperation;
import org.mikolamb.framework.sub.taskmachine.queue.support.MikoLambTaskQueueSupportOperation;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.mikolamb.framework.common.enums.MikoLambExceptionEnum.ES00000050;
import static org.mikolamb.framework.common.enums.MikoLambExceptionEnum.ES00000052;

/**
 * @Author WangGang
 * @Description //TODO 任务队列阻塞和非阻塞支持$
 * Created by WangGang on 2019/4/18 0018 15:25
 **/
@Data
public class MikoLambTaskQueueExecutor<Model> extends MikoLambTaskQueueSupportOperation<MikoLambTaskQueue<Model>, Model> {

    @Builder(toBuilder = true)
    public MikoLambTaskQueueExecutor(String taskKey, boolean fair, int maxExecuteThreadNum, int maxExecuteQueueSize, int maxTaskExecuteTime, Method consumer, ConsumerTypeEnum consumerType){
        super.setTaskKey(taskKey);
        super.setFair(fair);
        super.setMaxExecuteThreadNum(maxExecuteThreadNum);
        super.setMaxExecuteQueueSize(maxExecuteQueueSize);
        super.setMaxTaskExecuteTime(maxTaskExecuteTime);
        super.setConsumer(consumer);
        super.setConsumerType(consumerType);
    }

    @Override
    protected MikoLambTaskQueueBlockOperation<Model> blockQueueInstructionSet() {
        return new MikoLambTaskQueueBlockOperation<Model>() {
            @Override
            public void push(Model model) {
                try {
                    MikoLambTaskQueueExecutor.super.getQueue().put(model);
                } catch (InterruptedException e) {
                    throw new MikoLambEventException(ES00000052);
                }
            }

            @Override
            public Optional<Model> pull() {
                try {
                    return Optional.ofNullable(MikoLambTaskQueueExecutor.super.getQueue().take());
                } catch (InterruptedException e) {
                    throw new MikoLambEventException(ES00000052);
                }
            }
        };
    }

    @Override
    protected MikoLambTaskQueueUnBlockOperation<Model> unblockQueueInstructionSet() {
        return new MikoLambTaskQueueUnBlockOperation<Model>() {
            @Override
            public boolean push(Model model) {
               return MikoLambTaskQueueExecutor.super.getQueue().offer(model);
            }

            @Override
            public Optional<Model> pull() {
                return Optional.ofNullable(MikoLambTaskQueueExecutor.super.getQueue().poll());
            }
        };
    }

    @Override
    protected MikoLambTaskQueue<Model> buildQueue() {
        Optional.ofNullable(super.getMaxExecuteQueueSize()).orElseThrow(()->new MikoLambEventException(ES00000050));
        if(super.getMaxExecuteQueueSize()<=0)throw new MikoLambEventException(ES00000050);
         return new MikoLambTaskQueue<Model>(super.getMaxExecuteQueueSize(),super.isFair());
    }

}
