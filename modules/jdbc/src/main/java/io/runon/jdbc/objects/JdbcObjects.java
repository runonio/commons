
package io.runon.jdbc.objects;

import io.runon.commons.callback.GenericCallBack;
import io.runon.commons.exception.ReflectiveOperationRuntimeException;
import io.runon.commons.utils.ExceptionUtil;
import io.runon.commons.utils.packages.classes.field.FieldUtil;
import io.runon.jdbc.Database;
import io.runon.jdbc.JdbcQuery;
import io.runon.jdbc.PrepareStatementData;
import io.runon.jdbc.annotation.Column;
import io.runon.jdbc.annotation.FlagBoolean;
import io.runon.jdbc.annotation.PrimaryKey;
import io.runon.jdbc.annotation.Table;

import io.runon.jdbc.common.*;
import io.runon.jdbc.connection.ApplicationConnectionPool;
import io.runon.jdbc.exception.FieldNullException;
import io.runon.jdbc.exception.PrimaryKeyNotSetException;
import io.runon.jdbc.exception.SQLRuntimeException;
import io.runon.jdbc.exception.TableNameEmptyException;
import io.runon.jdbc.naming.JdbcDataType;
import io.runon.jdbc.naming.JdbcNameDataType;
import io.runon.jdbc.naming.JdbcNamingDataType;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * JdbcObjects
 * class 에 컬럼 속성을 annotation 으로 정의하여 Db테이블과 일치화 하여 사용할떄 이용
 * DB용 객체와 사용 클래스를 일치화 할때 사용
 * @author macle
 */
@Slf4j
public class JdbcObjects {

    /**
     * List 얻기
     * @param conn Connection
     * @param objClass Class
     * @param <T> Table, Column annotation object
     * @return List
     * @throws IllegalAccessException IllegalAccessException
     * @throws SQLException SQLException
     * @throws InstantiationException InstantiationException
     */
    public static <T> List<T> getObjList(Connection conn, Class<T> objClass ) throws IllegalAccessException, SQLException, InstantiationException {
        return getObjList(conn, objClass, null, null, null, -1, null);

    }

    /**
     * List 얻기
     * @param conn Connection
     * @param objClass Class
     * @param whereValue String where query
     * @param <T> Table, Column annotation object
     * @return List
     * @throws IllegalAccessException IllegalAccessException
     * @throws SQLException SQLException
     * @throws InstantiationException InstantiationException
     */
    public static <T> List<T> getObjList(Connection conn, Class<T> objClass , String whereValue) throws IllegalAccessException, SQLException, InstantiationException {
        return getObjList(conn,  objClass, null, whereValue, null, -1, null);
    }


    /**
     * List 얻기
     * @param conn Connection
     * @param objClass Class
     * @param whereValue String where query
     * @param orderByValue String order by query
     * @param <T> Table, Column annotation object
     * @return List
     * @throws IllegalAccessException IllegalAccessException
     * @throws SQLException SQLException
     * @throws InstantiationException InstantiationException
     */
    public static <T> List<T> getObjList(Connection conn, Class<T> objClass , String whereValue, String orderByValue) throws IllegalAccessException, SQLException, InstantiationException {
        return getObjList(conn,  objClass, null, whereValue, orderByValue, -1, null);
    }

    /**
     * List 얻기
     * @param conn Connection
     * @param objClass Class
     * @param whereValue String where query
     * @param orderByValue String order by query
     * @param size int cut size
     * @param <T> Table, Column annotation object
     * @return List
     * @throws IllegalAccessException IllegalAccessException
     * @throws SQLException SQLException
     * @throws InstantiationException InstantiationException
     */
    public static <T> List<T> getObjList(Connection conn, Class<T> objClass , String whereValue, String orderByValue, int size) throws IllegalAccessException, SQLException, InstantiationException {
        return getObjList(conn,  objClass, null, whereValue, orderByValue, size, null);
    }

    /**
     * List 얻기
     * @param objClass Class
     * @param <T> Table, Column annotation object
     * @return List
     */
    public static <T> List<T> getObjList(Class<T> objClass ){
        try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
            return  getObjList(conn, objClass, null, null, null, -1, null);
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }catch(ReflectiveOperationException e){
            throw new ReflectiveOperationRuntimeException(e);
        }
    }

    /**
     * List 얻기
     * @param objClass Class
     * @param whereValue String where query
     * @param <T> Table, Column annotation object
     * @return List
     */
    public static <T> List<T> getObjList(Class<T> objClass , String whereValue){
        try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
            return getObjList(conn, objClass, null, whereValue, null, -1, null);
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }catch(ReflectiveOperationException e){
            throw new ReflectiveOperationRuntimeException(e);
        }
    }

    /**
     * List 얻기
     * @param objClass Class
     * @param whereValue String where query
     * @param size int cut size
     * @param <T> Table, Column annotation object
     * @return List
     */
    public static <T> List<T> getObjList(Class<T> objClass , String whereValue, int size){
        try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
            return getObjList(conn,  objClass, null, whereValue, null, size, null);
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }catch(ReflectiveOperationException e){
            throw new ReflectiveOperationRuntimeException(e);
        }
    }

    /**
     * List 얻기
     * @param objClass Class
     * @param whereValue String where query
     * @param orderByValue String order by query
     * @param <T> Table, Column annotation object
     * @return List
     */
    public static <T> List<T> getObjList(Class<T> objClass , String whereValue, String orderByValue){
        try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
            return getObjList(conn,  objClass, null, whereValue, orderByValue, -1, null);
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }catch(ReflectiveOperationException e){
            throw new ReflectiveOperationRuntimeException(e);
        }
    }

    /**
     * List 얻기
     * @param objClass Class
     * @param whereValue String where query
     * @param orderByValue String order by query
     * @param size int cut size
     * @param <T> Table, Column annotation object
     * @return List
     */
    public static <T> List<T> getObjList(Class<T> objClass , String whereValue, String orderByValue, int size){
        try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
            return getObjList(conn,  objClass, null, whereValue, orderByValue, size, null);
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }catch(ReflectiveOperationException e){
            throw new ReflectiveOperationRuntimeException(e);
        }
    }

    /**
     * List 얻기
     * @param objClass Class
     * @param whereValue String where query
     * @param prepareStatementDataMap Map 조건 데이터  date time 같이 database query 가 다른 경우
     * @param <T> Table, Column annotation object
     * @return List
     */
    public static <T> List<T> getObjList(Class<T> objClass , String whereValue, Map<Integer, PrepareStatementData> prepareStatementDataMap){
        try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
            return getObjList(conn,  objClass, null, whereValue, null, -1, prepareStatementDataMap);
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }catch(ReflectiveOperationException e){
            throw new ReflectiveOperationRuntimeException(e);
        }
    }

    /**
     * List 얻기
     * @param objClass Class
     * @param sql String sql
     * @param whereValue  String where query
     * @param prepareStatementDataMap  Map 조건 데이터  date time 같이 database query 가 다른 경우
     * @param <T> Table, Column annotation object
     * @return List
     */
    public static <T> List<T> getObjList(Class<T> objClass , String sql, String whereValue, Map<Integer, PrepareStatementData> prepareStatementDataMap){
        try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
            return getObjList(conn,  objClass, sql, whereValue, null, -1, prepareStatementDataMap);
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }catch(ReflectiveOperationException e){
            throw new ReflectiveOperationRuntimeException(e);
        }
    }

    /**
     * List 얻기
     * sql 활용
     * @param objClass Class
     * @param sql String sql
     * @param <T> Table, Column annotation object
     * @return List
     */
    public static <T> List<T> getSqlObjList(Class<T> objClass , String sql){
        try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
            return getObjList(conn,  objClass, sql, null, null, -1, null);
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }catch(ReflectiveOperationException e){
            throw new ReflectiveOperationRuntimeException(e);
        }
    }

    /**
     * List 얻기
     * @param conn Connection
     * @param objClass objClass Class
     * @param sql String sql
     * @param whereValue  String where query
     * @param prepareStatementDataMap Map 조건 데이터  date time 같이 database query 가 다른 경우
     * @param <T> Table, Column annotation object
     * @return List
     * @throws IllegalAccessException IllegalAccessException
     * @throws SQLException SQLException
     * @throws InstantiationException InstantiationException
     */
    public static <T> List<T> getObjList(Connection conn, Class<T> objClass , String sql, String whereValue, Map<Integer, PrepareStatementData> prepareStatementDataMap) throws IllegalAccessException, SQLException, InstantiationException {
        return getObjList(conn, objClass, sql, whereValue , null, -1 , prepareStatementDataMap);
    }

    public static <T> List<T> getObjList(Class<T> objClass , String sql, String whereValue, String orderByValue, Map<Integer, PrepareStatementData> prepareStatementDataMap)  {

        try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
            return getObjList(conn,  objClass, sql, whereValue, orderByValue, -1, prepareStatementDataMap);
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }catch(ReflectiveOperationException e){
            throw new ReflectiveOperationRuntimeException(e);
        }
    }


    /**
     *  List 얻기
     * @param conn Connection
     * @param objClass objClass Class
     * @param sql String sql
     * @param whereValue String where query
     * @param orderByValue String order by query
     * @param size int cut size
     * @param prepareStatementDataMap Map 조건 데이터  date time 같이 database query 가 다른 경우
     * @param <T> Table, Column annotation object
     * @return List
     * @throws IllegalAccessException IllegalAccessException
     * @throws SQLException SQLException
     * @throws InstantiationException InstantiationException
     */
    public static <T> List<T> getObjList(Connection conn, Class<T> objClass , String sql, String whereValue, String orderByValue, int size, Map<Integer, PrepareStatementData> prepareStatementDataMap) throws IllegalAccessException, SQLException, InstantiationException {

        final List<T> resultList = new ArrayList<>();
        GenericCallBack<T> callBack = resultList::add;
        callbackObj(conn, objClass, sql, whereValue, orderByValue, size, prepareStatementDataMap, callBack);
        return resultList;
    }


    public static <T> void callbackObj( Class<T> objClass , String whereValue, GenericCallBack callback){
        try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
                callbackObj(conn,  objClass, whereValue, null ,null,callback);
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }catch(ReflectiveOperationException e){
            throw new ReflectiveOperationRuntimeException(e);
        }
    }

    public static <T> void callbackObj(Connection conn, Class<T> objClass , String whereValue, String orderByValue,  Map<Integer, PrepareStatementData> prepareStatementDataMap, GenericCallBack callback) throws IllegalAccessException, SQLException, InstantiationException {
        callbackObj(conn, objClass, null, whereValue, orderByValue, -1, prepareStatementDataMap , callback);
    }

    public static <T> void callbackObj(Connection conn, Class<T> objClass , String sql, String whereValue, String orderByValue, int size, Map<Integer, PrepareStatementData> prepareStatementDataMap, GenericCallBack callback) throws IllegalAccessException, SQLException, InstantiationException {

        Table table = objClass.getAnnotation(Table.class);
        Map<String, Field> columnFieldMap = makeColumnFieldMap(objClass);

        String selectSql;
        if(sql == null) {
            selectSql = getSql(objClass, table, columnFieldMap.keySet(), whereValue, orderByValue);
        }else{
            selectSql = sql;
        }

        Statement stmt = null;
        ResultSet result = null;

        if(size == -1) {
            size = table.size();
        }

        //noinspection CaughtExceptionImmediatelyRethrown
        try{
            StmtResultSet stmtResultSet = JdbcCommon.makeStmtResultSet(conn, selectSql, prepareStatementDataMap, table.fetchSize());
            stmt = stmtResultSet.getStmt();
            result = stmtResultSet.getResultSet();

            if(size == -1){
                while(result.next()){
                    T resultObj = objClass.newInstance();

                    setFieldsValue(result, columnFieldMap, resultObj);

                    callback.callback(resultObj);
                }
            }else{
                int checkCount = 0;
                while(result.next()){
                    T resultObj = objClass.newInstance();
                    setFieldsValue(result, columnFieldMap, resultObj);
                    callback.callback(resultObj);
                    checkCount ++ ;
                    if(checkCount >= size)
                        break;
                }
            }

        }catch(Exception e){
            throw e;
        }finally{
            JdbcClose.statementResultSet(stmt, result);
        }
    }


    /**
     * Map make
     * @param objClass Class objClass
     * @param <T> Table, Column annotation object
     * @return Map
     */
    private static <T>  Map<String, Field> makeColumnFieldMap(Class<T> objClass){
        Field[] fields = FieldUtil.getFieldArrayToParents(objClass);
        Map<String, Field> columnFieldMap = new HashMap<>();
        for(Field field: fields){
            Column column = field.getAnnotation(Column.class);

            if(column != null){
                String name = column.name();
                if("".equals(name)){
                    columnFieldMap.put(field.getName(), field);
                }else{
                    columnFieldMap.put(name, field);
                }
            }
        }

        return columnFieldMap;
    }


    /**
     * 필드에 값 설정
     * @param result ResultSet
     * @param columnFieldMap Map
     * @param resultObj Object
     * @throws IllegalArgumentException ReflectiveOperation
     * @throws IllegalAccessException ReflectiveOperation
     * @throws SQLException SQLException
     */
    private static void setFieldsValue(ResultSet result, Map<String, Field> columnFieldMap, Object resultObj ) throws IllegalArgumentException, IllegalAccessException, SQLException{

        Set<String> columnNames = columnFieldMap.keySet();

        for(String columnName : columnNames){
            Field field = columnFieldMap.get(columnName);
            JdbcField.setFieldObject(result, field, columnName, resultObj);
        }
    }

    /**
     * Table annotation 을 활용하여 sql 생성
     * @param objClass Class objClass
     * @param table Table annotation
     * @param columnNameSet Set
     * @param whereValue String where query
     * @param orderByValue String order by query
     * @param <T> Table, Column annotation object
     * @return String sql
     */
    public static <T> String getSql(Class<T> objClass, Table table , Set<String> columnNameSet , String whereValue, String orderByValue){
        StringBuilder sqlBuilder = new StringBuilder();

        String tableSql = table.sql();
        if(tableSql.equals(Table.EMPTY)){

            if(table.name().equals(Table.EMPTY)){
                throw new TableNameEmptyException(objClass.getName());
            }

            if(columnNameSet == null || columnNameSet.size() ==0){
                throw new FieldNullException(objClass.getName());
            }
            StringBuilder fieldBuilder = new StringBuilder();

            for(String columnName : columnNameSet){
                fieldBuilder.append(", ").append(columnName);
            }
            sqlBuilder.append("SELECT ");
            sqlBuilder.append(fieldBuilder.substring(1));
            sqlBuilder.append(" FROM ").append(table.name());
        }else{
            sqlBuilder.append(tableSql);
        }

        sqlBuilder.append(TableSql.getWhereOrderBySql(table, whereValue, orderByValue));
        return sqlBuilder.toString();
    }




    /**
     * 객체결과 얻기
     * @param conn Connection
     * @param objClass Class objClass
     * @param <T> Table, Column annotation object
     * @return T Table, Column annotation object
     * @throws IllegalAccessException IllegalAccessException
     * @throws SQLException SQLException
     * @throws InstantiationException InstantiationException
     */
    public static <T> T getObj(Connection conn, Class<T> objClass ) throws IllegalAccessException, SQLException, InstantiationException {
        return getObj(conn,  objClass, null, null, null, null);
    }

    /**
     * 객체결과 얻기
     * @param conn Connection
     * @param objClass Class objClass
     * @param whereValue String where query
     * @param <T> Table, Column annotation object
     * @return T Table, Column annotation object
     * @throws IllegalAccessException IllegalAccessException
     * @throws SQLException SQLException
     * @throws InstantiationException InstantiationException
     */
    public static <T> T getObj(Connection conn, Class<T> objClass , String whereValue) throws IllegalAccessException, SQLException, InstantiationException {
        return getObj(conn,  objClass, null, whereValue, null, null);
    }


    /**
     * 객체결과 얻기
     * @param objClass Class objClass
     * @param <T> Table, Column annotation object
     * @return T Table, Column annotation object
     */
    public static <T> T getObj(Class<T> objClass ){
        try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
            return getObj(conn, objClass, null, null, null, null);
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }catch(ReflectiveOperationException e){
            throw new ReflectiveOperationRuntimeException(e);
        }
    }

    /**
     * 객체결과 얻기
     * @param objClass Class objClass
     * @param whereValue String where query
     * @param <T> Table, Column annotation object
     * @return T Table, Column annotation object
     */
    public static <T> T getObj(Class<T> objClass , String whereValue){
        try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
            return getObj(conn, objClass, null, whereValue, null, null);
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }catch(ReflectiveOperationException e){
            throw new ReflectiveOperationRuntimeException(e);
        }
    }

    /**
     *
     * 객체결과 얻기
     * @param objClass Class objClass
     * @param whereValue String where query
     * @param orderByValue String order by query
     * @param <T> Table, Column annotation object
     * @return T Table, Column annotation object
     */
    public static <T> T getObj(Class<T> objClass , String whereValue, String orderByValue){
        try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
            return getObj(conn, objClass, null, whereValue, orderByValue, null);
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }catch(ReflectiveOperationException e){
            throw new ReflectiveOperationRuntimeException(e);
        }
    }


    /**
     * 객체결과 얻기
     * @param objClass Class objClass
     * @param whereValue String where query
     * @param prepareStatementDataMap  Map 조건 데이터  date time 같이 database query 가 다른 경우
     * @param <T> Table, Column annotation object
     * @return T Table, Column annotation object
     */
    public static <T> T getObj(Class<T> objClass , String whereValue, Map<Integer, PrepareStatementData> prepareStatementDataMap){
        try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
            return getObj(conn, objClass, null, whereValue, null, prepareStatementDataMap);
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }catch(ReflectiveOperationException e){
            throw new ReflectiveOperationRuntimeException(e);
        }
    }

    /**
     * 객체결과 얻기
     * @param objClass Class objClass
     * @param sql String sql
     * @param whereValue String where query
     * @param prepareStatementDataMap  Map 조건 데이터  date time 같이 database query 가 다른 경우
     * @param <T> Table, Column annotation object
     * @return T Table, Column annotation object
     */
    public static <T> T getObj(Class<T> objClass , String sql, String whereValue, Map<Integer, PrepareStatementData> prepareStatementDataMap){
        try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
            return getObj(conn, objClass, sql, whereValue, null, prepareStatementDataMap);
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }catch(ReflectiveOperationException e){
            throw new ReflectiveOperationRuntimeException(e);
        }
    }

    /**
     * 객체결과 얻기
     * sql 활용
     * @param objClass Class objClass
     * @param sql String sql
     * @param <T> Table, Column annotation object
     * @return T Table, Column annotation object
     */
    public static <T> T getSqlObj(Class<T> objClass , String sql){
        try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
            return getObj(conn,  objClass, sql, null, null, null);
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }catch(ReflectiveOperationException e){
            throw new ReflectiveOperationRuntimeException(e);
        }
    }

    /**
     * 객체결과 얻기
     * @param conn Connection
     * @param objClass Class objClass
     * @param sql String sql
     * @param whereValue String where query
     * @param prepareStatementDataMap  Map 조건 데이터  date time 같이 database query 가 다른 경우
     * @param <T> Table, Column annotation object
     * @return T Table, Column annotation object
     * @throws IllegalAccessException IllegalAccessException
     * @throws SQLException SQLException
     * @throws InstantiationException InstantiationException
     */
    public static <T> T getObj(Connection conn, Class<T> objClass, String sql, String whereValue, Map<Integer, PrepareStatementData> prepareStatementDataMap) throws IllegalAccessException, SQLException, InstantiationException {
        return getObj(conn, objClass, sql, whereValue, null, prepareStatementDataMap);
    }

    /**
     * 객체결과 얻기
     * @param conn Connection
     * @param objClass Class objClass
     * @param sql String sql
     * @param whereValue String where query
     * @param orderByValue String order by query
     * @param prepareStatementDataMap  Map 조건 데이터  date time 같이 database query 가 다른 경우
     * @param <T> Table, Column annotation object
     * @return T Table, Column annotation object
     * @throws IllegalAccessException IllegalAccessException
     * @throws SQLException SQLException
     * @throws InstantiationException InstantiationException
     */
    public static <T> T getObj(Connection conn, Class<T> objClass, String sql, String whereValue, String orderByValue, Map<Integer, PrepareStatementData> prepareStatementDataMap) throws IllegalAccessException, SQLException, InstantiationException {


        Table table = objClass.getAnnotation(Table.class);

        Map<String, Field> columnFieldMap = makeColumnFieldMap(objClass);

        String selectSql;
        if(sql == null) {
            selectSql = getSql(objClass, table, columnFieldMap.keySet(), whereValue, orderByValue);
        }else{
            selectSql = sql;
        }

        Statement stmt = null;
        ResultSet result = null;
        T resultObj = null;

        //noinspection CaughtExceptionImmediatelyRethrown
        try{

            StmtResultSet stmtResultSet = JdbcCommon.makeStmtResultSet(conn, selectSql, prepareStatementDataMap);
            stmt = stmtResultSet.getStmt();
            result = stmtResultSet.getResultSet();
            result.setFetchSize(2);
            if(result.next()){
                resultObj = objClass.newInstance();
                setFieldsValue(result, columnFieldMap, resultObj);

            }
        }catch(Exception e){
            throw e;
        }finally{
            JdbcClose.statementResultSet(stmt, result);
        }

        return resultObj;
    }

    /**
     * upsert
     * @param objClassList List
     * @param <T> Table Column annotation object
     * @return int fail -1
     */
    public static <T> int upsert( List<T> objClassList){
        return upsert(objClassList, true);
    }


    /**
     * upsert
     * @param objClassList List
     * @param isClearParameters boolean
     * @param <T> Table Column annotation object
     * @return int fail -1
     */
    public static <T> int upsert( List<T> objClassList,   boolean isClearParameters){
        ApplicationConnectionPool connectionPool = ApplicationConnectionPool.getInstance();
        try(Connection conn = connectionPool.getConnection()){
            int result =  insert(conn, objClassList, "UPSERT", isClearParameters);
            if(!connectionPool.isAutoCommit()){
                conn.commit();
            }
            return result;
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }
    }

    /**
     * upsert
     * @param conn Connection
     * @param objClassList List
     * @param isClearParameters boolean
     * @param <T> Table Column annotation object
     * @return int fail -1
     */
    public static <T> int upsert(Connection conn, List<T> objClassList,   boolean isClearParameters){
        return insert(conn, objClassList , "UPSERT", isClearParameters);
    }

    /**
     * insert
     * @param objClassList List
     * @param <T> Table Column annotation object
     * @return int fail -1
     */
    public static <T> int insert( List<T> objClassList){
        ApplicationConnectionPool connectionPool = ApplicationConnectionPool.getInstance();
        try(Connection conn = connectionPool.getConnection()){
            int result =  insert(conn, objClassList, "INSERT", true);
            if(!connectionPool.isAutoCommit()){
                conn.commit();
            }
            return result;
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }
    }

    /**
     * insert
     * @param objClassList List
     * @param isClearParameters boolean
     * @param <T> Table Column annotation object
     * @return int fail -1
     */
    public static <T> int insert( List<T> objClassList, boolean isClearParameters){
        ApplicationConnectionPool connectionPool = ApplicationConnectionPool.getInstance();
        try(Connection conn = connectionPool.getConnection()){
            int result =  insert(conn, objClassList , "INSERT", isClearParameters);
            if(!connectionPool.isAutoCommit()){
                conn.commit();
            }
            return result;

        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }
    }


    /**
     * insert
     * @param conn Connection
     * @param objClassList List
     * @param isClearParameters boolean
     * @param <T> Table Column annotation object
     * @return int fail -1
     */
    public static <T> int insert(Connection conn, List<T> objClassList,   boolean isClearParameters){
        return insert(conn, objClassList , "INSERT", isClearParameters);
    }

    /**
     * insert
     * @param conn Connection
     * @param objClassList List
     * @param insertQueryValue String upsert, insert
     * @param isClearParameters boolean
     * @param <T> Table Column annotation object
     * @return int fail -1
     */
    public static <T> int insert(Connection conn, List<T> objClassList, String insertQueryValue,  boolean isClearParameters){
        if(objClassList == null || objClassList.size() ==0){
            return 0;
        }
        Class<?> objClass = objClassList.get(0).getClass();

        Map<String, Field> columnFieldMap = makeColumnFieldMap(objClass);
        String [] columnNames = columnFieldMap.keySet().toArray(new String[0]);

        //순서정보를 위한 세팅
        Field [] fields = new Field[columnNames.length];
        for (int i = 0; i <columnNames.length ; i++) {
            fields[i] = columnFieldMap.get(columnNames[i]);
        }

        String insertSql = getInsertSql(objClass, columnNames, insertQueryValue);

        return JdbcCommon.insert(conn, objClassList, fields, insertSql, isClearParameters);

    }


    public static <T> int insertOrUpdate(T obj){
        return insertOrUpdate(obj, false);
    }

    public static <T> int insertOrUpdate(Connection connection, T obj){
        return insertOrUpdate(connection, obj, false);
    }


    /**
     * 있으면 update, 없으면 insert
     * @param obj T
     * @param isNullUpdate boolean null value update flag
     * @param <T> Table Column annotation object
     * @return int fail -1
     */
    public static <T> int insertOrUpdate(T obj, boolean isNullUpdate){

        ApplicationConnectionPool connectionPool = ApplicationConnectionPool.getInstance();
        try(Connection conn = connectionPool.getCommitConnection()){
            int result = insertOrUpdate(conn, obj, isNullUpdate);
            if(!connectionPool.isAutoCommit()){
                conn.commit();
            }
            return result;
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }

    }

    /**
     * 있으면 update, 없으면 insert
     * @param conn Connection
     * @param obj T
     * @param isNullUpdate boolean null value update flag
     * @param <T> Table Column annotation object
     * @return int fail -1
     */
    public static <T> int insertOrUpdate(Connection conn, T obj , boolean isNullUpdate ){
        int successCount ;
        try{

            if(JdbcQuery.isRowData(conn, getRowCheckQuery(obj))){
                successCount = update(conn, obj, isNullUpdate);
            }else{
                successCount =insert(conn, obj);
            }

        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }

        return successCount;

    }

    /**
     * data check sql get
     * @param obj T
     * @param <T> Table Column annotation object
     * @return String check sql
     * @throws IllegalAccessException IllegalAccessException
     */
    public static <T> String getCheckWhere(T obj) throws IllegalAccessException {
        Class<?> objClass = obj.getClass();

        Map<String, Field> columnFieldMap = makeColumnFieldMap(objClass);
        String [] columnNames = columnFieldMap.keySet().toArray(new String[0]);
        //순서정보를 위한 세팅
        Field [] fields = new Field[columnNames.length];
        for (int i = 0; i <columnNames.length ; i++) {
            fields[i] = columnFieldMap.get(columnNames[i]);
        }

        Map<Field,String> columnNameMap = new HashMap<>();


        List<Field> pkColumnList = new LinkedList<>();

        for(int i=0 ; i<fields.length ; i++){
            fields[i].setAccessible(true);
            PrimaryKey pk  = fields[i].getAnnotation(PrimaryKey.class);
            if(pk != null){
                pkColumnList.add(fields[i]);
                columnNameMap.put(fields[i], columnNames[i]);
            }
        }

        if(pkColumnList.size() ==0){
            throw new PrimaryKeyNotSetException(objClass.getName());
        }

        pkColumnList.sort(JdbcCommon.PK_SORT_ASC);

        StringBuilder whereBuilder = new StringBuilder();
        //noinspection ForLoopReplaceableByForEach
        for(int i= 0 ; i < pkColumnList.size() ; i++){
            Field field = pkColumnList.get(i);
            field.setAccessible(true);
            if(field.isAnnotationPresent(FlagBoolean.class)){

                boolean flag  = (boolean)field.get(obj);

                if(flag){
                    whereBuilder.append(" AND ").append(columnNameMap.get(field)).append("='").append("Y").append("'");
                }else{
                    whereBuilder.append(" AND ").append(columnNameMap.get(field)).append("='").append("N").append("'");
                }

            }else if(field.getType().isEnum()){
                whereBuilder.append(" AND ").append(columnNameMap.get(field)).append("='").append(field.get(obj).toString()).append("'");
            }else{
                whereBuilder.append(" AND ").append(columnNameMap.get(field)).append("='").append(field.get(obj)).append("'");
            }
        }

        return whereBuilder.substring(4);
    }

    public static <T> String getRowCheckQuery(T obj){
        Class<?> objClass = obj.getClass();
        String tableName = TableSql.getTableName(objClass.getAnnotation(Table.class), objClass.getName());

        Field[] fields = FieldUtil.getFieldArrayToParents(objClass);
        int firstSeq = Integer.MAX_VALUE;
        String firstPk = null;
        for (Field field : fields) {
            field.setAccessible(true);
            PrimaryKey pk = field.getAnnotation(PrimaryKey.class);
            Column column = field.getAnnotation(Column.class);

            if (column != null) {

                if (pk != null) {
                    int seq = pk.seq();
                    if (seq < firstSeq) {
                        firstSeq = seq;
                        firstPk = column.name();
                    }
                }
            }
        }
        if(firstPk == null){
            throw new PrimaryKeyNotSetException(obj.getClass().getName());
        }
        try {
            return "select " + firstPk + "  from " + tableName + " where " + getCheckWhere(obj);
        }catch (ReflectiveOperationException e){
            throw new ReflectiveOperationRuntimeException(e);
        }
    }





    /**
     * upsert
     * @param conn Connection
     * @param obj T
     * @param <T> Table, Column annotation object
     * @return int fail -1
     */
    public static <T> int upsert(Connection conn, T obj){
        return insert(conn, obj, "UPSERT");
    }


    /**
     * upsert
     * @param obj T
     * @param <T> Table, Column annotation object
     * @return int fail -1
     */
    public static <T> int upsert(T obj){
        ApplicationConnectionPool connectionPool = ApplicationConnectionPool.getInstance();
        try(Connection conn = connectionPool.getConnection()){

            int result =  insert(conn, obj, "UPSERT");
            if(!connectionPool.isAutoCommit()){
                conn.commit();
            }
            return result;
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }
    }

    /**
     * insert
     * @param obj T
     * @param <T> Table Column annotation object
     * @return int fail -1
     */
    public static <T> int insert(T obj){
        ApplicationConnectionPool connectionPool = ApplicationConnectionPool.getInstance();
        try(Connection conn = connectionPool.getConnection()){

            int result =   insert(conn, obj, "INSERT");
            if(!connectionPool.isAutoCommit()){
                conn.commit();
            }
            return result;

        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }
    }

    /**
     * insert
     * @param conn Connection
     * @param obj T
     * @param <T> Table Column annotation object
     * @return int fail -1
     */
    public static <T> int insert(Connection conn, T obj){
        return insert(conn, obj, "INSERT");
    }

    /**
     * insert
     * @param conn Connection
     * @param obj T
     * @param insertQueryValue String upsert, insert
     * @param <T> Table Column annotation object
     * @return int fail -1
     */
    public static <T> int insert(Connection conn, T obj, String insertQueryValue){

        Class<?> objClass = obj.getClass();
        Map<String, Field> columnFieldMap = makeColumnFieldMap(objClass);
        String [] columnNames = columnFieldMap.keySet().toArray(new String[0]);
        String insertSql = getInsertSql(objClass, columnNames, insertQueryValue);
        //순서정보를 위한 세팅
        Field [] fields = new Field[columnNames.length];
        for (int i = 0; i <columnNames.length ; i++) {
            fields[i] = columnFieldMap.get(columnNames[i]);
        }

        return JdbcCommon.insert(conn, obj, fields, insertSql);
    }

    /**
     * insert sql get
     * @param objClass Class
     * @param columnNames String []
     * @param insertQueryValue String upsert, insert
     * @return String sql
     */
    public static String getInsertSql(Class<?> objClass, String [] columnNames, String insertQueryValue){
        Table table = objClass.getAnnotation(Table.class);

        String tableName = TableSql.getTableName(table, objClass.getName());

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(insertQueryValue).append(" INTO ").append(tableName).append(" (");

        StringBuilder fieldBuilder = new StringBuilder();

        //noinspection ForLoopReplaceableByForEach
        for(int i=0 ; i<columnNames.length ; i++){
            fieldBuilder.append(", ").append(columnNames[i]);
        }
        sqlBuilder.append(fieldBuilder.substring(1));
        sqlBuilder.append(") VALUES (");


        fieldBuilder.setLength(0);
        for(int i=0 ; i<columnNames.length ; i++){
            fieldBuilder.append(", ?");
        }
        sqlBuilder.append(fieldBuilder.substring(1));
        sqlBuilder.append(" )");

        return sqlBuilder.toString();
    }
    public static <T> int update(T obj ) {
        return update(obj, false);
    }
    /**
     * update
     * @param obj T
     * @param isNullUpdate boolean null value update flag
     * @param <T> Table Column annotation object
     * @return int fail -1
     */
    public static <T> int update(T obj , boolean isNullUpdate ) {
        ApplicationConnectionPool connectionPool = ApplicationConnectionPool.getInstance();
        try(Connection conn = connectionPool.getConnection()){
            int result =   update(conn, obj, isNullUpdate);
            if(!connectionPool.isAutoCommit()){
                conn.commit();
            }
            return result;

        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }
    }


    /**
     * update
     * @param conn Connection
     * @param obj T
     * @param isNullUpdate boolean null value update flag
     * @param <T> Table Column annotation object
     * @return int fail -1
     */
    public static <T> int update(Connection conn,T obj , boolean isNullUpdate ){

        Class<?> objClass = obj.getClass();
        String tableName = TableSql.getTableName(objClass.getAnnotation(Table.class), objClass.getName());

        Map<String, Field> columnFieldMap = makeColumnFieldMap(objClass);
        String [] columnNames = columnFieldMap.keySet().toArray(new String[0]);

        Field [] fields = new Field[columnNames.length];
        for (int i = 0; i <columnNames.length ; i++) {
            fields[i] = columnFieldMap.get(columnNames[i]);
        }

        Map<Field,String> columnNameMap = new HashMap<>();

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append("UPDATE ").append(tableName).append(" SET ");

        StringBuilder fieldBuilder = new StringBuilder();
        List<Field> pkColumnList = new LinkedList<>();

        for(int i=0 ; i<fields.length ; i++){
            fields[i].setAccessible(true);
            PrimaryKey pk = fields[i].getAnnotation(PrimaryKey.class);
            if(pk != null){
                pkColumnList.add(fields[i]);
                columnNameMap.put(fields[i], columnNames[i]);
                continue;
            }

            if(!isNullUpdate){
                try{
                    Object object = fields[i].get(obj);
                    if(object == null){
                        continue;
                    }
                }catch(Exception e){
                    log.error(ExceptionUtil.getStackTrace(e));
                }
            }

            fieldBuilder.append(", ").append(columnNames[i]).append("=?");
        }

        if(pkColumnList.size() ==0){
            throw new PrimaryKeyNotSetException(objClass.getName());
        }

        pkColumnList.sort(JdbcCommon.PK_SORT_ASC);



        sqlBuilder.append(fieldBuilder.substring(1));
        sqlBuilder.append(" WHERE ");

        fieldBuilder.setLength(0);

        //noinspection ForLoopReplaceableByForEach
        for(int i= 0 ; i < pkColumnList.size() ; i++){
            Field field = pkColumnList.get(i);
            fieldBuilder.append(" AND ").append(columnNameMap.get(field)).append("=?");
        }
        sqlBuilder.append(fieldBuilder.substring(4));
        PreparedStatement pstmt = null;

        int successCount;
        try{
            pstmt = conn.prepareStatement(sqlBuilder.toString());
            int index = JdbcCommon.setPrimaryKeyField(pstmt,fields,obj,isNullUpdate);

            //noinspection ForLoopReplaceableByForEach
            for(int i= 0 ; i < pkColumnList.size() ; i++){
                Field field = pkColumnList.get(i);
                Object object = field.get(obj);

                JdbcCommon.setPstmt(obj, object, field, pstmt, index);
                index++;
            }

            pstmt.addBatch();
            pstmt.clearParameters();

            pstmt.executeBatch();
            successCount = 1;
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }catch(ReflectiveOperationException e){
            throw new ReflectiveOperationRuntimeException(e);
        }finally{
            //noinspection CatchMayIgnoreException
            try{if(pstmt != null) pstmt.close();}catch(Exception e){}
        }

        return successCount;
    }


    /**
     * 데이터가 없을떄만 insert
     * @param obj T
     * @param <T> Table Column annotation object
     * @return int fail -1
     */
    public static <T> int insertIfNoData(T obj){
        ApplicationConnectionPool connectionPool = ApplicationConnectionPool.getInstance();
        try(Connection conn = connectionPool.getCommitConnection()){


            int result =  insertIfNoData(conn, obj);
            if(!connectionPool.isAutoCommit() && result != -1){
                conn.commit();
            }
            return result;
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }
    }

    /**
     * 데이터가 없을떄만 insert
     * @param conn Connection
     * @param obj T
     * @param <T> Table Column annotation object
     * @return int fail -1
     */
    public static <T> int insertIfNoData(Connection conn,T obj){

        int successCount = -1;

        try{

            Object checkObj = getObj(conn, obj.getClass(), getCheckWhere(obj));
            if(checkObj == null){
                successCount =insert(conn, obj);
            }
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }catch(ReflectiveOperationException e){
            throw new ReflectiveOperationRuntimeException(e);
        }

        return successCount;
    }

    /**
     * class 생성 도움 내용 생성
     * @param tableName String
     * @return String Table Column annotation String value
     */
    public static String makeObjectValue( String tableName) {

        try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
            return makeObjectValue(conn, tableName, true);
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }
    }

    public static String makeObjectValue( String tableName, boolean isPrivate) {

        try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
            return makeObjectValue(conn, tableName, isPrivate);
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }
    }

    /**
     * class 생성 도움 내용 생성
     * @param conn Connection
     * @param tableName String
     * @return String Table Column annotation String value
     */
    public static String makeObjectValue(Connection conn, String tableName, boolean isPrivate){
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n@Table(name=\"").append(tableName).append("\")");

        boolean isAnnotationPrimaryKey = false;
        boolean isAnnotationDateTime = false;
        boolean isAnnotationFlogBoolean = false;



        JdbcNamingDataType jdbcNamingDataType = JdbcNamingDataType.getInstance();
        try{
            Map<String, Integer> pkMap = Database.getPrimaryKeyColumnsForTable(conn, tableName);

            JdbcNameDataType [] nameTypes = Database.getColumns(conn, tableName);

            for (JdbcNameDataType nameType : nameTypes){

                sb.append("\n\n");

                String name = nameType.getName();

                Integer pkSeq = pkMap.get(name);
                if(pkSeq != null){
                    sb.append("@PrimaryKey(seq = ").append(pkSeq).append(")\n");
                    isAnnotationPrimaryKey = true;
                }

                JdbcDataType dataType = nameType.getDataType();


                boolean isFlagBoolean = false;

                if(dataType == JdbcDataType.DATE_TIME ){
                    sb.append("@DateTime\n");
                    isAnnotationDateTime = true;
                }else {
                    if(jdbcNamingDataType.isFrontPriority()){
                        //앞먼저 검사
                        if(jdbcNamingDataType.isFront() && name.toUpperCase().startsWith("FG_")){
                            sb.append("@FlagBoolean\n");
                            isFlagBoolean = true;
                        }else if(jdbcNamingDataType.isBack() && name.toUpperCase().endsWith("_FG")){
                            sb.append("@FlagBoolean\n");
                            isFlagBoolean = true;
                        }

                    }else{
                        if(jdbcNamingDataType.isBack() && name.toUpperCase().endsWith("_FG")){
                            sb.append("@FlagBoolean\n");
                            isFlagBoolean = true;
                            isAnnotationFlogBoolean = true;
                        }else if(jdbcNamingDataType.isFront() && name.toUpperCase().startsWith("FG_")){
                            sb.append("@FlagBoolean\n");
                            isFlagBoolean = true;
                            isAnnotationFlogBoolean = true;
                        }
                    }
                }

                sb.append("@Column(name = \"").append(name).append("\")");

                StringBuilder field = new StringBuilder();
                if(isPrivate) {
                    field.append("\nprivate ");
                }else{
                    field.append("\n");
                }


                if(isFlagBoolean){
                    field.append("Boolean ");
                }else {
                    JdbcDataType jdbcDataType =  nameType.getDataType();
                    switch (jdbcDataType) {
                        case DATE_TIME:
                        case LONG:
                            field.append("Long ");
                            break;

                        case DOUBLE:
                            field.append("Double ");
                            break;
                        case INTEGER:
                            field.append("Integer ");
                            break;
                        case BOOLEAN:
                            field.append("Boolean ");
                            break;
                        case BIG_DECIMAL:
                            field.append("BigDecimal ");
                            break;
                        default:
                            field.append("String ");
                            break;
                    }
                }
                String [] names = name.split("_");
                field.append(names[0]);
                for (int i = 1; i <names.length ; i++) {
                    if(names[i] == null || names[i].length()<1)
                        continue;
                    field.append(names[i].substring(0,1).toUpperCase());
                    if(names[i].length()> 1){
                        field.append(names[i].substring(1));
                    }
                }
                field.append(";");
                sb.append(field);
            }

        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }

        StringBuilder importBuilder = new StringBuilder();
        importBuilder.append("\nimport com.seomse.jdbc.annotation.Column;");
        importBuilder.append("\nimport com.seomse.jdbc.annotation.Table;");
        if(isAnnotationPrimaryKey){
            importBuilder.append("\nimport com.seomse.jdbc.annotation.PrimaryKey;");
        }
        if(isAnnotationDateTime){
            importBuilder.append("\nimport com.seomse.jdbc.annotation.DateTime;");
        }
        if(isAnnotationFlogBoolean){
            importBuilder.append("\nimport com.seomse.jdbc.annotation.FlagBoolean;");
        }


        return importBuilder.toString() + sb;
    }



    /**
     * 바뀐정보가 있으면 update
     * @param obj T obj
     * @param updateInfo T check obj
     * @param <T> Table, Column annotation object
     * @return boolean isUpdate
     */
    public static <T> boolean updateObj(T obj, T updateInfo) {

        Class<?> objClass = obj.getClass();

        Field[] fields = FieldUtil.getFieldArrayToParents(objClass);

        boolean isUpdate = false;

        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Column.class)) {

                    Object data = field.get(obj);

                    Object updateData = field.get(updateInfo);

                    if(data == null &&  updateData == null){
                        continue;
                    }

                    if(data == updateData){
                        continue;
                    }

                    if(
                            (data == null || updateData == null)
                            || !data.equals(updateData)
                    ){
                        field.set(obj, updateData);
                        isUpdate = true;
                    }
                }

            }
        }catch(ReflectiveOperationException e){
            throw new ReflectiveOperationRuntimeException(e);
        }

        return isUpdate;
    }

}
