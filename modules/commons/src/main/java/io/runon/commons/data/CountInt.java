package io.runon.commons.data;

import java.util.Comparator;

public class CountInt  implements java.io.Serializable{

    public final static Comparator<CountInt> SORT_DESC = (c1, c2) -> Integer.compare(c2.count, c1.count);

    private static final long serialVersionUID = -6323716102558973829L;
    protected int count;

    /**
     * 생성자
     */
    public CountInt(){
        count = 0;
    }

    /**
     * 생성자
     * @param count 건수
     */
    public CountInt(int count){
        this.count = count;
    }

    /**
     * 건수 1 증가
     */
    public void plus(){
        count ++ ;
    }

    /**
     * 건수증가
     * @param count 증가건수
     */
    public void plus(int count){
        this.count += count;
    }

    /**
     * 건수 1 감소
     */
    public void minus(){
        count --;
    }


    /**
     * 건수 감소
     * @param count 감소건수
     */
    public void minus(int count){
        this.count -= count;
    }

    /**
     * 건수 얻기
     * @return 건수
     */
    public int getCount(){
        return count;
    }

}
