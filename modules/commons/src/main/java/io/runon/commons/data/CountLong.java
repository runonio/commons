package io.runon.commons.data;

/**
 * @author macle
 */
public class CountLong  implements java.io.Serializable{

    long count;

    /**
     * 생성자
     */
    public CountLong(){
        count = 0;
    }

    public CountLong(String longValue){
        count = Long.parseLong(longValue.trim());
    }

    public void setCount(long count) {
        this.count = count;
    }

    /**
     * 생성자
     * @param count 건수
     */
    public CountLong(long count){
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
    public void plus(long count){
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
    public void minus(long count){
        this.count -= count;
    }

    /**
     * 건수 얻기
     * @return 건수
     */
    public long getCount(){
        return count;
    }
}
