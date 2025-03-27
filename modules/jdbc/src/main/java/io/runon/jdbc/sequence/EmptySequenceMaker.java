
package io.runon.jdbc.sequence;
/**
 * 시퀀스를 돌려 주지 못함
 * database 유형이 잘못 되었을때
 * @author macle
 */
public class EmptySequenceMaker implements SequenceMaker {
    @Override
    public String nextVal(String sequenceName) {
        return null;
    }

    @Override
    public long nextLong(String sequenceName) {
        return 0;
    }
}
