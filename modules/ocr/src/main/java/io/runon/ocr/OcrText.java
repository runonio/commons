package io.runon.ocr;

import lombok.Data;

@Data
public class OcrText {

    public enum Type{
        ERROR
        , SUCCESS
    }
    Type type;
    String text;


    public OcrText(){

    }

    public OcrText(String text){
        type = Type.SUCCESS;
        this.text = text;
    }

    public OcrText( Type type, String text){
        this.type = type;
        this.text = text;
    }


}
