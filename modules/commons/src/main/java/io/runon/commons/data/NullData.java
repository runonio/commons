

package io.runon.commons.data;

/**
 * 공통 적으로 사용 하는 null 대신에 방지용 정리
 * @author macle
 */
public class NullData {

    /**
     * String [] 을 null 이 아닌 String [0] 으로 생성 해서 전달할 때 사용
     */
    public static final String [] EMPTY_STRING_ARRAY = new String[0];

    public static final int [] EMPTY_INT_ARRAY = new int[0];

}
