

package io.runon.commons.data;

import java.util.Collections;
import java.util.List;
/**
 * @author macle
 */
public class BeginEndIndexEmpty extends BeginEndIndexData {
	/**
	 * 생성자
	 */
	BeginEndIndexEmpty(){
		
	}

	@Override
	public void addBeginEndIndex(int startIndex, int endIndex){
		
	}

	@Override
	public List<BeginEndIndex> getBeginEndIndexList() {
		return Collections.emptyList();
	}


	public boolean isData(){

		return false;
	}
}
