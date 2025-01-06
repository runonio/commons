package io.runon.commons.parallel;
/**
 * @author macle
 */
public interface ParallelWork<T>{

    void work(T t);
}
