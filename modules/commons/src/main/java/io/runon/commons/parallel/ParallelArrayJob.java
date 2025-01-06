package io.runon.commons.parallel;

/**
 * @author macle
 */
public class ParallelArrayJob <T> extends ParallelJob<T>{

    public ParallelArrayJob(T [] array, ParallelWork<T> work){
        super(work, new ParallelArrayNext<T>(array));
    }
}
