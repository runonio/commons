
package io.runon.commons.data;
/**
 * @author macle
 */
public class TextStartEndIndex extends StartEndIndex {
	protected String text;
	
	
	public TextStartEndIndex(){
		
	}
	public void setText(String text) {
		this.text = text;
	}


	public TextStartEndIndex(String text){
		this.text = text;
	}
	public TextStartEndIndex(String text, int startIndex, int endIndex){
		super(startIndex, endIndex);
		this.text = text;
	}

	public String getText() {
		return text;
	}

}
