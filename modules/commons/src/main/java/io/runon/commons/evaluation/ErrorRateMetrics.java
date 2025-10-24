package io.runon.commons.evaluation;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * 오류율 계산
 * wer, cer 등에 활용
 * @author macle
 */
@Getter
public class ErrorRateMetrics {
    public int substitutions;
    public int deletions;
    public int insertions;
    public int hits;

    public ErrorRateMetrics(int s, int d, int i, int h) {
        this.substitutions = s;
        this.deletions = d;
        this.insertions = i;
        this.hits = h;
    }

    public ErrorRateMetrics(){
        this.substitutions = 0;
        this.deletions = 0;
        this.insertions = 0;
        this.hits = 0;
    }


    public void add(ErrorRateMetrics metrics) {
        this.substitutions += metrics.getSubstitutions();
        this.deletions += metrics.getDeletions();
        this.insertions += metrics.getInsertions();
        this.hits += metrics.getHits();
    }


    public BigDecimal getErrorRate() {
        return getErrorRate(-1);
    }


    /**
     * 오류율(Error Rate)을 계산합니다.
     * 공식: (S + D + I) / (S + D + C)
     * @return 계산된 오류율. 정답 텍스트 길이가 0이면 0.0을 반환.
     */
    public BigDecimal getErrorRate(int scale) {

        BigDecimal sdc = new BigDecimal(substitutions).add(new BigDecimal(deletions)).add(new BigDecimal(hits));
        BigDecimal sdi = new BigDecimal(substitutions).add(new BigDecimal(deletions)).add(new BigDecimal(insertions));
        if (sdc.compareTo(BigDecimal.ZERO) == 0) {
            // 정답(ref) 텍스트가 비어있는 경우.
            // 예측(pred)도 비어있으면 오류 0%, 아니면 100%로 볼 수도 있지만, 일반적으로 0으로 처리.
            return (sdi.compareTo(BigDecimal.ZERO) > 0) ? BigDecimal.ONE : BigDecimal.ZERO;
        }

        if(scale < 0) {
            return sdi.divide(sdc, MathContext.DECIMAL128);
        }else{
            return sdi.divide(sdc, scale, RoundingMode.HALF_UP);
        }
    }

    public BigDecimal getErrorPercent(){
        return getErrorPercent(-1);
    }


    public BigDecimal getErrorPercent(int scale){

        if(scale < 0){
            return getErrorRate(-1).multiply(new BigDecimal(100).stripTrailingZeros());
        }

        return getErrorRate(scale +2).multiply(new BigDecimal(100)).setScale(scale, RoundingMode.HALF_UP).stripTrailingZeros();
    }

    @Override
    public String toString() {
        return String.format("S=%d, D=%d, I=%d, C=%d",
                substitutions, deletions, insertions, hits);
    }


    public int getS(){
        return substitutions;
    }

    public int getD(){
        return deletions;
    }

    public int getI(){
        return insertions;
    }

    public int getC(){
        return hits;
    }

}
