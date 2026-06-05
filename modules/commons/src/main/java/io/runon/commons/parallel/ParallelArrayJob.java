package io.runon.commons.parallel;

/**
 * @author macle
 */
public class ParallelArrayJob <T> extends ParallelNormalJob<T> {

    public ParallelArrayJob(T [] array, ParallelWork<T> work){
        super(work, new ParallelArrayNext<T>(array));
    }
}
