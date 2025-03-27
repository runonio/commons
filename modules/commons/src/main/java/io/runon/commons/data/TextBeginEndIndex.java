
package io.runon.commons.data;
/**
 * @author macle
 */
public class TextBeginEndIndex extends BeginEndIndex {
	protected String text;
	
	
	public TextBeginEndIndex(){
		
	}
	public void setText(String text) {
		this.text = text;
	}


	public TextBeginEndIndex(String text){
		this.text = text;
	}
	public TextBeginEndIndex(String text, int startIndex, int endIndex){
		super(startIndex, endIndex);
		this.text = text;
	}

	public String getText() {
		return text;
	}

}
