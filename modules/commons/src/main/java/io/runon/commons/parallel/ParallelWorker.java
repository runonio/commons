package io.runon.commons.parallel;

import io.runon.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author macle
 */
@Slf4j
public class ParallelWorker<T> implements Runnable{

    private final ParallelJob<T> job;

    private boolean isStop = false;

    ParallelWorker(ParallelJob<T> job){
        this.job = job;
    }

    @Override
    public void run() {

        ParallelWork<T> work = job.work;

        try{

            for(;;){
                if(isStop){
                    break;
                }

                T t = job.next();
                if(t == null){
                    break;
                }

                work.work(t);
            }

        }catch (Exception e){
            log.error(ExceptionUtils.getStackTrace(e));
        }

        job.endJob();
    }


    void stop(){
        isStop = true;
    }
}
