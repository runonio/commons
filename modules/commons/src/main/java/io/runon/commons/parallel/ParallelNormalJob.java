package io.runon.commons.parallel;
/**
 * @author macle
 */
public class ParallelNormalJob<T> extends ParallelJob<T>{

    protected final ParallelWork<T> work;

    public ParallelNormalJob(ParallelWork<T> work, ParallelNext<T> next) {
        super(next);
        this.work = work;
    }


    private ParallelNormalWorker<T>[] workers;

    public void runAsync(){
        //noinspection unchecked
        workers = new ParallelNormalWorker[threadCount];
        for (int i = 0; i <workers.length ; i++) {
            workers[i] = new ParallelNormalWorker<>(this);
            new Thread(workers[i]).start();
        }

        super.parallelWorker = workers;


    }

    private int endCount = 0;

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


}
