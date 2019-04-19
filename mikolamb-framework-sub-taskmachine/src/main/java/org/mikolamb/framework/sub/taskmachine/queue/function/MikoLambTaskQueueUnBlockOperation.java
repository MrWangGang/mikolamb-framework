package org.mikolamb.framework.sub.taskmachine.queue.function;

import java.util.Optional;

/**
 * @Author WangGang
 * @Description //TODO 任务机队列接口$
 * Created by WangGang on 2019/4/18 0018 15:12
 **/
public interface MikoLambTaskQueueUnBlockOperation<M> {

    /*入队*/
    public boolean push(M m);

    /*出队*/
    public Optional<M> pull();
}
