package io.runon.commons.parallel;

import io.runon.commons.callback.Callback;
import io.runon.commons.config.Config;
import io.runon.commons.utils.time.Times;

/**
 * @author macle
 */
public abstract class ParallelJob<T> {


    protected final Object lock = new Object();

    protected final Object endLock = new Object();

    protected Callback callback = null;
    protected int threadCount = getDefaultThreadCount();



    protected final ParallelNext<T> next;

    protected ParallelWorker [] parallelWorker;


    public ParallelJob( ParallelNext<T> next){
        this.next = next;

    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setThreadCount(int threadCount) {
        if(threadCount < 1){
            threadCount = 1;
        }

        int defaultThreadCount = getDefaultThreadCount();
        if(threadCount > defaultThreadCount){
            threadCount = defaultThreadCount;
        }

        this.threadCount = threadCount;
    }


    T next(){
        synchronized (lock) {
            return next.next();
        }
    }


    protected Thread currentThread = null;

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

    abstract public void runAsync();



    protected boolean isEnd = false;


    public void stop(){
        if(parallelWorker == null){
            return;
        }

        for(ParallelWorker worker : parallelWorker){
            worker.stopWork();
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
