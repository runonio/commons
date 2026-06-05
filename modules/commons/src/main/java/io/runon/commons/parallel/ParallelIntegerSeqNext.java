package io.runon.commons.parallel;

public class ParallelIntegerSeqNext implements ParallelNext<Integer>{

    private final int end;

    private int index;

    private int gap = 1;

    public ParallelIntegerSeqNext(int start, int end) {
        this.end = end;
        index = start;
    }
    public ParallelIntegerSeqNext(int start, int end, int gap) {
        this.end = end;
        index = start;
        this.gap = gap;
    }



    @Override
    public Integer next() {

        if(index >= end){
            return null;
        }

        int value = index;
        index = index + gap;
        return value;
    }
}
