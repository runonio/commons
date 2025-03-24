

package io.runon.commons.data;

import lombok.Data;

/**
 * begin end
 * 구현체
 * @author macle
 */
@Data
public class BeginEndData implements BeginEnd {

    private int begin;
    private int end;

    /**
     * 생성자
     * @param begin int
     * @param end int
     */
    public BeginEndData(int begin, int end){
        this.begin = begin;
        this.end = end;
    }

    public BeginEndData(){

    }
}
