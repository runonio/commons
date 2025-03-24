package io.runon.commons.data;

import lombok.Data;

@Data
public class BeginEndTextData implements BeginEndText{


    String text;

    int begin;
    int end;

    public BeginEndTextData(String text, int begin, int end) {
        this.text = text;
        this.begin = begin;
        this.end = end;
    }

    public BeginEndTextData() {

    }

}
