
package io.runon.commons.data;

import java.util.Comparator;

/**
 * 시작위치 끝위치
 * @author macle
 */
public class StartEndIndex {

	public final static Comparator<StartEndIndex> SORT_INDEX_LENGTH = (o1, o2) -> {

		if(o1.endIndex == o2.endIndex){
			return Integer.compare(o1.length(), o2.length());
		}

		return Integer.compare(o1.startIndex, o2.startIndex);
	};


	public final static Comparator<StartEndIndex> SORT_ASC = (w1, w2) -> {

		if(w1.startIndex == w2.startIndex){
			return Integer.compare(w1.endIndex, w2.endIndex);
		}

		return Integer.compare(w1.startIndex, w2.startIndex);
	};

	public final static Comparator<StartEndIndex> SORT_ASC_END_DESC = (w1, w2) -> {

		if(w1.startIndex == w2.startIndex){
			return Integer.compare(w2.endIndex, w1.endIndex);
		}

		return Integer.compare(w1.startIndex, w2.startIndex);
	};

	protected int startIndex = 0;
	protected int endIndex =0;
	

	public StartEndIndex(int startIndex, int endIndex){
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}

	public StartEndIndex(StartEndIndex initIndex){
		this.startIndex = initIndex.getStartIndex();
		this.endIndex = initIndex.getEndIndex();
	}

	/**
	 * 생성자
	 */
	public StartEndIndex(){
		
	}
	/**
	 * 시작위치를 돌려준다
	 * @return
	 */
	public int getStartIndex() {
		return startIndex;
	}
	
	/**
	 * 끝위치를 돌려준다.
	 * @return
	 */
	public int getEndIndex() {
		return endIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
    
    public void setEnd(int end){
        this.endIndex = end - 1;
    }

	public int getEnd(){
		return endIndex+1;
	}
	public int length(){
		return endIndex - startIndex + 1;
	}


	public String getStartEndText(){
		return startIndex + "," + endIndex;
	}
	

}