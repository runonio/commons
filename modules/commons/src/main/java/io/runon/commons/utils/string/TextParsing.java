package io.runon.commons.utils.string;

/**
 * 이곳에서 새로 발견된 부분에 대한 정의는 여기서 한다.
 *
 * @author macle
 */
public class TextParsing {

    /**
     * 한라인내에서 발견된 텍스트 제거
     */
    public static String replaceInLine(String text, String startText, String endText, String replace){

        if(text == null){
            return null;
        }
        if("".equals(text)){
            return "";
        }

        String [] lines = text.split("\n");

        StringBuilder sb = new StringBuilder();

        for(String line : lines){
            sb.append("\n");

            String changeLine = line;
            for(;;) {
                String last = changeLine;

                int startIndex = changeLine.indexOf(startText);
                if (startIndex == -1) {
                    break;
                }

                int endIndex = changeLine.indexOf(endText, startIndex + startText.length());
                if(endIndex == -1){
                    break;
                }

                changeLine = changeLine.substring(0, startIndex) + replace + changeLine.substring(endIndex+endText.length());
                if(last.equals(changeLine)){
                    break;
                }
            }

            sb.append(changeLine);

        }
        
        return sb.substring(1);
    }

    public static String replaceNumberChar(String text, String startText, String endText){

        if(text == null){
            return null;
        }
        if("".equals(text)){
            return "";
        }

        String [] lines = text.split("\n");

        StringBuilder sb = new StringBuilder();

        for(String line : lines){
            sb.append("\n");

            String changeLine = line;

            int fromIndex = 0;
            for(;;) {
                String last = changeLine;

                int startIndex = changeLine.indexOf(startText, fromIndex);
                if (startIndex == -1) {
                    break;
                }

                int endIndex = changeLine.indexOf(endText, startIndex + startText.length());
                if(endIndex == -1){
                    break;
                }

                String numberValue = changeLine.substring(startIndex + startText.length(), endIndex);
                if(Check.isNumber(numberValue)){

                    int chNumber = Integer.parseInt(numberValue);
                    char ch = (char)chNumber;

                    changeLine = changeLine.substring(0, startIndex) + ch + changeLine.substring(endIndex+endText.length());
                    fromIndex = 0;
                    if(last.equals(changeLine)){
                        break;
                    }
                }else {
                    fromIndex = startIndex + startText.length();
                }
            }

            sb.append(changeLine);

        }

        return sb.substring(1);
    }


    public static String removeText(String text, String startStr, String endStr){
        String str = text;

        for(;;){
            int index = str.indexOf(startStr);
            if(index == -1){
                break;
            }

            int end = str.indexOf(endStr, index + startStr.length());
            if(end == -1){
                break;
            }
            str = str.substring(0, index) +
                    str.substring(end + endStr.length());

        }

        return str;
    }

}
