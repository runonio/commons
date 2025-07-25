package io.runon.commons.data;

import lombok.Data;

@Data
public class StartEndTextData implements StartEndText {


    String text;

    int start;
    int end;

    public StartEndTextData(String text, int start, int end) {
        this.text = text;
        this.start = start;
        this.end = end;
    }

    public StartEndTextData() {

    }

}
