package org.mikolamb.framework.sub.taskmachine.queue;

import org.mikolamb.framework.sub.taskmachine.queue.function.MikoLambAbstractTaskQueue;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Author WangGang
 * @Description //TODO 队列$
 * Created by WangGang on 2019/4/19 0019 14:14
 **/
public class MikoLambTaskQueue<M> extends ArrayBlockingQueue<M> implements MikoLambAbstractTaskQueue<M> {

    public MikoLambTaskQueue(int capacity) {
        super(capacity);
    }

    public MikoLambTaskQueue(int capacity, boolean fair) {
        super(capacity, fair);
    }

    public MikoLambTaskQueue(int capacity, boolean fair, Collection<? extends M> c) {
        super(capacity, fair, c);
    }
}
