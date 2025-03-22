
package io.runon.jdbc.naming;

import io.runon.commons.exception.ReflectiveOperationRuntimeException;
import io.runon.commons.utils.ExceptionUtil;
import io.runon.commons.utils.packages.classes.field.FieldUtil;
import io.runon.jdbc.Database;
import io.runon.jdbc.PrepareStatementData;
import io.runon.jdbc.annotation.PrimaryKey;
import io.runon.jdbc.annotation.Table;
import io.runon.jdbc.common.*;
import io.runon.jdbc.connection.ApplicationConnectionPool;
import io.runon.jdbc.exception.FieldNullException;
import io.runon.jdbc.exception.PrimaryKeyNotSetException;
import io.runon.jdbc.exception.SQLRuntimeException;
import io.runon.jdbc.exception.TableNameEmptyException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * naming domain header 를 이용한 class 사용
 * DB용 객체와 사용객체를 나눠서 슬때 유용
 * 생성 되는 객체의 변수는 컬럼명으로 생성 됨
 * @author macle
 */
@Slf4j
public class JdbcNaming {

	/**
	 * List 얻기
	 * @param conn Connection
	 * @param objClass Class
	 * @param <T> Table annotation object
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
	 * @param <T> Table annotation object class
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
	 * @param <T> Table annotation object class
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
	 * @param size int lust cut size
	 * @param <T> Table annotation object class
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
	 * @param <T> Table annotation object class
	 * @return List
	 */
	public static <T> List<T> getObjList(Class<T> objClass ){
		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			return getObjList(conn, objClass, null, null, null, -1, null);
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
	 * @param <T> Table annotation object class
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
	 * @param size int lust cut size
	 * @param <T> Table annotation object class
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
	 * @param orderByValue  String order by query
	 * @param <T> Table annotation object class
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
	 * @param objClass  Class
	 * @param whereValue String where query
	 * @param orderByValue  String order by query
	 * @param size int lust cut size
	 * @param <T> Table annotation object class
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
	 * @param <T> Table annotation object class
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
	 * @param sql String direct query
	 * @param whereValue String where query
	 * @param prepareStatementDataMap Map 조건 데이터  date time 같이 database query 가 다른 경우
	 * @param <T> Table annotation object class
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
	 * @param objClass Class
	 * @param sql String direct query
	 * @param <T> Table annotation object class
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
	 * @param objClass Class
	 * @param sql String direct query
	 * @param whereValue String where query
	 * @param prepareStatementDataMap Map 조건 데이터  date time 같이 database query 가 다른 경우
	 * @param <T> Table annotation object class
	 * @return List
	 * @throws IllegalAccessException IllegalAccessException
	 * @throws SQLException SQLException
	 * @throws InstantiationException InstantiationException
	 */
	public static <T> List<T> getObjList(Connection conn, Class<T> objClass , String sql, String whereValue, Map<Integer, PrepareStatementData> prepareStatementDataMap) throws IllegalAccessException, SQLException, InstantiationException {
		return getObjList(conn, objClass, sql, whereValue , null, -1 , prepareStatementDataMap);
	}
	

	/**
	 * List 얻기
	 * @param conn Connection
	 * @param objClass Class
	 * @param sql String direct query
	 * @param whereValue String where query
	 * @param orderByValue  String order by query
	 * @param size int lust cut size
	 * @param prepareStatementDataMap Map 조건 데이터  date time 같이 database query 가 다른 경우
	 * @param <T> Table annotation object class
	 * @return List
	 * @throws IllegalAccessException IllegalAccessException
	 * @throws SQLException SQLException
	 * @throws InstantiationException InstantiationException
	 */
	public static <T> List<T> getObjList(Connection conn, Class<T> objClass , String sql, String whereValue, String orderByValue, int size, Map<Integer, PrepareStatementData> prepareStatementDataMap) throws IllegalAccessException, SQLException, InstantiationException {
		
		List<T> resultList = new ArrayList<>();

		Table table = objClass.getAnnotation(Table.class);

		Field [] fields = getFields(objClass);
		String selectSql;
		if(sql == null) {
			selectSql = getSql(objClass, table, fields, whereValue, orderByValue);
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

					for(Field field : fields){
						JdbcField.setFieldObject(result, field, field.getName(), resultObj);
					}
					
					resultList.add(resultObj);
				}	
			}else{
				int checkCount = 0;
				while(result.next()){

					T resultObj = objClass.newInstance();

					for(Field field : fields){
						JdbcField.setFieldObject(result, field, field.getName(), resultObj);
					}

					resultList.add(resultObj);
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
		
		return resultList;
		
	}

	/**
	 * sql 얻기
	 * @param objClass Class
	 * @param table Table annotation
	 * @param fields Field []
	 * @param whereValue String where query
	 * @param orderByValue String order by query
	 * @param <T> Table annotation object class
	 * @return String sql
	 */
	public static <T> String getSql(Class<T> objClass, Table table , Field [] fields, String whereValue, String orderByValue){
		StringBuilder sqlBuilder = new StringBuilder();

		String tableSql = table.sql();
		if(tableSql.equals(Table.EMPTY)){

			if(table.name().equals(Table.EMPTY)){
				throw new TableNameEmptyException(objClass.getName());
			}

			if(fields == null || fields.length ==0){
				throw new FieldNullException(objClass.getName());
			}
			StringBuilder fieldBuilder = new StringBuilder();

			for(Field field : fields){

				fieldBuilder.append(", ").append(field.getName());


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
	 * Table annotation object get
	 * @param conn Connection
	 * @param objClass Class
	 * @param <T> Table annotation object class
	 * @return T Table annotation object class
	 * @throws IllegalAccessException IllegalAccessException
	 * @throws SQLException SQLException
	 * @throws InstantiationException InstantiationException
	 */
	public static <T> T getObj(Connection conn, Class<T> objClass ) throws IllegalAccessException, SQLException, InstantiationException {
		return getObj(conn,  objClass, null, null, null, null);
	}


	/**
	 * Table annotation object get
	 * @param conn Connection
	 * @param objClass Class
	 * @param whereValue String where query
	 * @param <T> Table annotation object class
	 * @return T Table annotation object class
	 * @throws IllegalAccessException IllegalAccessException
	 * @throws SQLException SQLException
	 * @throws InstantiationException InstantiationException
	 */
	public static <T> T getObj(Connection conn, Class<T> objClass , String whereValue) throws IllegalAccessException, SQLException, InstantiationException {
		return getObj(conn,  objClass, null, whereValue, null, null);
	}

	/**
	 * Table annotation object get
	 * @param objClass Class
	 * @param <T> Table annotation object class
	 * @return T Table annotation object class
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
	 * Table annotation object get
	 * @param objClass Class
	 * @param whereValue String where query
	 * @param <T> Table annotation object class
	 * @return T Table annotation object class
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
	 * Table annotation object get
	 * @param objClass Class
	 * @param whereValue String where query
	 * @param orderByValue String order by query
	 * @param <T> Table annotation object class
	 * @return T Table annotation object class
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
	 * Table annotation object get
	 * @param objClass Class
	 * @param whereValue String where query
	 * @param prepareStatementDataMap Map 조건 데이터  date time 같이 database query 가 다른 경우
	 * @param <T> Table annotation object class
	 * @return T Table annotation object class
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
	 * Table annotation object get
	 * @param objClass Class
	 * @param sql String direct query
	 * @param whereValue String where query
	 * @param prepareStatementDataMap Map 조건 데이터  date time 같이 database query 가 다른 경우
	 * @param <T> Table annotation object class
	 * @return T Table annotation object class
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
	 * Table annotation object get
	 * @param objClass Class
	 * @param sql String direct query
	 * @param <T> Table annotation object class
	 * @return T Table annotation object class
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
	 * Table annotation object get
	 * @param conn Connection
	 * @param objClass Class
	 * @param sql String direct query
	 * @param whereValue String where query
	 * @param prepareStatementDataMap Map 조건 데이터  date time 같이 database query 가 다른 경우
	 * @param <T> Table annotation object class
	 * @return T Table annotation object class
	 * @throws IllegalAccessException IllegalAccessException
	 * @throws SQLException SQLException
	 * @throws InstantiationException InstantiationException
	 */
	public static <T> T getObj(Connection conn, Class<T> objClass, String sql, String whereValue, Map<Integer, PrepareStatementData> prepareStatementDataMap) throws IllegalAccessException, SQLException, InstantiationException {
		return getObj(conn, objClass, sql, whereValue, null, prepareStatementDataMap);
	}

	/**
	 * Table annotation object get
	 * @param conn Connection
	 * @param objClass Class
	 * @param sql String direct query
	 * @param whereValue String where query
	 * @param orderByValue String order by query
	 * @param prepareStatementDataMap Map 조건 데이터  date time 같이 database query 가 다른 경우
	 * @param <T> Table annotation object class
	 * @return T Table annotation object class
	 * @throws IllegalAccessException IllegalAccessException
	 * @throws SQLException SQLException
	 * @throws InstantiationException InstantiationException
	 */
	public static <T> T getObj(Connection conn, Class<T> objClass, String sql, String whereValue, String orderByValue, Map<Integer, PrepareStatementData> prepareStatementDataMap) throws IllegalAccessException, SQLException, InstantiationException {

		Field [] fields = FieldUtil.getFieldArrayToParents(objClass);

		String makeSql;
		if(sql == null) {
			makeSql = getSql(objClass,  objClass.getAnnotation(Table.class), fields, whereValue, orderByValue);
		}else{
			makeSql = sql;
		}

		Statement stmt = null;
		ResultSet result = null;
		
		T resultObj = null;

		//noinspection CaughtExceptionImmediatelyRethrown
		try{
			StmtResultSet stmtResultSet = JdbcCommon.makeStmtResultSet(conn, makeSql, prepareStatementDataMap, 2);
			stmt = stmtResultSet.getStmt();
			result = stmtResultSet.getResultSet();
			if(result.next()){
				resultObj = objClass.newInstance();

				for(Field field : fields){
					JdbcField.setFieldObject(result, field, field.getName(), resultObj);
				}
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
	 * @param <T> Table annotation object class
	 * @return int fail -1
	 */
	public static <T> int upsert( List<T> objClassList){
		ApplicationConnectionPool connectionPool = ApplicationConnectionPool.getInstance();
		try(Connection conn = connectionPool.getConnection()){
			int result = insert(conn, objClassList, "UPSERT", true);
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
	 * @param objClassList List
	 * @param isClearParameters boolean
	 * @param <T> Table annotation object class
	 * @return int fail -1
	 */
	public static <T> int upsert( List<T> objClassList,   boolean isClearParameters){
		ApplicationConnectionPool connectionPool = ApplicationConnectionPool.getInstance();
		try(Connection conn = connectionPool.getConnection()){
			int result =  insert(conn, objClassList, "UPSERT", isClearParameters);
			if(!connectionPool.isAutoCommit()) conn.commit();
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
	 * @param <T> Table annotation object class
	 * @return int fail -1
	 */
	public static <T> int upsert(Connection conn, List<T> objClassList,   boolean isClearParameters){
		return insert(conn, objClassList , "UPSERT", isClearParameters);
	}
	
	/**

	/**
	 * insert
	 * @param objClassList List
	 * @param <T> Table annotation object class
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
	 * @param <T> Table annotation object class
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
	 * @param <T> Table annotation object class
	 * @return int fail -1
	 */
	public static <T> int insert(Connection conn, List<T> objClassList,   boolean isClearParameters){
		return insert(conn, objClassList , "INSERT", isClearParameters);
	}

	/**
	 * insert
	 * @param conn Connection
	 * @param objClassList List
	 * @param insertQueryValue string insert query
	 * @param isClearParameters boolean
	 * @param <T> Table annotation object class
	 * @return int fail -1
	 */
	public static <T> int insert(Connection conn, List<T> objClassList, String insertQueryValue,  boolean isClearParameters){
		if(objClassList == null || objClassList.size() ==0){
			return 0;
		}
		Class<?> objClass = objClassList.get(0).getClass();
		Field [] fields = getFields(objClass);
		String insertSql = getInsertSql(objClass, fields, insertQueryValue);
		return JdbcCommon.insert(conn, objClassList, fields, insertSql, isClearParameters);
	}

	/**
	 * insert query get
	 * @param objClass Class
	 * @param fields Field []
	 * @param insertQueryValue string INSERT, UPSERT
	 * @return String insert query
	 */
	public static String getInsertSql(Class<?> objClass, Field [] fields , String insertQueryValue){

		Table table = objClass.getAnnotation(Table.class);

		String tableName = TableSql.getTableName(table, objClass.getName());

		StringBuilder sqlBuilder = new StringBuilder();

		sqlBuilder.append(insertQueryValue).append(" INTO ").append(tableName).append(" (");

		StringBuilder fieldBuilder = new StringBuilder();

		//noinspection ForLoopReplaceableByForEach
		for(int i=0 ; i<fields.length ; i++){
			fieldBuilder.append(", ").append(fields[i].getName());
		}
		sqlBuilder.append(fieldBuilder.substring(1));
		sqlBuilder.append(") VALUES (");


		fieldBuilder.setLength(0);
		for(int i=0 ; i<fields.length ; i++){
			fieldBuilder.append(", ?");
		}
		sqlBuilder.append(fieldBuilder.substring(1));
		sqlBuilder.append(" )");

		return sqlBuilder.toString();
	}


	public static <T> int insertOrUpdate(T obj){
		return insertOrUpdate(obj ,false);
	}
	/**
	 * insert or update
	 * @param obj T
	 * @param isNullUpdate boolean null value update flag
	 * @param <T> Table annotation object class
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
	 * insert or update
	 * @param conn Connection
	 * @param obj T
	 * @param isNullUpdate boolean null value update flag
	 * @param <T> Table annotation object class
	 * @return int fail -1
	 */
	public static <T> int insertOrUpdate(Connection conn, Object obj , boolean isNullUpdate ){


		int successCount ;
		try{

			Object checkVo = getObj(conn, obj.getClass(), null, getCheckWhere(obj), null, null);
			if(checkVo == null){
				successCount =insert(conn, obj);
			}else{
				successCount = update(conn, obj, isNullUpdate); 
			}
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}catch(ReflectiveOperationException e){
			throw new ReflectiveOperationRuntimeException(e);
		}
				
		return successCount;
		
	}

	/**
	 * upsert
	 * @param conn Connection
	 * @param obj T
	 * @param <T> Table annotation object class
	 * @return int fail -1
	 */
	public static <T> int upsert(Connection conn, T obj){
		return insert(conn, obj, "UPSERT");
	}

	/**
	 * upsert
	 * @param obj T
	 * @param <T> Table annotation object class
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
	 * inset
	 * @param obj T
	 * @param <T> Table annotation object class
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
	 * inset
	 * @param conn Connection
	 * @param obj T
	 * @param <T> Table annotation object class
	 * @return int fail -1
	 */
	public static <T> int insert(Connection conn, T obj){
		return insert(conn, obj, "INSERT");
	}

	/**
	 * insert
	 * @param conn Connection
	 * @param obj T
	 * @param insertQueryValue String INSERT, UPSERT
	 * @param <T> Table annotation object class
	 * @return int fail -1
	 */
	public static <T> int insert(Connection conn, T obj, String insertQueryValue){
		
		Class<?> objClass = obj.getClass();
		Field [] fields =getFields(objClass);

		String insertSql = getInsertSql(objClass, fields, insertQueryValue);

		return JdbcCommon.insert(conn, obj, fields, insertSql);
	}


	public static <T> int update(T obj ) {
		return update(obj, false);
	}

	/**
	 * update
	 * @param obj T
	 * @param isNullUpdate  boolean null value update flag
	 * @param <T> Table annotation object class
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
	 * @param <T> Table annotation object class
	 * @return int fail -1
	 */
	public static <T> int update(Connection conn,T obj , boolean isNullUpdate ){
			
		Class<?> objClass = obj.getClass();
		String tableName = TableSql.getTableName(objClass.getAnnotation(Table.class), objClass.getName());
		
		Field [] fields = getFields(objClass);

		StringBuilder sqlBuilder = new StringBuilder();
	
		sqlBuilder.append("UPDATE ").append(tableName).append(" SET ");
		
		StringBuilder fieldBuilder = new StringBuilder();
		List<Field> pkColumnList = new LinkedList<>();

		//noinspection ForLoopReplaceableByForEach
		for(int i=0 ; i<fields.length ; i++){
			fields[i].setAccessible(true);
			PrimaryKey pk = fields[i].getAnnotation(PrimaryKey.class);
			if(pk != null){
				pkColumnList.add(fields[i]);
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
	
			fieldBuilder.append(", ").append(fields[i].getName()).append("=?");
		}
		
		if(pkColumnList.size() ==0){
			throw new PrimaryKeyNotSetException(objClass.getName());
		}

		sqlBuilder.append(fieldBuilder.substring(1));
		sqlBuilder.append(" WHERE ");
		
		fieldBuilder.setLength(0);

		pkColumnList.sort(JdbcCommon.PK_SORT_ASC);
		//noinspection ForLoopReplaceableByForEach
		for(int i= 0 ; i < pkColumnList.size() ; i++){
			Field field = pkColumnList.get(i);
			fieldBuilder.append(" AND ").append(field.getName()).append("=?");
		}
		sqlBuilder.append(fieldBuilder.substring(4));
		PreparedStatement pstmt = null;


		int successCount;
		try{
			pstmt = conn.prepareStatement(sqlBuilder.toString());
			int index = JdbcCommon.setPrimaryKeyField(pstmt,fields,obj,isNullUpdate);

			//순서 정보가 명확해야 한다는 느낌을 주기위한 반복문 fori 사용
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
	 * object 생성 도움 매소드
	 * @param conn Connection
	 * @param tableName String table name
	 * @return String object 생성할 때 복사 붙여 넣기 값
	 */
	public static String makeObjectValue(Connection conn, String tableName){
		Statement stmt = null;
		ResultSet result = null;


		boolean isAnnotationPrimaryKey = false;
		boolean isAnnotationDateTime = false;

		StringBuilder sb = new StringBuilder();
		sb.append("\n\n@Table(name=\"").append(tableName).append("\")\n\n");
		JdbcNamingDataType jdbcNamingDataType = JdbcNamingDataType.getInstance();

		try{
			Map<String, Integer> pkMap = Database.getPrimaryKeyColumnsForTable(conn, tableName);
			Map<String,String> defaultValueMap = Database.getDefaultValue(tableName);
			String [] columnNames = Database.getColumnNames(conn, tableName);
			JdbcNameDataType [] nameTypes = Database.getColumns(conn, tableName);

			for (JdbcNameDataType nameType : nameTypes){
				String columnName = nameType.getName();

				Integer pkSeq = pkMap.get(columnName);
				if(pkSeq != null){
					sb.append("@PrimaryKey(seq = ").append(pkSeq).append(")\n");
					isAnnotationPrimaryKey = true;
				}

				JdbcDataType dataType = nameType.getDataType();

				if(dataType == JdbcDataType.STRING){

					sb.append("private String ").append(columnName);

					String defaultValue = defaultValueMap.get(columnName);
					if(defaultValue != null){
						sb.append(" = ").append(defaultValue.replace("'", "\""));
					}
					sb.append(";\n");


				}else if(dataType == JdbcDataType.DATE_TIME ){
					isAnnotationDateTime =true;
					sb.append("@DateTime\nprivate Long ").append(columnName);


					String defaultValue = defaultValueMap.get(columnName);
					if(defaultValue != null){

						defaultValue = defaultValue.replace("'", "").toUpperCase().trim();

						if(defaultValue.equals("SYSDATE") || defaultValue.equals("NOW()") || defaultValue.equals("CURRENT_TIMESTAMP()")){
							sb.append( " = System.currentTimeMillis()" );
						}
					}
					sb.append(";\n");

				}else if(dataType == JdbcDataType.LONG ){
					sb.append("private Long ").append(columnName);

					String defaultValue = defaultValueMap.get(columnName);
					if(defaultValue != null){
						defaultValue = defaultValue.replace("'", "").trim();
						sb.append(" = ").append(defaultValue).append("L");

					}
					sb.append(";\n");

				}else if(dataType == JdbcDataType.DOUBLE ){
					sb.append("private Double ").append(columnName);

					String defaultValue = defaultValueMap.get(columnName);
					if(defaultValue != null){
						defaultValue = defaultValue.replace("'", "").trim();
						sb.append(" = ").append(defaultValue);
						if(!defaultValue.contains(".")){
							sb.append(".0");
						}


					}
					sb.append(";\n");

				}else if(dataType == JdbcDataType.INTEGER
						){
					sb.append("private Integer ").append(columnName);

					String defaultValue = defaultValueMap.get(columnName);
					if(defaultValue != null){
						defaultValue = defaultValue.replace("'", "").trim();
						sb.append(" = ").append(defaultValue);

					}
					sb.append(";\n");

				}else if(dataType == JdbcDataType.BOOLEAN){
					sb.append("private Boolean ").append(columnName);

					String defaultValue = defaultValueMap.get(columnName);
					if(defaultValue != null){
						defaultValue = defaultValue.replace("'", "").trim();
						sb.append(" = ").append(defaultValue);

					}
					sb.append(";\n");

				}else if(dataType == JdbcDataType.BIG_DECIMAL){
					sb.append("private BigDecimal ").append(columnName);

					String defaultValue = defaultValueMap.get(columnName);
					if(defaultValue != null){
						defaultValue = defaultValue.replace("'", "").trim();
						sb.append(" = ").append(defaultValue);

					}
					sb.append(";\n");

				}

			}

		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			JdbcClose.statementResultSet(stmt, result);
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

		return importBuilder.toString() + sb;
	}


	/**
	 * object 생성 도움 매소드
	 * @param tableName String table name
	 * @return String object 생성할 때 복사 붙여 넣기 값
	 */
	public static String makeObjectValue( String tableName){
		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			return makeObjectValue(conn, tableName);
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}
	}

	/**
	 * data 가 없는 경우 insert
	 * @param obj T
	 * @param <T> Table annotation object class
	 * @return int success insert count, fail -1
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
	 * data 가 없는 경우 insert
	 * @param conn Connection
	 * @param obj T
	 * @param <T> Table annotation object class
	 * @return  int success insert count, fail -1
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
	 * where query 얻기
	 * @param obj T
	 * @param <T> Table annotation object class
	 * @return String where query
	 * @throws IllegalAccessException IllegalAccessException
	 */
	public static <T> String getCheckWhere(T obj) throws IllegalAccessException {
		Class<?> objClass = obj.getClass();
		String tableName = TableSql.getTableName(objClass.getAnnotation(Table.class), objClass.getName());

		Field [] fields = getFields(objClass);

		List<Field> pkColumnList = new LinkedList<>();

		//noinspection ForLoopReplaceableByForEach
		for(int i=0 ; i<fields.length ; i++){
			fields[i].setAccessible(true);
			PrimaryKey pk  = fields[i].getAnnotation(PrimaryKey.class);
			if(pk != null){
				pkColumnList.add(fields[i]);
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
			whereBuilder.append(" AND ").append(field.getName()).append("='").append(field.get(obj)).append("'");
		}

		return whereBuilder.substring(4);
	}


	/**
	 * Field []  얻기
	 * @param objClass objClass
	 * @return Field []
	 */
	public static Field [] getFields(Class<?> objClass){
		Field [] fields = FieldUtil.getFieldArrayToParents(objClass);
		if(fields == null || fields.length ==0){
			throw new FieldNullException(objClass.getName());
		}
		return fields;
	}
}