
package io.runon.commons.utils.string.highlight;

import io.runon.commons.data.StartEnd;
import io.runon.commons.data.StartEndText;
import io.runon.commons.exception.OutOfRangeException;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 문자열 하이라이트
 * @author macle
 */
public class StringHighlight {


    public static String make(String text, StartEndText[] tokens, StartEnd[] splitStartEnds, String [] keywords, String pre, String post, int length ){

        //글자 길이수 역순으로 정렬
        Arrays.sort(keywords, (a, b)->Integer.compare(b.length(), a.length()));

        HighlightSearch search = new HighlightSearch();

        outer:
        for (int i = 0; i <tokens.length ; i++) {
            StartEndText token = tokens[i];
            for (int jj = 0; jj <keywords.length ; jj++) {
                String keyword = keywords[jj];

                if(  token.getText().equals(keyword)){
                    HighlightKeyword highlightKeyword = new HighlightKeyword();
                    highlightKeyword.start = token.getStart();
                    highlightKeyword.end = token.getEnd();
                    search.add(highlightKeyword);
                }


                String subText = text.substring(token.getStart(), token.getEnd());

                if(
                        //변형어 토큰으로인해 위치가 같을때만 앞뒤를 추가로 비교한다.
                        subText.equals(token.getText())
                        &&
                        (
                                ( keyword.length() > 1 && token.getText().startsWith(keyword) )
                                || ( keyword.length() > 1 && token.getText().endsWith(keyword) )
                        )
                ){


                    HighlightKeyword highlightKeyword = new HighlightKeyword();
//                    highlightKeyword.index = jj;

                    int tokenLength = token.getEnd()-token.getStart();
                    if(tokenLength == keyword.length()){
                        highlightKeyword.start = token.getStart();
                        highlightKeyword.end = token.getEnd();
                    }else if(token.getText().startsWith(keyword)){
                        highlightKeyword.start = token.getStart();
                        highlightKeyword.end = token.getEnd() - (tokenLength - keyword.length());
                    }else{
                        highlightKeyword.start = token.getStart() + (tokenLength - keyword.length());
                        highlightKeyword.end = token.getEnd();
                    }

                    search.add(highlightKeyword);

                }
            }
        }

        if(search.list.isEmpty()){
            if(length > text.length()){
                return text;
            }else{
                return text.substring(0, length);
            }
        }

        int highlightStart;
        int highlightEnd;

        if(text.length() < length){
            highlightStart = 0;
            highlightEnd = text.length();
        }else{
            highlightStart = search.list.get(0).start;
            highlightEnd = search.list.get(search.list.size()-1).end;

            int gap = length - (highlightEnd - highlightStart);

            //범위확장 가능여부 체크
            int splitStart = -1;

            for (StartEnd startEnd : splitStartEnds) {
                if (startEnd.getStart() <= highlightStart && startEnd.getEnd() > highlightStart) {
                    splitStart = startEnd.getStart();
                    break;
                }
            }

            if(splitStart == -1){
                throw new OutOfRangeException("splitStartEnds error");
            }

            //앞부분 확장
            if( highlightEnd - splitStart < length){
                highlightStart = splitStart;

            }else{

                int spaceIndex = text.lastIndexOf(' ', highlightStart);
                if(spaceIndex == -1){
                    if(highlightStart < gap){
                        highlightStart = 0;
                    }
                }else{

                    int checkIndex = spaceIndex+1;

                    if(checkIndex != highlightStart && highlightStart - checkIndex < gap){
                        highlightStart = checkIndex;
                    }
                }
            }

            //뒷 부분 확장
            gap = length - (highlightEnd - highlightStart);

            highlightEnd = Math.min(highlightEnd + gap, text.length());
        }

        HighlightKeyword [] highlightKeywords = search.list.toArray(new HighlightKeyword[0]);
        for(HighlightKeyword highlightKeyword : highlightKeywords){
            highlightKeyword.start -= highlightStart;
            highlightKeyword.end -= highlightStart;

        }

        if(highlightEnd == text.length()){
            return make (text.substring(highlightStart, highlightEnd), pre, post, highlightKeywords);
        }else{
            return make (text.substring(highlightStart, highlightEnd), pre, post, highlightKeywords) + "...";
        }
    }

    public static String make(String text, String pre, String post, StartEnd[] startEnds){

        Arrays.sort(startEnds, Comparator.comparingInt(StartEnd::getStart));

        StringBuilder sb = new StringBuilder();
        int lastIndex = 0;

        for(StartEnd startEnd : startEnds){
            if(lastIndex > text.length()){
                break;
            }

            if(startEnd.getStart() > text.length()){
                break;
            }

            if(lastIndex > startEnd.getStart()){
                continue;
            }

            int end = startEnd.getEnd();
            if(end > text.length()){
                end = text.length();
            }

            sb.append(text, lastIndex, startEnd.getStart());

            sb.append(pre).append(text, startEnd.getStart(), end).append(post);
            lastIndex = startEnd.getEnd();
        }

        if(lastIndex < text.length()){
            sb.append(text, lastIndex, text.length());
        }

        return sb.toString();
    }
}