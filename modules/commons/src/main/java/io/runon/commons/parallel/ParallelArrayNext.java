package io.runon.commons.parallel;

/**
 * @author macle
 */
public class ParallelArrayNext<T> implements ParallelNext<T>{
    private final T [] array;

    private int index = 0;
    ParallelArrayNext(T [] array) {
        this.array = array;
    }

    @Override
    public T next() {
        if (index >= array.length) {
            return null;
        }
        return array[index++];
    }
}
