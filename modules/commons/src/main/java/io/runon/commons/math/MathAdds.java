package io.runon.commons.math;

import java.util.Arrays;

public class MathAdds {
    public static double logB(double x, double base) {
        return Math.log(x) / Math.log(base);
    }

    public static long positiveIntegerSum(long positiveInteger){

        return positiveInteger*(positiveInteger+1)/2;
    }

    public static double weightGap(double a, double b){
        if( a > b){
            return b/a;
        }else{
            return a/b;
        }
    }

    /**
     * 분포평균 얻기 (10%[0.1] 를지정하면 상위10% 하위10%를 제거한 중간값들의 평균)
     * @param valueArray double []
     * @param percent double 0.1을 지정하면 상위10% 하위10%를 제거한 중간값들의 평균
     * @return double 분포평균
     */
    public static double getAverageDistribution(double [] valueArray, double percent){
        Arrays.sort(valueArray);
        int start = (int)(((double) valueArray.length) * percent);
        int last = valueArray.length - start;
        double sum = 0;
        for(int i=start ; i < last ; i++){
            sum += valueArray[i];
        }
        return sum/(double)(last - start);
    }

    /**
     * 평균 얻기
     * @param valueArray double []
     * @return double 평균
     */
    public static double getAverage(double [] valueArray){

        double sum = 0;
        for(double value : valueArray){
            sum += value;
        }
        return sum/valueArray.length;
    }

    public static int levenshteinDistance(char[] source, char [] target) {


        int n = source.length;
        int m = target.length;
        // 다이나믹 프로그래밍을 위한 2차원 DP 테이블 초기화
        int[][] dp = new int[n + 1][m + 1];
        // DP 테이블 초기 설정
        for (int i = 1; i <= n; i++) {
            dp[i][0] = i;
        }
        for (int j = 1; j <= m; j++) {
            dp[0][j] = j;
        }


        // 최소 편집 거리 계산
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                // 문자가 같다면, 왼쪽 위에 해당하는 수를 그대로 대입
                if (source[i - 1] == target[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // 문자가 다르다면, 세 가지 경우 중에서 최솟값 찾기
                    // 삽입(왼쪽), 삭제(위쪽), 교체(왼쪽 위) 중에서 최소 비용을 찾아 대입
                    dp[i][j] = 1 + Math.min(dp[i][j - 1], Math.min(dp[i - 1][j], dp[i - 1][j - 1]));
                }
            }
        }

        return dp[n][m];
    }
}
