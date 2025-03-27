
package io.runon.jdbc.common;

import java.sql.ResultSet;
import java.sql.Statement;
/**
 * Statement
 * ResultSet
 * 정보성 클래스
 * @author macle
 */
public class StmtResultSet {

    Statement stmt = null;
    ResultSet resultSet = null;

    StmtResultSet(){


    }

    /**
     * Statement 얻기
     * @return Statement
     */
    public Statement getStmt() {
        return stmt;
    }

    /**
     * ResultSet 얻기
     * @return ResultSet
     */
    public ResultSet getResultSet() {
        return resultSet;
    }
}
