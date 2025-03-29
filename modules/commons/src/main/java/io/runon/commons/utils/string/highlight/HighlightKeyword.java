

package io.runon.commons.utils.string.highlight;

import io.runon.commons.data.BeginEnd;

/**
 * 하이라이트 키워드
 * @author macle
 */

class HighlightKeyword implements BeginEnd {
//    int index;
    int begin;
    int end;

    @Override
    public int getBegin() {
        return begin;
    }

    @Override
    public int getEnd() {
        return end;
    }
}
