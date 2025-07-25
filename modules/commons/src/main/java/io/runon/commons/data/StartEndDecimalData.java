package io.runon.commons.data;

import lombok.Data;

import java.math.BigDecimal;
/**
 * @author macle
 */
@Data
public class StartEndDecimalData implements StartEndDecimal {

    BigDecimal number;
    int start;
    int end;

    public StartEndDecimalData(){

    }

    public StartEndDecimalData(int start, int end, BigDecimal number){
        this.start = start;
        this.end = end;
        this.number = number;
    }

}
