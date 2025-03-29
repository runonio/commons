
package io.runon.commons.utils.string.highlight;

import io.runon.commons.data.BeginEnd;
import io.runon.commons.data.BeginEndText;
import io.runon.commons.exception.OutOfRangeException;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 문자열 하이라이트
 * @author macle
 */
public class StringHighlight {


    public static String make(String text, BeginEndText[] tokens,  BeginEnd[] splitBeginEnds, String [] keywords, String pre, String post, int length ){

        //글자 길이수 역순으로 정렬
        Arrays.sort(keywords, (a, b)->Integer.compare(b.length(), a.length()));

        HighlightSearch search = new HighlightSearch();

        outer:
        for (int i = 0; i <tokens.length ; i++) {
            BeginEndText token = tokens[i];
            for (int jj = 0; jj <keywords.length ; jj++) {
                String keyword = keywords[jj];

                if(  token.getText().equals(keyword)){
                    HighlightKeyword highlightKeyword = new HighlightKeyword();
                    highlightKeyword.begin = token.getBegin();
                    highlightKeyword.end = token.getEnd();
                    search.add(highlightKeyword);
                }


                String subText = text.substring(token.getBegin(), token.getEnd());

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

                    int tokenLength = token.getEnd()-token.getBegin();
                    if(tokenLength == keyword.length()){
                        highlightKeyword.begin = token.getBegin();
                        highlightKeyword.end = token.getEnd();
                    }else if(token.getText().startsWith(keyword)){
                        highlightKeyword.begin = token.getBegin();
                        highlightKeyword.end = token.getEnd() - (tokenLength - keyword.length());
                    }else{
                        highlightKeyword.begin = token.getBegin() + (tokenLength - keyword.length());
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

        int highlightBegin;
        int highlightEnd;

        if(text.length() < length){
            highlightBegin = 0;
            highlightEnd = text.length();
        }else{
            highlightBegin = search.list.get(0).begin;
            highlightEnd = search.list.get(search.list.size()-1).end;

            int gap = length - (highlightEnd - highlightBegin);

            //범위확장 가능여부 체크
            int splitBegin = -1;

            for (BeginEnd beginEnd : splitBeginEnds) {
                if (beginEnd.getBegin() <= highlightBegin && beginEnd.getEnd() > highlightBegin) {
                    splitBegin = beginEnd.getBegin();
                    break;
                }
            }

            if(splitBegin == -1){
                throw new OutOfRangeException("splitBeginEnds error");
            }

            //앞부분 확장
            if( highlightEnd - splitBegin < length){
                highlightBegin = splitBegin;

            }else{

                int spaceIndex = text.lastIndexOf(' ', highlightBegin);
                if(spaceIndex == -1){
                    if(highlightBegin < gap){
                        highlightBegin = 0;
                    }
                }else{

                    int checkIndex = spaceIndex+1;

                    if(checkIndex != highlightBegin && highlightBegin - checkIndex < gap){
                        highlightBegin = checkIndex;
                    }
                }
            }

            //뒷 부분 확장
            gap = length - (highlightEnd - highlightBegin);

            highlightEnd = Math.min(highlightEnd + gap, text.length());
        }

        HighlightKeyword [] highlightKeywords = search.list.toArray(new HighlightKeyword[0]);
        for(HighlightKeyword highlightKeyword : highlightKeywords){
            highlightKeyword.begin -= highlightBegin;
            highlightKeyword.end -= highlightBegin;

        }

        if(highlightEnd == text.length()){
            return make (text.substring(highlightBegin, highlightEnd), pre, post, highlightKeywords);
        }else{
            return make (text.substring(highlightBegin, highlightEnd), pre, post, highlightKeywords) + "...";
        }
    }

    public static String make(String text, String pre, String post, BeginEnd[] beginEnds){

        Arrays.sort(beginEnds, Comparator.comparingInt(BeginEnd::getBegin));

        StringBuilder sb = new StringBuilder();
        int lastIndex = 0;

        for(BeginEnd beginEnd : beginEnds){
            if(lastIndex > text.length()){
                break;
            }

            if(beginEnd.getBegin() > text.length()){
                break;
            }

            if(lastIndex > beginEnd.getBegin()){
                continue;
            }

            int end = beginEnd.getEnd();
            if(end > text.length()){
                end = text.length();
            }

            sb.append(text, lastIndex, beginEnd.getBegin());

            sb.append(pre).append(text, beginEnd.getBegin(), end).append(post);
            lastIndex = beginEnd.getEnd();
        }

        if(lastIndex < text.length()){
            sb.append(text, lastIndex, text.length());
        }

        return sb.toString();
    }
}