

package io.runon.commons.utils.string.highlight;

import io.runon.commons.data.StartEnd;

/**
 * 하이라이트 키워드
 * @author macle
 */

class HighlightKeyword implements StartEnd {
//    int index;
    int start;
    int end;

    @Override
    public int getStart() {
        return start;
    }

    @Override
    public int getEnd() {
        return end;
    }
}
