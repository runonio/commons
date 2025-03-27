

package io.runon.commons.statistics.rank;

import java.util.*;

public class Rank {
	
	/**
	 * 정렬된 Rank형태라는걸 인지시키기위해 LinkedHashMap 형태로 리턴해준다
	 * @param countRankCollection RankCount
	 * @param showRankLength 몇위까지 보여줄건지
	 * @param index 몇번째 인자로 랭크를 계산할건지
	 * @return
	 */
	public static <T extends RankIndex> LinkedHashMap<Integer, List<T>> getRankIndex(Collection<T> countRankCollection, int showRankLength, int index){

		//랭크정보 리스트 초기화
		List<Double> rankList = new LinkedList<Double>();
		for(int i=0 ; i<showRankLength ; i++){
			rankList.add(0.0);
		}

		Map<Double, List<T>> rankListMap = new HashMap<Double, List<T>>(); 
		for(T rankCount : countRankCollection){
			double rankPoint = rankCount.getRankIndex(index);	
			if(isRank(rankList, rankPoint)){
				List<T> rankWordList = rankListMap.get(rankPoint);
				if(rankWordList == null){
					rankWordList = new ArrayList<T>(); 
					rankListMap.put(rankPoint, rankWordList);
				}
				rankWordList.add(rankCount);
			}
		}
		
		List<Double> rankCountList = new ArrayList<Double>();
		for(int i=0 ; i<showRankLength ; i++){
			double count = rankList.get(i);
			if(count !=0 && !rankCountList.contains(count)){
				rankCountList.add(count);
			}
		}
		
		LinkedHashMap<Integer, List<T>> rankMap = new LinkedHashMap<Integer, List<T>>(); 
		int rank = 1;
		int size = rankCountList.size();
		for(int i=0 ; i<size ; i++){
			List<T> rankWordList = rankListMap.get(rankCountList.get(i));
			rankMap.put(rank, rankWordList);
			rank += rankWordList.size();
		}
		//가비지 컬렉터 성능향상을 위한 사용하지 않는 데이터 참조 소멸( 소멸자구현)
		Collection<Double> keys = rankListMap.keySet();
		for(double key : keys){
			List<T> rankWordList = rankListMap.get(key);
			if(!rankCountList.contains(key)){
				rankWordList.clear();
			}
		}
		rankList.clear(); rankList =null;
		rankListMap.clear(); rankListMap = null;
		rankCountList.clear(); rankCountList = null;
		//소멸 끝
		
		return rankMap;
	}
	
	private static boolean isRank(List<Double> rankList, double point){
		boolean isRank = false;
		
		for(int i=rankList.size()-1 ; i>-1 ; i--){
			double rankCount = rankList.get(i);
			if(point > rankCount){
				isRank = true;
			}else if(point == rankCount){
				rankList.add(i+1, point);
				rankList.remove(rankList.size()-1);		
				return isRank;
			}else{
				if(isRank){
					rankList.add(i+1, point);
					rankList.remove(rankList.size()-1);		
				}
				return isRank;
			}
		}
		if(isRank){
			rankList.add(0, point);
			rankList.remove(rankList.size()-1);	
		}
		
		return isRank;
	}
	

	/**
	 * 몇번째 인덱스까지 사용하여 랭크결과를 생성할건지를 결정한다.
	 * @param countRankCollection 랭킹계산에 필요한 콜랜션 객체
	 * @param showRankLength
	 * @return
	 */
	public static <T extends RankIndex> LinkedHashMap<Integer, List<T>> getRank(Collection<T> countRankCollection, int showRankLength, int rankCountLenth){
		if(countRankCollection == null || countRankCollection.size()<1){
			return null;
		}
		
		LinkedHashMap<Integer, List<T>> rankMap = getRankIndex(countRankCollection, showRankLength, 0);
		
		Collection<Integer> rankPointCollection= rankMap.keySet();
	
		if(rankCountLenth > 1){
			List<Integer> searchRankList = new LinkedList<Integer>();
			for(Integer rankPoint : rankPointCollection){	
					List<T> rankList = rankMap.get(rankPoint);
					if(rankList.size() > 1){
						searchRankList.add(rankPoint);
					}
			}
			if(searchRankList.size() > 1){
				setRank(rankMap, searchRankList, showRankLength, rankCountLenth, 0);
			}
			LinkedHashMap<Integer, List<T>> tempRankMap = new LinkedHashMap<Integer, List<T>>(); 
			Integer[] rankArray = rankMap.keySet().toArray(new Integer[rankMap.size()]);
			Arrays.sort(rankArray);
			int chkRank = showRankLength +1;
			for(Integer rank : rankArray){				
				if(rank < chkRank)
					tempRankMap.put(rank, rankMap.get(rank));
			}
			rankMap.clear();
			rankMap = tempRankMap;
			tempRankMap = null;
		}
		return rankMap;
	}
	
	private static <T extends RankIndex> void setRank(LinkedHashMap<Integer, List<T>> rankMap, List<Integer> searchRankList, int showRankLength ,int rankCountLenth, int index){
		Integer [] searchRankArray = searchRankList.toArray(new Integer[searchRankList.size()]);
		for(Integer searchRank : searchRankArray){
			List<T> rankList =  rankMap.get(searchRank);
			LinkedHashMap<Integer, List<T>> childRankMap = getRankIndex(rankList, rankList.size(), index);
			if(childRankMap.size() > 1){
				rankMap.remove(searchRank);
				searchRankList.remove(searchRank);
					
				Collection<Integer> keys = childRankMap.keySet();
				for(Integer key : keys){
					int rankPoint = key - 1 +searchRank;
					List<T> childRankList = childRankMap.get(key);
					rankMap.put(rankPoint, childRankList);
					if(childRankList.size() > 1){
						searchRankList.add(rankPoint);
					}
				}
					
			}
			childRankMap.clear();
			childRankMap = null;
		}
			
		searchRankArray = null;
		index++;
		if(searchRankList.size() > 0 && index < rankCountLenth){
			setRank(rankMap, searchRankList, showRankLength, rankCountLenth, index);
		}
	}
		
	public static void main(String [] args){
	
	}
}