

package io.runon.commons.math.calculator;

import io.runon.commons.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringCalculatorCheck {
	

	/**
	 * 유효성체크
	 * @param validValue 유효성체크계산식
	 * @param numberValue 숫자변환문자
	 * @return
	 */
	public static boolean valid(String validValue, String numberValue){
		try{
			final StringCalculator calc = new StringCalculator();
			
			if(numberValue != null){
				validValue = validValue.replace(numberValue, "100");
			}
			
			Double result = calc.getResult(validValue);
			if(result == null){
				return false;
			}else{
				return true;
			}
			
		}catch(Exception e){
			
			log.debug(ExceptionUtils.getStackTrace(e));
			return false;
		}
		
	}
	
	
	public static void main(String [] args){
		String value = ("(((1+(2*3))/4)+5+SIZE)");
		System.out.println(StringCalculatorCheck.valid(value,"SIZE"));
		
	}
}
