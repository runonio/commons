
package io.runon.commons.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author macle
 */
public class BeginEndIndexData {
	
	
	@SuppressWarnings("StaticInitializerReferencesSubClass")
	public static final BeginEndIndexData EMPTY_DATA = new BeginEndIndexEmpty();
	
	private List<BeginEndIndex> beginEndIndexList = null;
	/**
	 * 생성자
	 */
	public BeginEndIndexData(){
		
	}

	public void addBeginEndIndex(int startIndex, int endIndex){
		
		if(beginEndIndexList == null){
			beginEndIndexList = new ArrayList<>();
		}
		BeginEndIndex positionIndex = new BeginEndIndex(startIndex, endIndex);
		beginEndIndexList.add(positionIndex);
	
	
	}

	public void addBeginEndIndex(BeginEndIndex positionIndex){
		if(beginEndIndexList == null){
			beginEndIndexList = new ArrayList<>();
		}
		beginEndIndexList.add(positionIndex);
	}
	

	public void addAll(Collection<BeginEndIndex> indexColl){
		if(indexColl.isEmpty()){
			return ;
		}
		if(beginEndIndexList == null){
			beginEndIndexList = new ArrayList<>();
		}
		
		beginEndIndexList.addAll(indexColl);
		
	}

	public List<BeginEndIndex> getBeginEndIndexList() {
		if(beginEndIndexList == null){
			return Collections.emptyList();
		}
		return beginEndIndexList;
	}
	

	public boolean isData(){
		return beginEndIndexList != null && !beginEndIndexList.isEmpty();
	}

	public int size(){
		if(beginEndIndexList == null){
			return 0 ;
		}

		return beginEndIndexList.size();
	}
}