
package io.runon.jdbc.sequence;

import io.runon.jdbc.JdbcQuery;

/**
 * maria 유형의 시퀀스 생성기
 * @author macle
 */
public class MariaSequenceMaker implements SequenceMaker{

    @Override
    public String nextVal(String sequenceName) {
        return JdbcQuery.getResultOne("SELECT NEXT VALUE FOR " + sequenceName);
    }

    @Override
    public long nextLong(String sequenceName) {
        return JdbcQuery.getResultLong("SELECT NEXT VALUE FOR " + sequenceName);
    }

}