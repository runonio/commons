
package io.runon.jdbc.common;

import io.runon.commons.exception.IORuntimeException;
import io.runon.commons.exception.ReflectiveOperationRuntimeException;
import io.runon.jdbc.Database;
import io.runon.jdbc.PrepareStatementData;
import io.runon.jdbc.annotation.DateTime;
import io.runon.jdbc.annotation.FlagBoolean;
import io.runon.jdbc.annotation.PrimaryKey;
import io.runon.jdbc.annotation.Sequence;
import io.runon.jdbc.exception.SQLRuntimeException;
import io.runon.jdbc.naming.JdbcDataType;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * jdbc common method util
 * @author macle
 */
public class JdbcCommon {


    /**
     * PreparedStatement ResultSet setting
     * @param conn Connection
     * @param sql String
     * @param prepareStatementDataMap Map
     * @return StmtResultSet
     * @throws SQLException SQLException
     */
    public static StmtResultSet makeStmtResultSet(Connection conn, String sql,  Map<Integer, PrepareStatementData> prepareStatementDataMap) throws SQLException {

        StmtResultSet stmtResultSet = new StmtResultSet();
        if(prepareStatementDataMap != null){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            stmtResultSet.stmt = pstmt;

            setStmt(pstmt,prepareStatementDataMap);
            stmtResultSet.resultSet = pstmt.executeQuery();
        }else{
            stmtResultSet.stmt = conn.createStatement();
            stmtResultSet.resultSet = stmtResultSet.stmt.executeQuery(sql);
        }

        return stmtResultSet;
    }

    /**
     * PreparedStatement ResultSet setting
     * @param conn Connection
     * @param sql String
     * @param prepareStatementDataMap  Map
     * @param fetchSize int
     * @return StmtResultSet
     * @throws SQLException SQLException
     */
    public static StmtResultSet makeStmtResultSet(Connection conn, String sql,  Map<Integer, PrepareStatementData> prepareStatementDataMap, int fetchSize) throws SQLException {
        StmtResultSet stmtResultSet = makeStmtResultSet(conn,sql,prepareStatementDataMap);
        if(fetchSize > 0){
            stmtResultSet.stmt.setFetchSize(fetchSize);
            stmtResultSet.resultSet.setFetchSize(fetchSize);
        }

        return stmtResultSet;
    }





    /**
     * prepareStatementDataMap 을 이용한 stmt 설정
     * @param pstmt PreparedStatement
     * @param prepareStatementDataMap Map
     * @throws SQLException SQLException
     */
    public static void setStmt(PreparedStatement pstmt, Map<Integer, PrepareStatementData> prepareStatementDataMap) throws SQLException {
        Set<Integer> indexSet = prepareStatementDataMap.keySet();
        for(Integer index : indexSet){
            PrepareStatementData prepareStatementData = prepareStatementDataMap.get(index);
            if(prepareStatementData.getType() == JdbcDataType.DATE_TIME){
                Timestamp timestamp = new Timestamp((Long)prepareStatementData.getData());
                pstmt.setTimestamp(index, timestamp);
            }else if(prepareStatementData.getType() == JdbcDataType.STRING){
                pstmt.setString(index, (String) prepareStatementData.getData());
            }else if(prepareStatementData.getType() == JdbcDataType.INTEGER){
                pstmt.setInt(index, (Integer)prepareStatementData.getData());
            }else if(prepareStatementData.getType() == JdbcDataType.DOUBLE){
                pstmt.setDouble(index, (Double)prepareStatementData.getData());
            }else if(prepareStatementData.getType() == JdbcDataType.LONG){
                pstmt.setLong(index, (Long)prepareStatementData.getData());
            }else if(prepareStatementData.getType() == JdbcDataType.BOOLEAN){
                pstmt.setBoolean(index, (Boolean) prepareStatementData.getData());
            }else if(prepareStatementData.getType() == JdbcDataType.BIG_DECIMAL){
                pstmt.setBigDecimal(index, (BigDecimal) prepareStatementData.getData());
            }
        }
    }

    /**
     *
     * @param obj  T jdbc object
     * @param fields Field []
     * @param pstmt PreparedStatement
     * @param <T> T jdbc object
     * @throws IllegalAccessException IllegalAccessException
     * @throws SQLException SQLException
     */
    public static <T> void addBatch(T obj, Field [] fields, PreparedStatement pstmt ) throws IllegalAccessException, SQLException {
        for(int i=0 ; i<fields.length ; i++){

            fields[i].setAccessible(true);
            Object object = fields[i].get(obj);

            if(object == null){
                setNullPstmt(obj, fields[i], pstmt, i);

            }else{
                setPstmt(obj,object, fields[i], pstmt, i);
            }

        }
        pstmt.addBatch();
    }

    /**
     *
     * @param obj T jdbc object
     * @param field Field
     * @param pstmt PreparedStatement
     * @param i int index
     * @param <T> T jdbc object
     * @throws SQLException SQLException
     * @throws IllegalAccessException IllegalAccessException
     */
    public static <T> void setNullPstmt(T obj, Field field, PreparedStatement pstmt, int i) throws SQLException, IllegalAccessException {
        Sequence sequence = field.getAnnotation(Sequence.class);
        if(sequence != null){
            if(field.getType() == Long.TYPE || field.getType() == Long.class){
                long next = Database.nextLong(sequence.name());
                field.set(obj, next);
                pstmt.setLong(i+1, next);
            }else{
                String nextVal = Database.nextVal(sequence.name());
                nextVal =  sequence.prefix() + nextVal;

                field.set(obj, nextVal);
                pstmt.setString(i+1, nextVal);
            }

            return;
        }

        DateTime dateTime = field.getAnnotation(DateTime.class);
        if(dateTime != null && !dateTime.isNullable()) {
            Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
            pstmt.setTimestamp(i+1, timeStamp);
            return;
        }

        pstmt.setNull(i+1,  java.sql.Types.NULL);

    }

    /**
     * PreparedStatement data set
     * @param obj T jdbc object
     * @param object Object field set data
     * @param field Field
     * @param pstmt PreparedStatement
     * @param i int index
     * @param <T> T jdbc object
     * @throws SQLException SQLException
     * @throws IllegalAccessException IllegalAccessException
     */
    public static <T> void setPstmt(T obj, Object object, Field field, PreparedStatement pstmt, int i) throws SQLException, IllegalAccessException {

        if(field.getType().isEnum()){
            pstmt.setString(i+1, field.get(obj).toString());
            return;
        }


        FlagBoolean flagBoolean = field.getAnnotation(FlagBoolean.class);

        if(flagBoolean != null && (field.getType() == Boolean.TYPE || field.getType() == Boolean.class)){
            boolean isObj = field.getBoolean(obj);
            if(isObj){
                pstmt.setString(i+1, "Y");
            }else{
                pstmt.setString(i+1, "N");
            }
            return;
        }


        DateTime dateColumn =  field.getAnnotation(DateTime.class);
        if(dateColumn == null){
            if(field.getType() == String.class){
                pstmt.setString(i+1, (String)object);
            }else if(field.getType() == Long.TYPE || field.getType() == Long.class){
                pstmt.setLong(i+1, (long)object);
            }else if(field.getType() == Integer.TYPE || field.getType() == Integer.class){
                pstmt.setInt(i+1, (int)object);
            }else if(field.getType() == Float.TYPE || field.getType() == Float.class){
                pstmt.setFloat(i+1, (float)object);
            }else if(field.getType() == Double.TYPE || field.getType() == Double.class){
                pstmt.setDouble(i+1, (double)object);
            }else if(field.getType() == Boolean.TYPE || field.getType() == Boolean.class){
                pstmt.setBoolean(i+1, (boolean)object);
            }else if(field.getType() == BigDecimal.class){
                pstmt.setBigDecimal(i+1, (BigDecimal)object);
            }else if(field.getType() == File.class){
                try{
                    File file = (File)object;

                    byte [] bytes = Files.readAllBytes(file.toPath());
                    pstmt.setBytes(i+1, bytes);
                }catch(IOException e){
                    throw new IORuntimeException(e);
                }
            }else if(field.getType().getName().equals("[B")){
                pstmt.setBytes(i+1, (byte[]) object);
            }
            else{
                pstmt.setObject(i+1, object);
            }
        }else{

            Timestamp timeStamp = new Timestamp((long)object);
            pstmt.setTimestamp(i+1, timeStamp);
        }
    }

    /**
     * insert
     * @param conn Connection
     * @param objClassList List
     * @param fields Field []
     * @param insertSql String
     * @param isClearParameters boolean
     * @param <T> Table annotation object class
     * @return int
     */
    public static <T>  int insert(Connection conn, List<T> objClassList, Field [] fields, String insertSql, boolean isClearParameters){
        PreparedStatement pstmt = null;
        int successCount ;

        try{
            pstmt = conn.prepareStatement(insertSql);

            for(T obj : objClassList){
                JdbcCommon.addBatch(obj, fields, pstmt);
                if(isClearParameters){
                    pstmt.clearParameters();
                }else{
                    pstmt.executeBatch();
                }

            }
            if(isClearParameters){
                pstmt.executeBatch();
            }
            successCount = objClassList.size();
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }catch(ReflectiveOperationException e){
            throw new ReflectiveOperationRuntimeException(e);
        } finally{
            //noinspection CatchMayIgnoreException
            try{if(pstmt != null)pstmt.close(); }catch(Exception e){}
        }
        return successCount;
    }

    /**
     * insert
     * @param conn Connection
     * @param obj T
     * @param fields Field []
     * @param insertSql String
     * @param <T> Table annotation object class
     * @return int
     */
    public static <T>  int insert(Connection conn, T obj, Field [] fields, String insertSql ){

        int successCount ;

        PreparedStatement pstmt = null;

        //noinspection TryFinallyCanBeTryWithResources
        try{
            pstmt = conn.prepareStatement(insertSql);

            JdbcCommon.addBatch(obj, fields, pstmt);
            pstmt.clearParameters();

            pstmt.executeBatch();
            successCount = 1;
        }catch(SQLException e){
            throw new SQLRuntimeException(e);
        }catch(ReflectiveOperationException e){
            throw new ReflectiveOperationRuntimeException(e);
        }finally{
            //noinspection CatchMayIgnoreException
            try{if(pstmt!=null)pstmt.close();  }catch(Exception e){}
        }

        return successCount;
    }

    /**
     * setPrimaryKeyField
     * @param pstmt PreparedStatement
     * @param fields Field []
     * @param obj T
     * @param isNullUpdate boolean null value update flag
     * @param <T> Table annotation object class
     * @return int index
     * @throws SQLException SQLException
     * @throws IllegalAccessException IllegalAccessException
     */
    public static <T> int setPrimaryKeyField( PreparedStatement pstmt, Field [] fields, T obj, boolean isNullUpdate) throws SQLException, IllegalAccessException {

        int index = 0;
        //noinspection ForLoopReplaceableByForEach
        for(int i=0 ; i<fields.length ; i++){

            PrimaryKey pk = fields[i].getAnnotation(PrimaryKey.class);
            if(pk != null){
                continue;
            }
            fields[i].setAccessible(true);
            Object object = fields[i].get(obj);
            if(!isNullUpdate){

                if(object == null){
                    continue;
                }
            }

            if(object == null){
                JdbcCommon.setNullPstmt(obj,fields[i],pstmt,index);
            }else{
                JdbcCommon.setPstmt(obj, object, fields[i], pstmt, index);
            }
            index++;

        }

        return index;
    }

    /**
     * pk sort
     */
    public final static Comparator<Field> PK_SORT_ASC = Comparator.comparingInt(f -> f.getAnnotation(PrimaryKey.class).seq());

}
