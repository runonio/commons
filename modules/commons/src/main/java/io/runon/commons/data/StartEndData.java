

package io.runon.commons.data;

import lombok.Data;

/**
 * 구현체
 * @author macle
 */
@Data
public class StartEndData implements StartEnd {

    private int start;
    private int end;

    /**
     * 생성자
     * @param start int
     * @param end int
     */
    public StartEndData(int start, int end){
        this.start = start;
        this.end = end;
    }

    public StartEndData(){

    }
}
