

package io.runon.commons.example;

import io.runon.commons.data.StartEnd;
import io.runon.commons.utils.string.highlight.StringHighlight;

/**
 * @author macle
 */
public class StringHighlightExample {

    public static void main(String[] args) {

        String text = "섬세한사람들 작더라도 쓸모있게 만드는 개발자 모임이다.";

        StartEnd se1 = new StartEnd() {
            @Override
            public int getStart() {
                return 0;
            }

            @Override
            public int getEnd() {
                return 6;
            }
        };

        StartEnd se2 = new StartEnd() {
            @Override
            public int getStart() {
                return 21;
            }

            @Override
            public int getEnd() {
                return 24;
            }
        };

        StartEnd se3 = new StartEnd() {
            @Override
            public int getStart() {
                return 25;
            }

            @Override
            public int getEnd() {
                return 27;
            }
        };

        StartEnd[] startEnds = new StartEnd[3];
        startEnds[0] = se1;
        startEnds[1] = se2;
        startEnds[2] = se3;

        System.out.println(StringHighlight.make(text,"<em>","</em>", startEnds));
    }

}
