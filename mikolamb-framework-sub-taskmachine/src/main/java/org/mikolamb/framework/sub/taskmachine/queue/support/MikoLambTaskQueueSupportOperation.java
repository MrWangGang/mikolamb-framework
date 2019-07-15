package org.mikolamb.framework.sub.taskmachine.queue.support;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.mikolamb.framework.common.exception.MikoLambEventException;
import org.mikolamb.framework.sub.taskmachine.enums.ConsumerTypeEnum;
import org.mikolamb.framework.sub.taskmachine.queue.function.MikoLambAbstractTaskQueue;
import org.mikolamb.framework.sub.taskmachine.queue.function.MikoLambTaskQueueBlockOperation;
import org.mikolamb.framework.sub.taskmachine.queue.function.MikoLambTaskQueueUnBlockOperation;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mikolamb.framework.common.enums.MikoLambExceptionEnum.*;

/**
 * @Author WangGang
 * @Description //TODO 队列操作者$
 * Created by WangGang on 2019/4/18 0018 15:18
 **/
@Data
public abstract class MikoLambTaskQueueSupportOperation<Q extends MikoLambAbstractTaskQueue<M>,M> {

    /*队列实例*/
    private Q queue;
    /*任务标识*/
    private String taskKey;
    /*是否是公平队列*/
    private boolean fair;

    /*最大工作线程数量*/
    private int maxExecuteThreadNum;

    /*最大工作队列容量*/
    private int maxExecuteQueueSize;

    /*每个任务最大耗时时间*/
    private int maxTaskExecuteTime;

    /*消费者*/
    private Method consumer;

    /*消费者类型*/
    private ConsumerTypeEnum consumerType;


    public void load(){
        verify();
        if(queue==null)this.queue = buildQueue();
        supperQueueAvailable();
        buildConsumerThreads();
    }

    private void verify(){
        if(maxExecuteThreadNum<=0 && maxExecuteThreadNum%2!=0)throw new MikoLambEventException(ES00000054);
        if(maxExecuteQueueSize<=0)throw new MikoLambEventException(ES00000055);
        if(maxTaskExecuteTime<=1000)throw new MikoLambEventException(ES00000056);
        if(StringUtils.isBlank(taskKey))throw new MikoLambEventException(ES00000053);
        if(consumer == null)throw new MikoLambEventException(ES00000061);
        if(consumerType==null)throw new MikoLambEventException(ES00000062);
    }

    private void supperQueueAvailable(){
        Optional.ofNullable(this.getQueue()).orElseThrow(()->new MikoLambEventException(ES00000051));
    }

    /*阻塞操作*/
    public MikoLambTaskQueueBlockOperation<M> block(){
        return Optional.ofNullable(blockQueueInstructionSet()).orElseThrow(()->new MikoLambEventException(ES00000051));
    }

    /*非阻塞操作*/
    public MikoLambTaskQueueUnBlockOperation<M> unblock(){
        return Optional.ofNullable(unblockQueueInstructionSet()).orElseThrow(()->new MikoLambEventException(ES00000051));
    }

    /*阻塞操作指令集具体实现*/
    protected abstract MikoLambTaskQueueBlockOperation<M> blockQueueInstructionSet();

    /*非阻塞操作指令集具体实现*/
    protected abstract MikoLambTaskQueueUnBlockOperation<M> unblockQueueInstructionSet();

    /*生成队列实例*/
    protected abstract Q buildQueue();

    /*消费者线程创建*/
    protected  void buildConsumerThreads(){
        IntStream.range(0,maxExecuteThreadNum).forEach(e->{
            String threadName = taskKey+"-"+"Queue"+"-"+e;
            new Thread(() -> {
                for(;;){
                    if(ConsumerTypeEnum.BLOCK.getCode().equals(consumerType.getCode())){
                       try {
                           Optional<M> model =  blockQueueInstructionSet().pull();
                           executeConsumer(model);
                       }catch (Exception error){
                            error.printStackTrace();
                       }
                    }else if(ConsumerTypeEnum.UNBLOCK.getCode().equals(consumerType.getCode())){
                        try {
                            Optional<M> model =  unblockQueueInstructionSet().pull();
                            executeConsumer(model);
                        }catch (Exception error){
                            error.printStackTrace();
                        }
                    }else {
                        //销毁线程
                        throw new MikoLambEventException(ES00000062);
                    }
                }
            },threadName).start();
        });
    }

    private <M>void executeConsumer(M model){

    }
}
