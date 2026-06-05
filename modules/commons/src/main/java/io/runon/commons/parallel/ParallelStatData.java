package io.runon.commons.parallel;
/**
 * @author macle
 */
public interface ParallelStatData<T> {

    /**
     * 쓰레드별 통계
     */
    void workStat(T t);

    /**
     * 쓰레드별 통계한 데이터를 다시 합치는 매소드 정의
     */
    void dataStat(ParallelStatData<T> data);



}
