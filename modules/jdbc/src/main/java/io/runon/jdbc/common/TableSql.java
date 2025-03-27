
package io.runon.jdbc.common;

import io.runon.jdbc.annotation.Table;
import io.runon.jdbc.exception.TableNameEmptyException;

/**
 * table annotation 을 활용한 자동 쿼리 생성
 * @author macle
 */
public class TableSql {

    /**
     * table annotation 을 활용하여 생성한 쿼리 얻기 
     * @param table Table annotation
     * @param whereValue String
     * @param orderByValue String
     * @return String query
     */
    public static String getWhereOrderBySql(Table table, String whereValue, String orderByValue){

        StringBuilder sqlBuilder = new StringBuilder();

        if( whereValue != null){
            sqlBuilder.append(" WHERE ").append(whereValue);
        }else{
            String where = table.where();
            if(!where.equals(Table.EMPTY)){
                sqlBuilder.append(" WHERE ").append(where);
            }
        }

        if(orderByValue != null) {
            sqlBuilder.append(" ORDER BY ").append(orderByValue);
        }else {
            String orderBy = table.orderBy();
            if(!orderBy.equals(Table.EMPTY)){
                sqlBuilder.append(" ORDER BY ").append(orderBy);
            }
        }
        return sqlBuilder.toString();
    }

    /**
     * 테이블 명 얻기
     * @param table Table  annotation
     * @param objClassName String class name
     * @return String table name
     */
    public static String getTableName(Table table, String objClassName){
        String tableName = table.name();
        if(tableName.equals(Table.EMPTY)){
            throw new TableNameEmptyException(objClassName);
        }
        return tableName;
    }

}
