package io.runon.commons.data;

import lombok.Data;

import java.math.BigDecimal;
/**
 * @author macle
 */
@Data
public class BeginEndDecimalData implements BeginEndDecimal{

    BigDecimal number;
    int begin;
    int end;

    public BeginEndDecimalData(){

    }

    public BeginEndDecimalData(int begin, int end, BigDecimal number){
        this.begin = begin;
        this.end = end;
        this.number = number;
    }

}
