package io.runon.commons.parallel;

import io.runon.commons.callback.Callback;
import io.runon.commons.config.Config;
import io.runon.commons.utils.time.Times;

/**
 * @author macle
 */
public class ParallelJob<T> {

    protected final Object lock = new Object();

    protected final Object endLock = new Object();

    protected Callback callback = null;
    protected int threadCount = getDefaultThreadCount();

    protected final ParallelWork<T> work;

    protected final ParallelNext<T> next;

    public ParallelJob(ParallelWork<T> work, ParallelNext<T> next){
        this.work = work;
        this.next = next;

    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setThreadCount(int threadCount) {
        if(threadCount < 1){
            threadCount = 1;
        }

        this.threadCount = threadCount;
    }

    T next(){
        synchronized (lock) {
            return next.next();
        }
    }

    private ParallelWorker<T>[] workers;

    private Thread currentThread = null;


    //동기실행
    public void runSync(){
        currentThread = Thread.currentThread();
        runAsync();
        //noinspection ConditionalBreakInInfiniteLoop
        for(;;){

            if(isEnd){
                break;
            }
            try{
                Thread.sleep(Times.DAY_1);
            }catch (Exception ignore){}
        }

    }

    public void runAsync(){
        //noinspection unchecked
        workers = new ParallelWorker[threadCount];
        for (int i = 0; i <workers.length ; i++) {
            workers[i] = new ParallelWorker<>(this);
            new Thread(workers[i]).start();
        }
    }

    private int endCount = 0;

    boolean isEnd = false;

    void endJob(){
        synchronized (endLock){
            endCount ++;
            if(endCount >= workers.length){

                isEnd = true;

                if(currentThread != null){
                    try {
                        currentThread.interrupt();
                    }catch (Exception ignore){}
                }

                if(callback != null){
                    callback.callback();
                }
            }
        }
    }

    public void stop(){
        if(workers == null){
            return;
        }

        for(ParallelWorker<T> worker : workers){
            worker.stop();
        }

    }

    public static int getDefaultThreadCount(){
        int defaultThreadCount =  Config.getInteger("default.thread.count", Runtime.getRuntime().availableProcessors() -1 );
        if(defaultThreadCount < 1){
            defaultThreadCount = 1;
        }
        return defaultThreadCount;
    }
}
