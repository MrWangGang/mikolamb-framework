package org.mikolamb.framework.sub.taskmachine.queue;

import lombok.Builder;
import lombok.Data;
import org.mikolamb.framework.common.exception.MikoLambEventException;
import org.mikolamb.framework.sub.taskmachine.queue.function.MikoLambTaskQueueBlockOperation;
import org.mikolamb.framework.sub.taskmachine.queue.function.MikoLambTaskQueueUnBlockOperation;
import org.mikolamb.framework.sub.taskmachine.queue.support.MikoLambTaskQueueSupportOperation;

import java.util.Optional;

import static org.mikolamb.framework.common.enums.MikoLambExceptionEnum.ES00000050;
import static org.mikolamb.framework.common.enums.MikoLambExceptionEnum.ES00000052;

/**
 * @Author WangGang
 * @Description //TODO 任务队列阻塞和非阻塞支持$
 * Created by WangGang on 2019/4/18 0018 15:25
 **/
@Data
@Builder
public class MikoLambTaskQueueExecutor<Model> extends MikoLambTaskQueueSupportOperation<MikoLambTaskQueue<Model>, Model> {


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
    protected MikoLambTaskQueue<Model> buildQueue(Integer size, boolean fair) {
        Optional.ofNullable(size).orElseThrow(()->new MikoLambEventException(ES00000050));
        if(size.intValue()<=0)throw new MikoLambEventException(ES00000050);
         return new MikoLambTaskQueue<Model>(size,true);
    }
}
