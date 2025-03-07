
package io.runon.commons.utils;
/**
 * 코드체계 관련 유틸
 * @author macle
 */
public class CodeUtil {
	
	/**
	 * 자리수만큼 00문자열을 생성해서 돌려줌
	 * @param codeNum int codeNum
	 * @param length int length
	 * @return String CodeNumberValue
	 */
	public static String getCodeNumberValue(int codeNum, int length){
		
		String numValue = Integer.toString(codeNum);
		
		int gap = length - numValue.length();
		
		if(gap == 0){
			return numValue;
		}
		
		StringBuilder sb = new StringBuilder();
		
		for(int i=0 ; i < gap ; i++){
			sb.append("0");
		}
		
		sb.append(numValue);
		
		return sb.toString();
	}

}
