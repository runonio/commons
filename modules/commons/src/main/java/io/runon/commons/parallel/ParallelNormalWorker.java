package io.runon.commons.parallel;

import io.runon.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author macle
 */
@Slf4j
public class ParallelNormalWorker<T> implements Runnable, ParallelWorker{

    private final ParallelNormalJob<T> job;

    private boolean isStop = false;

    ParallelNormalWorker(ParallelNormalJob<T> job){
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


    @Override
    public void stopWork() {
        isStop = true;
    }
}
