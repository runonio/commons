
package io.runon.commons.math.calculator;

public class StringCalculatorToMath {
	private StringCalculator stringCalculator;
	public StringCalculatorToMath(){
		stringCalculator = new StringCalculator();	
	}
	
	public enum FunctionSymbol{
		LOG
		, EXP
	}
	
	
	/**
	 * 문자열 입력 값에 대한 결과를 돌려준다.
	 * @param dataIn
	 * @return
	 */
	public Double getResult(String dataIn){
		if(dataIn == null)
			return null;
		
	
//		if(dataIn.indexOf("LOG") == -1){
//			return stringCalculator.getResult(dataIn);
//		}
		
		dataIn = dataIn.replaceAll("\\p{Space}","");
		FunctionSymbol [] functionSymbolArray = FunctionSymbol.values();
		for(FunctionSymbol functionSymbolCheck : functionSymbolArray){
			dataIn = dataIn.replace(functionSymbolCheck.toString().toLowerCase(),functionSymbolCheck.toString());
		}
		
			
		String result = getMathResult(dataIn);
		if(result == null)
			return null;
		return stringCalculator.getResult(result);
	}
	
	private String getMathResult(String dataIn){
	
		FunctionSymbol [] functionSymbolArray = FunctionSymbol.values();
		while(true){
			FunctionSymbol functionSymbol = null;
			int index =-1;
			for(FunctionSymbol functionSymbolCheck : functionSymbolArray){
				int symbolIndex = dataIn.indexOf(functionSymbolCheck.toString() +"(");
				if((symbolIndex > -1 && index==-1 )
						||(symbolIndex > -1 && symbolIndex < index)){
					index = symbolIndex;
					functionSymbol = functionSymbolCheck; 
				}
			}
			if(index ==-1){
				break;
			}

			int length = dataIn.length();
			int startCount = 0;
			int endIndex = -1;
			for(int i=index + functionSymbol.toString().length()+2; i<length; i++){
			
				char ch = dataIn.charAt(i);
				if(ch =='('){
					startCount++;
				}else if(ch ==')'){
					
					if(startCount == 0){
						endIndex = i;	
						break;
					}else{
						startCount --;
					}
				}
			}
			if(endIndex == -1){
				
				return null;
			}
			
			
			String subString = dataIn.substring(index + functionSymbol.toString().length()+1, endIndex );
			for(FunctionSymbol functionSymbolCheck : functionSymbolArray){
				if(subString.indexOf(functionSymbolCheck.toString() +"(") != -1){
					
					subString = getMathResult(subString);
					break;
				}
			}
			Double value = stringCalculator.getResult(subString);
			if( value == null){
				return null;
			}else{
				if(functionSymbol==FunctionSymbol.EXP){
					value = Math.exp(value);
				}else if(functionSymbol==FunctionSymbol.LOG){
					value = Math.log(value);
					
				}
				dataIn = dataIn.replace(dataIn.substring(index, endIndex+1 ), Double.toString(value));
			}
			
		}
//		while((index = dataIn.indexOf(searchString) ) != -1){
//			
//			
//			int startCount = 0;
//			int endIndex = -1;
//			for(int i=index+5; i<dataIn.length(); i++){
//				
//				char ch = dataIn.charAt(i);
//				if(ch =='('){
//					startCount++;
//				}else if(ch ==')'){
//					
//					if(startCount == 0){
//						endIndex = i;	
//						break;
//					}else{
//						startCount --;
//					}
//				}
//			}
//			
//			if(endIndex == -1){
//			
//				return null;
//			}
//			
//			String subString = dataIn.substring(index+4, endIndex );
//			
//			if(subString.indexOf(searchString) != -1){
//				subString = getMathResult(subString, functionSymbol);
//			}
//			Double value = stringCalculator.getResult(subString);
//			if( value == null){
//				return null;
//			}else{
//				if(functionSymbol==FunctionSymbol.EXP){
//					value = Math.exp(value);
//				}else if(functionSymbol==FunctionSymbol.LOG){
//					value = Math.log(value);
//				}
//				dataIn = dataIn.replace(dataIn.substring(index, endIndex+1 ), Double.toString(value));
//			}
//		}
		
		return dataIn;
	}
	
	public static void main(String [] args){
		String dataIn ="exp(LOG(EXP(LOG(10.0*(5-1)  + LOG(10))))) +100 + LOG(5)*7";
//		System.out.println( dataIn.substring(5));
		System.out.println(new StringCalculatorToMath().getResult(dataIn));
	}
}