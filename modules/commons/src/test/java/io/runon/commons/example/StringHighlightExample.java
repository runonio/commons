

package io.runon.commons.example;

import io.runon.commons.data.BeginEnd;
import io.runon.commons.utils.string.highlight.StringHighlight;

/**
 * @author macle
 */
public class StringHighlightExample {

    public static void main(String[] args) {

        String text = "섬세한사람들 작더라도 쓸모있게 만드는 개발자 모임이다.";

        BeginEnd se1 = new BeginEnd() {
            @Override
            public int getBegin() {
                return 0;
            }

            @Override
            public int getEnd() {
                return 6;
            }
        };

        BeginEnd se2 = new BeginEnd() {
            @Override
            public int getBegin() {
                return 21;
            }

            @Override
            public int getEnd() {
                return 24;
            }
        };

        BeginEnd se3 = new BeginEnd() {
            @Override
            public int getBegin() {
                return 25;
            }

            @Override
            public int getEnd() {
                return 27;
            }
        };

        BeginEnd[] beginEnds = new BeginEnd[3];
        beginEnds[0] = se1;
        beginEnds[1] = se2;
        beginEnds[2] = se3;

        System.out.println(StringHighlight.make(text,"<em>","</em>", beginEnds));
    }

}
