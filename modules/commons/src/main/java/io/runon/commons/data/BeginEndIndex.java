
package io.runon.commons.data;

import java.util.Comparator;

/**
 * 시작위치 끝위치
 * @author macle
 */
public class BeginEndIndex {

	public final static Comparator<BeginEndIndex> SORT_INDEX_LENGTH = (o1, o2) -> {

		if(o1.endIndex == o2.endIndex){
			return Integer.compare(o1.length(), o2.length());
		}

		return Integer.compare(o1.beginIndex, o2.beginIndex);
	};


	public final static Comparator<BeginEndIndex> SORT_ASC = (w1, w2) -> {

		if(w1.beginIndex == w2.beginIndex){
			return Integer.compare(w1.endIndex, w2.endIndex);
		}

		return Integer.compare(w1.beginIndex, w2.beginIndex);
	};

	public final static Comparator<BeginEndIndex> SORT_ASC_END_DESC = (w1, w2) -> {

		if(w1.beginIndex == w2.beginIndex){
			return Integer.compare(w2.endIndex, w1.endIndex);
		}

		return Integer.compare(w1.beginIndex, w2.beginIndex);
	};

	protected int beginIndex = 0;
	protected int endIndex =0;
	

	public BeginEndIndex(int startIndex, int endIndex){
		this.beginIndex = startIndex;
		this.endIndex = endIndex;
	}

	public BeginEndIndex(BeginEndIndex initIndex){
		this.beginIndex = initIndex.getBeginIndex();
		this.endIndex = initIndex.getEndIndex();
	}

	/**
	 * 생성자
	 */
	public BeginEndIndex(){
		
	}
	/**
	 * 시작위치를 돌려준다
	 * @return
	 */
	public int getBeginIndex() {
		return beginIndex;
	}
	
	/**
	 * 끝위치를 돌려준다.
	 * @return
	 */
	public int getEndIndex() {
		return endIndex;
	}

	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}


	public int getEnd(){
		return endIndex+1;
	}
	public int length(){
		return endIndex - beginIndex + 1;
	}


	public String getBeginEndText(){
		return beginIndex + "," + endIndex;
	}
	

}