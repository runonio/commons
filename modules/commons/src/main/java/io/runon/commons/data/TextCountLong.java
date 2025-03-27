package io.runon.commons.data;

import java.util.Comparator;
/**
 * @author macle
 */
public class TextCountLong extends CountLong {


    public final static Comparator<TextCountLong> SORT_DESC = (c1, c2) -> Long.compare(c2.count, c1.count);
    private String text;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TextCountLong() {
    }

    public TextCountLong(String str, long count) {
        this.text = str;
        this.count = count;
    }
    public TextCountLong(String str, String countValue) {
        this.text = str;
        this.count = Long.parseLong(countValue);
    }

    @Override
    public String toString() {
        return text +"\t" + count;
    }


}
