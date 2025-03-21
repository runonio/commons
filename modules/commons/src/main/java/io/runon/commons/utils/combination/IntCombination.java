

package io.runon.commons.utils.combination;

import java.util.ArrayList;
import java.util.List;

public class IntCombination {
	private IntCombination(){	
	}

	public static List<int []> getCombinationList(int [] values){
		return getCombinationList(values, values.length);
	}
	
	
	public static List<int []> getCombinationList(int [] values, int maxCombinationLength){
		List<int []> combinationList = new ArrayList<int []>();
//		this.words = words;
//		wordsSize = words.length;
		int size = maxCombinationLength +1 ;
		for(int i=2; i<size ; i++){
			int [] combination = new int[i];
//			cbtSize = i;
			setCombination(values.length, i, i, values, combinationList, combination);
		}
		return combinationList;
	}
	
	private static void setCombination(int n, int r, int cbtSize, int [] values, List<int []> combinationList,int [] combination){
		if(r == 0) {
			int size = combination.length;
			int [] temps = new int[size];
			for(int i=0 ; i<size ;i++)
				temps[i] = combination[i];
			combinationList.add(temps);
		}else if(n<r) {
			return;
		}else{
//			cbt[r-1] = words[n-1]; //출력순서 반대로 하기.
			combination[cbtSize-r] = values[values.length-n];
			setCombination(n-1, r-1, cbtSize, values, combinationList, combination);
			setCombination(n-1,r, cbtSize, values, combinationList, combination);
		}
	}
	
//	public void clear(){
//		combinationList.clear(); combinationList = null;
//		combination = null; words = null;
//	}
	
	public static void main(String args[]){
//		String [] a ={"a","d","e","a","b","b","c"};
		int [] b = {1,3,5};
		List<int []> ad = IntCombination.getCombinationList(b,b.length-1);
		for(int [] d : ad){			
			for(int ww : d)
				System.out.print(ww+" ");
			
			System.out.println();
		}
		
		
		
		System.out.println(ad.size());
//		System.out.println(b.length);

	}
	
}