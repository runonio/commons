

package io.runon.commons.utils.combination;

import io.runon.commons.utils.string.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 조합의 경우의수 추출
 * @author macle
 */
public class StringCombination {
	private StringCombination(){	
	}

	public static List<String []> getCombinationList(String [] values){
		return getCombinationList(values, values.length);
	}
	
	public static List<String []> getCombinationList(List<String> valueList){
		return getCombinationList(valueList.toArray(new String[valueList.size()]), valueList.size());
	}
	
	public static List<String []> getCombinationList(List<String> valueList, int maxCombinationLength){
		return getCombinationList(valueList.toArray(new String[valueList.size()]), maxCombinationLength);
	}

	public static List<String []> getCombinationList(String [] values, int max){
		return getCombinationList(values,2,max, null, null);
	}

	public static List<String []> getCombinationList(String [] values, int min, int max, Set<String> essentialSet){
		return getCombinationList(values, min, max, essentialSet, null);
	}

	public static List<String []> getCombinationList(String [] values, int min, int max, Set<String> essentialSet, List<String []> essentialArrayList){
		List<String []> combinationList = new ArrayList<>();
//		this.words = words;
//		wordsSize = words.length;
		for(int i=min ; i<=max ; i++){
			String [] combination = new String[i];
//			cbtSize = i;
			setCombination(values.length, i, i, values, combinationList, combination, essentialSet, essentialArrayList);
		}
		return combinationList;
	}
	
	private static void setCombination(int n, int r, int cbtSize, String [] values, List<String []> combinationList, String [] combination,  Set<String> essentialSet, List<String []> essentialArrayList){
		if(r == 0) {
			int size = combination.length;
			String [] temps = new String[size];
			for(int i=0 ; i<size ;i++)
				temps[i] = combination[i];
			
			if(essentialSet != null){
				boolean isIn = false;
				for(String temp : temps){
					if(essentialSet.contains(temp)){
						isIn = true;
						break;
					}
				}
				if(!isIn)
					return ;
			}

			if(essentialArrayList != null &&  essentialArrayList .size() > 0){
				boolean isIn = false;
				for(String [] essentialArray : essentialArrayList){
					if(Strings.contains(temps, essentialArray)){
						isIn = true;
						break;
					}
				}
				if(!isIn)
					return ;
			}

			combinationList.add(temps);
		}else if(n<r) {
			return;
		}else{
//			cbt[r-1] = words[n-1]; //출력순서 반대로 하기.
			combination[cbtSize-r] = values[values.length-n];
			setCombination(n-1, r-1, cbtSize, values, combinationList, combination, essentialSet, essentialArrayList);
			setCombination(n-1,r, cbtSize, values, combinationList, combination, essentialSet, essentialArrayList);
		}
	}
	
//	public void clear(){
//		combinationList.clear(); combinationList = null;
//		combination = null; words = null;
//	}
	
	public static void main(String args[]){
		
//		Set<String > test = new HashSet<String>();
//		test.add("c");
//		test.add("e");
		


		String [] a ={"a","b","c","d","e"};
//		String [] b = {"a","b","c"};
		List<String []> ad = StringCombination.getCombinationList( a, 3, 3, null);
		for(String [] d : ad){
			for(String ww : d)
				System.out.print(ww+" ");

			System.out.println();
		}
		
		
		
		System.out.println(ad.size());
//		System.out.println(b.length);

	}
	
}