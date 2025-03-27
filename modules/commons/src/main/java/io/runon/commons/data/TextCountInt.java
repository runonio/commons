package io.runon.commons.data;

import java.util.Comparator;

/**
 * @author macle
 */
public class TextCountInt extends CountInt{


    public final static Comparator<TextCountInt> SORT_DESC = (c1, c2) -> Integer.compare(c2.count, c1.count);

    private String text;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
