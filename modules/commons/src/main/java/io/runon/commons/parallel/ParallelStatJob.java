package io.runon.commons.parallel;

import java.lang.reflect.InvocationTargetException;

/**
 * @author macle
 */
public class ParallelStatJob<T, E extends ParallelStatData<T>> extends ParallelJob<T>{

    E data;

    final Class<E> dataClass;

    public ParallelStatJob( ParallelNext<T> next, Class<E> dataClass) {
        super(next);

        this.dataClass = dataClass;

        try {
            data = dataClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }


    private ParallelStatWorker<T, E>[] workers;

    public void runAsync(){
        //noinspection unchecked
        workers = new ParallelStatWorker[threadCount];
        for (int i = 0; i <workers.length ; i++) {
            workers[i] = new ParallelStatWorker<>(this, dataClass);
            new Thread(workers[i]).start();
        }

        super.parallelWorker = workers;


    }

    private int endCount = 0;

    void endJob(ParallelStatWorker<T, E> worker){
        synchronized (endLock){

            endCount ++;


            if(!worker.isStop){
                data.dataStat(worker.data);
            }

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


    public E getData(){
        return data;
    }


}

