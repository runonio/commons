package io.runon.commons.data;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Comparator;

/**
 * 위치정보와 숫자정보
 * @author macle
 */
@Data
public class IndexDecimal {


    public final static Comparator<IndexDecimal> SORT = Comparator.comparingInt(o -> o.index);

    int index;
    BigDecimal number;
    String text;

    public IndexDecimal(int index, BigDecimal number) {
        this.index = index;
        this.number = number;
    }

    public IndexDecimal(){

    }

}
