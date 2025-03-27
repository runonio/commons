

package io.runon.commons.statistics.rank;

public class ExRankIndex implements RankIndex{

	private int index1, index2;
	
	public ExRankIndex(int index1, int index2){
		this.index1 = index1;
		this.index2 = index2;
	}
	
	@Override
	public double getRankIndex(int index) {
		if(index == 0){
			return index1;
		}else{
			return index2;
		}
		
	}

}