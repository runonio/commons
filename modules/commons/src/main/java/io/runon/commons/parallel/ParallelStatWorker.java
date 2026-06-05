package io.runon.commons.parallel;

import io.runon.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;

/**
 * @author macle
 */
@Slf4j
public class ParallelStatWorker<T, E extends ParallelStatData<T>> implements Runnable, ParallelWorker{


    private final ParallelStatJob<T, E> job;

    final E data;

    ParallelStatWorker(ParallelStatJob<T, E> job, Class<E> dataClass){
        this.job = job;

        try {
            data = dataClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void run() {

        try{
            for(;;){
                if(isStop){
                    break;
                }

                T t = job.next();
                if(t == null){
                    break;
                }
                data.workStat(t);
            }

        }catch (Exception e){
            log.error(ExceptionUtils.getStackTrace(e));
        }

        job.endJob(this);
    }

    boolean isStop = false;
    @Override
    public void stopWork() {
        isStop = true;
    }

}
