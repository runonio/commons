
package io.runon.commons.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author macle
 */
public class StartEndIndexData {
	
	
	@SuppressWarnings("StaticInitializerReferencesSubClass")
	public static final StartEndIndexData EMPTY_DATA = new StartEndIndexEmpty();
	
	private List<StartEndIndex> startEndIndexList = null;
	/**
	 * 생성자
	 */
	public StartEndIndexData(){
		
	}

	public void addStartEndIndex(int startIndex, int endIndex){
		
		if(startEndIndexList == null){
			startEndIndexList = new ArrayList<>();
		}
		StartEndIndex positionIndex = new StartEndIndex(startIndex, endIndex);
		startEndIndexList.add(positionIndex);
	
	
	}

	public void addStartEndIndex(StartEndIndex positionIndex){
		if(startEndIndexList == null){
			startEndIndexList = new ArrayList<>();
		}
		startEndIndexList.add(positionIndex);
	}
	

	public void addAll(Collection<StartEndIndex> indexColl){
		if(indexColl.isEmpty()){
			return ;
		}
		if(startEndIndexList == null){
			startEndIndexList = new ArrayList<>();
		}
		
		startEndIndexList.addAll(indexColl);
		
	}

	public List<StartEndIndex> getStartEndIndexList() {
		if(startEndIndexList == null){
			return Collections.emptyList();
		}
		return startEndIndexList;
	}
	

	public boolean isData(){
		return startEndIndexList != null && !startEndIndexList.isEmpty();
	}

	public int size(){
		if(startEndIndexList == null){
			return 0 ;
		}

		return startEndIndexList.size();
	}
}