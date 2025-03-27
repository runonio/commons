
package io.runon.jdbc.common;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * jdbc close util
 * @author macle
 */
public class JdbcClose {

    /**
     * Statement, ResultSet close
     * @param statement Statement
     * @param resultSet ResultSet
     */
    public static void statementResultSet(Statement statement, ResultSet resultSet ){

        if(resultSet != null){
            //noinspection CatchMayIgnoreException
            try{resultSet.close();}catch(Exception e){}
        }

        if(statement != null){
            //noinspection CatchMayIgnoreException
            try{statement.close();}catch(Exception e){}
        }

    }

}
