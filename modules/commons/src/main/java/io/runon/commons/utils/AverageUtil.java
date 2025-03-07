

package io.runon.commons.utils;

import java.util.Arrays;

/**
 * 평균 관련 유틸성 클래스
 * @author macle
 */
public class AverageUtil {

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

}
