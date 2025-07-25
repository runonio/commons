

package io.runon.commons.data;

import java.util.Collections;
import java.util.List;
/**
 * @author macle
 */
public class StartEndIndexEmpty extends StartEndIndexData {
	/**
	 * 생성자
	 */
	StartEndIndexEmpty(){
		
	}

	@Override
	public void addStartEndIndex(int startIndex, int endIndex){
		
	}

	@Override
	public List<StartEndIndex> getStartEndIndexList() {
		return Collections.emptyList();
	}


	public boolean isData(){

		return false;
	}
}
