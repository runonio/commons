package io.runon.commons.data;

import java.math.BigDecimal;
import java.util.Comparator;

/**
 * @author macle
 */
public interface GetNumber {

    Comparator<GetNumber> SORT_ASC = Comparator.comparing(GetNumber::getNumber);

    Comparator<GetNumber> SORT_DESC = (n1, n2) -> n2.getNumber().compareTo(n1.getNumber());

    BigDecimal getNumber();

}
