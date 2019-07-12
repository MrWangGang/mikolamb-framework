package org.mikolamb.framework.sub.taskmachine.queue.support;

import lombok.Data;
import org.mikolamb.framework.common.exception.MikoLambEventException;
import org.mikolamb.framework.sub.taskmachine.queue.function.MikoLambAbstractTaskQueue;
import org.mikolamb.framework.sub.taskmachine.queue.function.MikoLambTaskQueueBlockOperation;
import org.mikolamb.framework.sub.taskmachine.queue.function.MikoLambTaskQueueUnBlockOperation;

import java.util.Optional;

import static org.mikolamb.framework.common.enums.MikoLambExceptionEnum.ES00000050;
import static org.mikolamb.framework.common.enums.MikoLambExceptionEnum.ES00000051;

/**
 * @Author WangGang
 * @Description //TODO 队列操作者$
 * Created by WangGang on 2019/4/18 0018 15:18
 **/
@Data
public abstract class MikoLambTaskQueueSupportOperation<Q extends MikoLambAbstractTaskQueue<M>,M> {

     private Q queue;

     private MikoLambTaskQueueSupportOperation(Integer size,boolean fair){
        if(size<=0)throw new MikoLambEventException(ES00000050);
         this.queue = buildQueue(size,fair);
    }

    private void supperQueueAvailable(){
        Optional.ofNullable(this.getQueue()).orElseThrow(()->new MikoLambEventException(ES00000051));
    }

    /*阻塞操作*/
    public MikoLambTaskQueueBlockOperation<M> block(){
        supperQueueAvailable();
        return Optional.ofNullable(blockQueueInstructionSet()).orElseThrow(()->new MikoLambEventException(ES00000051));
    }

    /*非阻塞操作*/
    public MikoLambTaskQueueUnBlockOperation<M> unblock(){
        supperQueueAvailable();
        return Optional.ofNullable(unblockQueueInstructionSet()).orElseThrow(()->new MikoLambEventException(ES00000051));
    }

    /*阻塞操作指令集具体实现*/
    protected abstract MikoLambTaskQueueBlockOperation<M> blockQueueInstructionSet();

    /*非阻塞操作指令集具体实现*/
    protected abstract MikoLambTaskQueueUnBlockOperation<M> unblockQueueInstructionSet();

    /*生成队列实例*/
    protected abstract Q buildQueue(Integer size,boolean fair);
}
