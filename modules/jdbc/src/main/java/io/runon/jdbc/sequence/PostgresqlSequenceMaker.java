

package io.runon.jdbc.sequence;

import io.runon.jdbc.JdbcQuery;

/**
 * postgresql 유형의 데이터베이스
 * @author macle
 */
public class PostgresqlSequenceMaker implements SequenceMaker{

    @Override
    public String nextVal(String sequenceName) {
        return JdbcQuery.getResultOne("SELECT nextval('"+sequenceName+"')");
    }

    @Override
    public long nextLong(String sequenceName) {
        return JdbcQuery.getResultLong("SELECT nextval('"+sequenceName+"')");
    }


}