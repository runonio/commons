package io.runon.commons.parallel;
/**
 * @author macle
 */
public class ParallelArrayStatJob<T,  E extends ParallelStatData<T>>  extends ParallelStatJob<T, E>{
    public ParallelArrayStatJob(T [] array, Class<E> dataClass) {
        super(new ParallelArrayNext<T>(array), dataClass);
    }
}
