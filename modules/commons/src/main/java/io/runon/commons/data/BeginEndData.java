

package io.runon.commons.data;

/**
 * begin end
 * 구현체
 * @author macle
 */
public class BeginEndData implements BeginEnd {

    private final int begin;
    private final int end;

    /**
     * 생성자
     * @param begin int
     * @param end int
     */
    public BeginEndData(int begin, int end){
        this.begin = begin;
        this.end = end;
    }

    @Override
    public int getBegin() {
        return begin;
    }

    @Override
    public int getEnd() {
        return end;
    }
}
