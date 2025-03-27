
package io.runon.jdbc;

import io.runon.commons.utils.ExceptionUtil;
import io.runon.jdbc.common.JdbcClose;
import io.runon.jdbc.common.JdbcCommon;
import io.runon.jdbc.common.StmtResultSet;
import io.runon.jdbc.connection.ApplicationConnectionPool;
import io.runon.jdbc.exception.SQLRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * JdbcQuery sql 유틸성 메소드
 * @author macle
 */
@Slf4j
public class JdbcQuery {

	/**
	 * sql을 활용하여 time 얻기
	 * unix time
	 * @param sql String sql
	 * @param defaultValue Long default
	 * @return Long unix time
	 */
	public static Long getResultDateTime(String sql, Long defaultValue) {


		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			Long result = getResultDateTime(conn, sql);
			if(result == null){
				return defaultValue;
			}

			return result;
		}catch(Exception e){
			log.error(ExceptionUtil.getStackTrace(e));
			return defaultValue;
		}
	}


	/**
	 * sql을 활용하여 time 얻기
	 * unix time
	 * @param sql String sql
	 * @return Long unix time
	 */
	public static Long getResultDateTime(String sql) {

		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			return getResultDateTime(conn, sql);
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}

	}


	/**
	 * sql을 활용하여 time 얻기
	 * unix time
	 * @param conn Connection
	 * @param sql sql String sql
	 * @return Long unix time
	 * @throws SQLException SQLException
	 */
	public static Long getResultDateTime(Connection conn, String sql) throws SQLException {
		Long resultTime = null;
		
		Statement stmt = null;
		ResultSet result = null;
		//noinspection CaughtExceptionImmediatelyRethrown
		try{
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			ResultSetMetaData metaData = result.getMetaData();
			int count = metaData.getColumnCount(); //number of column
			if(count > 0 ){
				String columnName = metaData.getColumnLabel(1); 		
				if(result.next()){
					Timestamp timeStamp = result.getTimestamp(columnName);
					if(timeStamp == null){
						return null;
					}

					resultTime = timeStamp.getTime();			
				}
			}
		}catch(SQLException e){
			throw e;
		}finally{
			JdbcClose.statementResultSet(stmt, result);
		}
		
		return resultTime;
	}

	public static Long getResultDateTime(String sql, Map<Integer, PrepareStatementData> prepareStatementDataMap, Long defaultValue) {


		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			Long result = getResultDateTime(conn, sql, prepareStatementDataMap);
			if(result == null){
				return defaultValue;
			}

			return result;
		}catch(Exception e){
			log.error(ExceptionUtil.getStackTrace(e));
			return defaultValue;
		}
	}

	/**
	 * sql을 활용하여 time 얻기
	 * unix time
	 * @param sql String sql
	 * @return Long unix time
	 */
	public static Long getResultDateTime(String sql, Map<Integer, PrepareStatementData> prepareStatementDataMap) {

		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			return getResultDateTime(conn, sql, prepareStatementDataMap);
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}

	}

	public static Long getResultDateTime(Connection conn, String sql, Map<Integer, PrepareStatementData> prepareStatementDataMap) throws SQLException {
		Long resultTime = null;

		Statement stmt = null;
		ResultSet result = null;
		//noinspection CaughtExceptionImmediatelyRethrown
		try{
			StmtResultSet stmtResultSet = JdbcCommon.makeStmtResultSet(conn, sql, prepareStatementDataMap, 0);
			stmt = stmtResultSet.getStmt();
			result = stmtResultSet.getResultSet();
			ResultSetMetaData metaData = result.getMetaData();
			int count = metaData.getColumnCount(); //number of column
			if(count > 0 ){
				String columnName = metaData.getColumnLabel(1);
				if(result.next()){
					Timestamp timeStamp = result.getTimestamp(columnName);
					if(timeStamp == null){
						return null;
					}

					resultTime = timeStamp.getTime();
				}
			}
		}catch(SQLException e){
			throw e;
		}finally{
			JdbcClose.statementResultSet(stmt, result);
		}

		return resultTime;
	}


	/**
	 * 단일 결과를 integer 로 얻기
	 * @param sql String sql
	 * @param defaultValue Integer default
	 * @return Integer
	 */
	public static Integer getResultInteger(String sql, Integer defaultValue) {

		Integer result = getResultInteger( sql);
		if(result == null){
			return defaultValue;
		}
		
		return 	result;
	}
	public static Integer getResultInteger(String sql, Map<Integer, PrepareStatementData> prepareStatementDataMap, Integer defaultValue) {

		Integer result = getResultInteger(sql, prepareStatementDataMap);
		if(result == null){
			return defaultValue;
		}

		return 	result;
	}

	/**
	 * 단일 결과를 integer 로 얻기
	 * @param sql String sql
	 * @return Integer
	 */
	public static Integer getResultInteger(String sql) {

		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			String result = getResultOne(conn, sql);
			if (result == null) {
				return null;
			}

			return Integer.parseInt(result);
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}
	}

	/**
	 * 단일 결과를 integer 로 얻기
	 * @param conn Connection
	 * @param sql String sql
	 * @return Integer
	 * @throws SQLException SQLException
	 */
	public static Integer getResultInteger(Connection conn, String sql) throws SQLException {
		String result = getResultOne(conn, sql);
		if(result == null){
			return null;
		}
		return 	Integer.parseInt(result);
	 }

	public static Integer getResultInteger(String sql, Map<Integer, PrepareStatementData> prepareStatementDataMap) {

		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			String result = getResultOne(conn, sql, prepareStatementDataMap) ;
			if (result == null) {
				return null;
			}

			return Integer.parseInt(result);
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}
	}

	public static Integer getResultInteger(Connection conn, Map<Integer, PrepareStatementData> prepareStatementDataMap, String sql) throws SQLException {
		String result = getResultOne(conn, sql, prepareStatementDataMap);
		if(result == null){
			return null;
		}
		return 	Integer.parseInt(result);
	}



	/**
	 * 단일 결과를 double 형태로 얻기
	 * @param sql String sql
	 * @param defaultValue Double default
	 * @return Double
	 */
	public static Double getResultDouble(String sql, Double defaultValue) {
		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			Double result = getResultDouble(conn, sql);
			if (result == null) {
				return defaultValue;
			}

			return result;
		}catch(SQLException e){
			log.error(ExceptionUtil.getStackTrace(e));
			return defaultValue;
		}
	}

	public static Double getResultDouble(String sql, Double defaultValue, Map<Integer, PrepareStatementData> prepareStatementDataMap) {
		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			Double result = getResultDouble(conn, sql, prepareStatementDataMap);
			if (result == null) {
				return defaultValue;
			}

			return result;
		}catch(SQLException e){
			log.error(ExceptionUtil.getStackTrace(e));
			return defaultValue;
		}
	}



	/**
	 * 단일 결과를 double 형태로 얻기
	 * @param sql String sql
	 * @return Double
	 */
	public static Double getResultDouble(String sql) {
		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			return getResultDouble(conn, sql);
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}
	}

	/**
	 * 단일 결과를 double 형태로 얻기
	 * @param conn Connection
	 * @param sql String sql
	 * @return Double
	 * @throws SQLException SQLException
	 */
	public static Double getResultDouble(Connection conn, String sql) throws SQLException {
		Double resultDouble = null;
		
		Statement stmt = null;
		ResultSet result = null;
		//noinspection CaughtExceptionImmediatelyRethrown
		try{
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			ResultSetMetaData metaData = result.getMetaData();
			int count = metaData.getColumnCount(); //number of column
			if(count > 0 ){
				String columnName = metaData.getColumnLabel(1); 		
				if(result.next()){
				
					resultDouble = result.getDouble(columnName);			
				}
			}

		}catch(Exception e){
			throw e;
		}finally{
			JdbcClose.statementResultSet(stmt, result);
		}
		
		return resultDouble;
	}

	public static Double getResultDouble(String sql, Map<Integer, PrepareStatementData> prepareStatementDataMap) {
		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			return getResultDouble(conn, sql, prepareStatementDataMap);
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}
	}


	public static Double getResultDouble(Connection conn, String sql, Map<Integer, PrepareStatementData> prepareStatementDataMap) throws SQLException {
		Double resultDouble = null;

		Statement stmt = null;
		ResultSet result = null;
		//noinspection CaughtExceptionImmediatelyRethrown
		try{
			StmtResultSet stmtResultSet = JdbcCommon.makeStmtResultSet(conn, sql, prepareStatementDataMap, 0);
			stmt = stmtResultSet.getStmt();
			result = stmtResultSet.getResultSet();
			ResultSetMetaData metaData = result.getMetaData();
			int count = metaData.getColumnCount(); //number of column
			if(count > 0 ){
				String columnName = metaData.getColumnLabel(1);
				if(result.next()){

					resultDouble = result.getDouble(columnName);
				}
			}

		}catch(Exception e){
			throw e;
		}finally{
			JdbcClose.statementResultSet(stmt, result);
		}

		return resultDouble;
	}


	/**
	 * 단일결과를 Long형태로 얻기
	 * @param sql  String sql
	 * @param defaultValue Long default
	 * @return Long
	 */
	public static Long getResultLong(String sql, Long defaultValue) {
		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			Long result = getResultLong(conn, sql);
			if (result == null) {
				return defaultValue;
			}

			return result;
		}catch(SQLException e){
			log.error(ExceptionUtil.getStackTrace(e));
			return defaultValue;
		}
	}


	public static Long getResultLong(String sql, Map<Integer, PrepareStatementData> prepareStatementDataMap, Long defaultValue) {
		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			Long result = getResultLong(conn, sql, prepareStatementDataMap);
			if (result == null) {
				return defaultValue;
			}

			return result;
		}catch(SQLException e){
			log.error(ExceptionUtil.getStackTrace(e));
			return defaultValue;
		}
	}


	/**
	 * 단일결과를 Long 형태로 얻기
	 * @param sql String sql
	 * @return Long
	 */
	public static Long getResultLong(String sql) {
		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			return getResultLong(conn, sql);
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}
	}

	/**
	 * 단일결과를 Long 형태로 얻기
	 * @param conn Connection
	 * @param sql String sql
	 * @return Long
	 * @throws SQLException SQLException
	 */
	public static Long getResultLong(Connection conn, String sql) throws SQLException {
		Long resultLong = null;
		
		Statement stmt = null;
		ResultSet result = null;
		//noinspection CaughtExceptionImmediatelyRethrown
		try{
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			ResultSetMetaData metaData = result.getMetaData();
			int count = metaData.getColumnCount(); //number of column
			if(count > 0 ){
				String columnName = metaData.getColumnLabel(1); 		
				if(result.next()){
				
					resultLong = result.getLong(columnName);			
				}
			}
		}catch(SQLException e){
			throw e;
		}finally{
			JdbcClose.statementResultSet(stmt, result);
		}
		
		return resultLong;
	}

	public static Long getResultLong(String sql, Map<Integer, PrepareStatementData> prepareStatementDataMap) {
		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			return getResultLong(conn, sql, prepareStatementDataMap);
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}
	}

	public static Long getResultLong(Connection conn, String sql, Map<Integer, PrepareStatementData> prepareStatementDataMap) throws SQLException {
		Long resultLong = null;

		Statement stmt = null;
		ResultSet result = null;
		//noinspection CaughtExceptionImmediatelyRethrown
		try{
			StmtResultSet stmtResultSet = JdbcCommon.makeStmtResultSet(conn, sql, prepareStatementDataMap, 0);
			stmt = stmtResultSet.getStmt();
			result = stmtResultSet.getResultSet();
			ResultSetMetaData metaData = result.getMetaData();
			int count = metaData.getColumnCount(); //number of column
			if(count > 0 ){
				String columnName = metaData.getColumnLabel(1);
				if(result.next()){

					resultLong = result.getLong(columnName);
				}
			}
		}catch(SQLException e){
			throw e;
		}finally{
			JdbcClose.statementResultSet(stmt, result);
		}

		return resultLong;
	}

	/**
	 * 단일 결과 얻기 String
	 * @param sql String sql
	 * @param defaultValue String default
	 * @return String
	 */
	public static String getResultOne(String sql, String defaultValue){
		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			String result = getResultOne(conn, sql);
			if (result == null) {
				return defaultValue;
			}

			return result;
		}catch (SQLException e){
			log.error(ExceptionUtil.getStackTrace(e));
			return defaultValue;
		}
	}

	/**
	 * 단일 결과 얻기 String
	 * @param sql String sql
	 * @param defaultValue String default
	 * @return String
	 */
	public static String getResultOne(String sql, Map<Integer, PrepareStatementData> prepareStatementDataMap, String defaultValue){
		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			String result = getResultOne(conn, sql, prepareStatementDataMap);
			if (result == null) {
				return defaultValue;
			}

			return result;
		}catch (SQLException e){
			log.error(ExceptionUtil.getStackTrace(e));
			return defaultValue;
		}
	}



	/**
	 * 단일 결과 얻기 String
	 * @param sql String sql
	 * @return String
	 */
	public static String getResultOne(String sql) {
		try (Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()) {
			return getResultOne(conn, sql);
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		}
	}


	public static boolean isRowData(String sql) {
		try (Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()) {
			return isRowData(conn, sql);
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		}
	}


	public static boolean isRowData(Connection conn, String sql) throws SQLException {
		String resultValue = null;

		Statement stmt = null;
		ResultSet result = null;
		//noinspection CaughtExceptionImmediatelyRethrown
		try{
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			ResultSetMetaData metaData = result.getMetaData();
			int count = metaData.getColumnCount(); //number of column
			if(count > 0 ){
				if(result.next()){
					return true;
				}
			}
		}catch(SQLException e){
			throw e;
		}finally{
			JdbcClose.statementResultSet(stmt, result);
		}

		return false;
	}


	public static boolean isRowData(String sql, Map<Integer, PrepareStatementData> prepareStatementDataMap){
		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			return isRowData(conn, sql, prepareStatementDataMap);
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}
	}

	public static boolean isRowData(Connection conn, String sql, Map<Integer, PrepareStatementData> prepareStatementDataMap) throws SQLException {
		String resultValue = null;

		Statement stmt = null;
		ResultSet result = null;
		//noinspection CaughtExceptionImmediatelyRethrown
		try{
			StmtResultSet stmtResultSet = JdbcCommon.makeStmtResultSet(conn, sql, prepareStatementDataMap, 0);
			stmt = stmtResultSet.getStmt();
			result = stmtResultSet.getResultSet();
			ResultSetMetaData metaData = result.getMetaData();
			int count = metaData.getColumnCount(); //number of column
			if(count > 0 ){
				String columnName = metaData.getColumnLabel(1);
				if(result.next()){

					return true;
				}
			}
		}catch(SQLException e){
			throw e;
		}finally{
			JdbcClose.statementResultSet(stmt, result);
		}

		return false;
	}


	/**
	 * 단일 결과 얻기 String
	 * @param conn Connection
	 * @param sql String sql
	 * @return String
	 * @throws SQLException SQLException
	 */
	public static String getResultOne(Connection conn, String sql) throws SQLException {
		String resultValue = null;
		
		Statement stmt = null;
		ResultSet result = null;
		//noinspection CaughtExceptionImmediatelyRethrown
		try{
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			ResultSetMetaData metaData = result.getMetaData();
			int count = metaData.getColumnCount(); //number of column
			if(count > 0 ){
				String columnName = metaData.getColumnLabel(1); 		
				if(result.next()){
				
					resultValue = result.getString(columnName);			
				}
			}
		}catch(SQLException e){
			throw e;
		}finally{
			JdbcClose.statementResultSet(stmt, result);
		}
		
		return resultValue;
	}

	public static String getResultOne(String sql, Map<Integer, PrepareStatementData> prepareStatementDataMap){
		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			return getResultOne(conn, sql, prepareStatementDataMap);
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}
	}


	public static String getResultOne(Connection conn, String sql, Map<Integer, PrepareStatementData> prepareStatementDataMap) throws SQLException {
		String resultValue = null;

		Statement stmt = null;
		ResultSet result = null;
		//noinspection CaughtExceptionImmediatelyRethrown
		try{
			StmtResultSet stmtResultSet = JdbcCommon.makeStmtResultSet(conn, sql, prepareStatementDataMap, 0);
			stmt = stmtResultSet.getStmt();
			result = stmtResultSet.getResultSet();
			ResultSetMetaData metaData = result.getMetaData();
			int count = metaData.getColumnCount(); //number of column
			if(count > 0 ){
				String columnName = metaData.getColumnLabel(1);
				if(result.next()){

					resultValue = result.getString(columnName);
				}
			}
		}catch(SQLException e){
			throw e;
		}finally{
			JdbcClose.statementResultSet(stmt, result);
		}

		return resultValue;
	}

	public static byte [] getResultBytes(String sql){
		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			return getResultBytes(conn, sql);
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}
	}

	public static byte[] getResultBytes(Connection conn, String sql) throws SQLException {
		byte[] resultValue = null;

		Statement stmt = null;
		ResultSet result = null;
		//noinspection CaughtExceptionImmediatelyRethrown
		try{
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			ResultSetMetaData metaData = result.getMetaData();
			int count = metaData.getColumnCount(); //number of column
			if(count > 0 ){
				String columnName = metaData.getColumnLabel(1);
				if(result.next()){
					resultValue = result.getBytes(columnName);
				}
			}
		}catch(SQLException e){
			throw e;
		}finally{
			JdbcClose.statementResultSet(stmt, result);
		}

		return resultValue;
	}

	public static byte[] getResultBytes(String sql, Map<Integer, PrepareStatementData> prepareStatementDataMap){
		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			return getResultBytes(conn, sql, prepareStatementDataMap);
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}
	}

	public static byte[] getResultBytes(Connection conn, String sql, Map<Integer, PrepareStatementData> prepareStatementDataMap) throws SQLException {
		byte[] resultValue = null;

		Statement stmt = null;
		ResultSet result = null;
		//noinspection CaughtExceptionImmediatelyRethrown
		try{
			StmtResultSet stmtResultSet = JdbcCommon.makeStmtResultSet(conn, sql, prepareStatementDataMap, 0);
			stmt = stmtResultSet.getStmt();
			result = stmtResultSet.getResultSet();
			ResultSetMetaData metaData = result.getMetaData();
			int count = metaData.getColumnCount(); //number of column
			if(count > 0 ){
				String columnName = metaData.getColumnLabel(1);
				if(result.next()){

					resultValue = result.getBytes(columnName);
				}
			}
		}catch(SQLException e){
			throw e;
		}finally{
			JdbcClose.statementResultSet(stmt, result);
		}

		return resultValue;
	}


	/**
	 * 단일 컬럼의 결과를 list로 얻기
 	 * @param sql String sql
	 * @return List
	 */
	public static List<String> getStringList(String sql){
		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			return getStringList(conn, sql);
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}
	}
	
	/**
	 * 단일 컬럼의 결과를 list로 얻기
	 * @param conn Connection
	 * @param sql  String sql
	 * @return List
	 * @throws SQLException SQLException
	 */
	public static List<String> getStringList(Connection conn, String sql) throws SQLException {
		List<String> resultList = new ArrayList<>();
	
		Statement stmt = null;
		ResultSet result = null;
		try{
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			ResultSetMetaData metaData = result.getMetaData();
			String columnName = metaData.getColumnLabel(1); 	
			while(result.next()){
				resultList.add(result.getString(columnName));
			}
		}catch(SQLException e){
			resultList.clear();
			throw e;
		}finally{
			JdbcClose.statementResultSet(stmt, result);
		}
		
		return resultList;
	}

	public static List<String> getStringList(String sql, Map<Integer, PrepareStatementData> prepareStatementDataMap){
		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			return getStringList(conn, sql, prepareStatementDataMap);
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}
	}

	public static List<String> getStringList(Connection conn, String sql, Map<Integer, PrepareStatementData> prepareStatementDataMap) throws SQLException {
		List<String> resultList = new ArrayList<>();

		Statement stmt = null;
		ResultSet result = null;
		try{
			StmtResultSet stmtResultSet = JdbcCommon.makeStmtResultSet(conn, sql, prepareStatementDataMap, 0);
			stmt = stmtResultSet.getStmt();
			result = stmtResultSet.getResultSet();

			ResultSetMetaData metaData = result.getMetaData();
			String columnName = metaData.getColumnLabel(1);
			while(result.next()){
				resultList.add(result.getString(columnName));
			}
		}catch(SQLException e){
			resultList.clear();
			throw e;
		}finally{
			JdbcClose.statementResultSet(stmt, result);
		}

		return resultList;
	}

	/**
	 * 테이블의 모든 데이터를 Map == row 화 하여 list로 얻기
	 * @param tableName String table name
	 * @return List  Map == row
	 */
	public static List<Map<String, String>> getAllMapStringList(String tableName){
		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			return getAllMapStringList(conn, tableName);
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}
	}


	/**
	 * 테이블의 모든 데이터를 Map == row 화 하여 list로 얻기
	 * @param conn Connection
	 * @param tableName String table name
	 * @return List Map == row
	 * @throws SQLException SQLException
	 */
	public static List<Map<String, String>> getAllMapStringList(Connection conn, String tableName) throws SQLException {
		String sql = "SELECT * FROM " + tableName;	
		return 	getMapStringList(conn, sql);
	}

	/**
	 * sql을 이용하여 결과를 Map == row 화 하여 list로 얻기
	 * @param sql String sql 
	 * @return List Map == row
	 */ 
	public static List<Map<String, String>> getMapStringList(String sql){

		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			return getMapStringList(conn, sql);
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}
	}

	/**
	 * sql을 이용하여 결과를 Map == row 화 하여 list로 얻기
	 * @param conn Connection
	 * @param sql  String sql 
	 * @return List Map == row
	 * @throws SQLException SQLException
	 */
	public static List<Map<String, String>> getMapStringList(Connection conn, String sql) throws SQLException {
		
		List<Map<String, String>> resultMapList = new ArrayList<>();
		
		Statement stmt = null;
		ResultSet result = null;
		
		try{
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);

			String [] columnNames = Database.getColumnNames(result);

			while(result.next()){
				Map<String, String> resultMap = new HashMap<>();
				for (String columnName : columnNames){
					resultMap.put(columnName, result.getString(columnName));
				}			
				resultMapList.add(resultMap);
			}
		}catch(SQLException e){
			resultMapList.clear();
			throw e;
		}finally{
			JdbcClose.statementResultSet(stmt, result);
		}
		
		return resultMapList;
	}

	/**
	 * sql을 이용하여 결과를 Map == row 화 하여 list로 얻기
	 * @param sql String sql
	 * @return List Map == row
	 */
	public static List<Map<String, String>> getMapStringList(String sql, Map<Integer, PrepareStatementData> prepareStatementDataMap){

		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			return getMapStringList(conn, sql, prepareStatementDataMap);
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}
	}

	public static List<Map<String, String>> getMapStringList(Connection conn, String sql, Map<Integer, PrepareStatementData> prepareStatementDataMap) throws SQLException {

		List<Map<String, String>> resultMapList = new ArrayList<>();

		Statement stmt = null;
		ResultSet result = null;

		try{
			StmtResultSet stmtResultSet = JdbcCommon.makeStmtResultSet(conn, sql, prepareStatementDataMap, 0);
			stmt = stmtResultSet.getStmt();
			result = stmtResultSet.getResultSet();

			String [] columnNames = Database.getColumnNames(result);

			while(result.next()){
				Map<String, String> resultMap = new HashMap<>();
				for (String columnName : columnNames){
					resultMap.put(columnName, result.getString(columnName));
				}
				resultMapList.add(resultMap);
			}
		}catch(SQLException e){
			resultMapList.clear();
			throw e;
		}finally{
			JdbcClose.statementResultSet(stmt, result);
		}

		return resultMapList;
	}


	/**
	 * 단일 row 를 Map 화 하여 얻기
	 * @param sql String sql
	 * @return Map
	 */
	public static Map<String, String> getMapString( String sql){
		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			return getMapString(conn, sql);
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}
	}
	

	/**
	 * 단일 row 를 Map 화 하여 얻기
	 * @param conn Connection
	 * @param sql  String sql
	 * @return Map
	 * @throws SQLException SQLException
	 */
	public static Map<String, String> getMapString(Connection conn, String sql) throws SQLException {
		Map<String, String> resultMap = new HashMap<>();
		Statement stmt = null;
		ResultSet result = null;
		//noinspection CaughtExceptionImmediatelyRethrown
		try{
			stmt = conn.createStatement();
			result = stmt.executeQuery(sql);
			String [] columnNames = Database.getColumnNames(result);
			if(result.next()){		
				for (String columnName : columnNames){
					resultMap.put(columnName, result.getString(columnName));
				}				
			}else{
				resultMap = null;
			}
		}catch(SQLException e){
			throw e;
		}finally{
			JdbcClose.statementResultSet(stmt, result);

		}
		return resultMap;
	}

	public static Map<String, String> getMapString( String sql, Map<Integer, PrepareStatementData> prepareStatementDataMap){
		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			return getMapString(conn, sql, prepareStatementDataMap);
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}
	}

	public static Map<String, String> getMapString(Connection conn, String sql, Map<Integer, PrepareStatementData> prepareStatementDataMap) throws SQLException {
		Map<String, String> resultMap = new HashMap<>();
		Statement stmt = null;
		ResultSet result = null;
		//noinspection CaughtExceptionImmediatelyRethrown
		try{
			StmtResultSet stmtResultSet = JdbcCommon.makeStmtResultSet(conn, sql, prepareStatementDataMap, 0);
			stmt = stmtResultSet.getStmt();
			result = stmtResultSet.getResultSet();
			String [] columnNames = Database.getColumnNames(result);
			if(result.next()){
				for (String columnName : columnNames){
					resultMap.put(columnName, result.getString(columnName));
				}
			}else{
				resultMap = null;
			}
		}catch(SQLException e){
			throw e;
		}finally{
			JdbcClose.statementResultSet(stmt, result);

		}
		return resultMap;
	}


	/**
	 * sql 실행
	 * @param sql String sql
	 * @return int fail -1
	 */
	public static int execute(String sql){
		ApplicationConnectionPool connectionPool = ApplicationConnectionPool.getInstance();
		try(Connection conn = connectionPool.getConnection()){
			int result =  execute(conn, sql);

			if(!connectionPool.isAutoCommit()){
				conn.commit();
			}
			return result;
		}catch(SQLException e){
			log.error(ExceptionUtil.getStackTrace(e));
			return -1;
		}
	}

	/**
	 * sql 실행
	 * @param conn Connection
	 * @param sql String sql
	 * @return int fail -1
	 * @throws SQLException SQLException
	 */
	public static int execute(Connection conn, String sql) throws SQLException {
		PreparedStatement pstmt = null;
		int count ;
		//noinspection CaughtExceptionImmediatelyRethrown
		try{
			pstmt = conn.prepareStatement(sql);			
			count = pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = null;
		
		}catch(SQLException e){
			throw e;
		}finally{

			if(pstmt!= null){
				//noinspection CatchMayIgnoreException
				try{pstmt.close(); }catch(Exception e){}
			}
		}
	
		return count;
	}

	public static int execute(String sql,  Map<Integer, PrepareStatementData> prepareStatementDataMap){
		ApplicationConnectionPool connectionPool = ApplicationConnectionPool.getInstance();
		try(Connection conn = connectionPool.getConnection()){
			int result =  execute(conn, sql, prepareStatementDataMap);

			if(!connectionPool.isAutoCommit()){
				conn.commit();
			}
			return result;
		}catch(SQLException e){
			log.error(ExceptionUtil.getStackTrace(e));
			return -1;
		}
	}

	public static int execute(Connection conn, String sql,  Map<Integer, PrepareStatementData> prepareStatementDataMap) throws SQLException {
		PreparedStatement pstmt = null;
		int count ;
		//noinspection CaughtExceptionImmediatelyRethrown
		try{
			pstmt = conn.prepareStatement(sql);
			JdbcCommon.setStmt(pstmt,prepareStatementDataMap);

			count = pstmt.executeUpdate();
			pstmt.close();

			pstmt = null;

		}catch(SQLException e){
			throw e;
		}finally{

			if(pstmt!= null){
				//noinspection CatchMayIgnoreException
				try{pstmt.close(); }catch(Exception e){}
			}
		}

		return count;
	}


	/**
	 * sql procedure 실행
	 * @param sql String sql(procedure)
	 * @return int fail -1
	 */
	public static int callProcedure(String sql){
		ApplicationConnectionPool connectionPool = ApplicationConnectionPool.getInstance();
		try(Connection conn = connectionPool.getConnection()){


			int result = callProcedure(conn, sql);
			if(!connectionPool.isAutoCommit()) {
				conn.commit();
			}
			return result;

		}catch(SQLException e){
			log.error(ExceptionUtil.getStackTrace(e));
			return -1;

		}
	}

	/**
	 * sql procedure 실행
	 * @param conn Connection
	 * @param sql String sql(procedure)
	 * @return int success 1, fail -1
	 * @throws SQLException SQLException
	 */
	public static int callProcedure(Connection conn, String sql) throws SQLException {
		PreparedStatement pstmt = null;
		int count ;
		//noinspection CaughtExceptionImmediatelyRethrown
		try{
			pstmt = conn.prepareCall(sql);
			pstmt.execute();
			pstmt.close();
			pstmt = null;
			count = 1;
		}catch(SQLException e){
			throw e;
		}finally{
			if(pstmt!= null){
				//noinspection CatchMayIgnoreException
				try{pstmt.close(); }catch(Exception e){}
			}
		}
		return count;
	}



	/**
	 * row data insert wait
	 * @param sql String sql row data check
	 */
	 public static void isRowWait(String sql) {
		 try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			isRowWait(conn, sql, 3, 350);
		}catch(SQLException e){
	 		log.error(ExceptionUtil.getStackTrace(e));
		}
	 }



	/**
	 * row data insert wait
	 * @param conn Connection
	 * @param sql String sql row data check
	 * @param checkCount int max check count
	 * @param waitTime long check 당 wait time
	 * @throws SQLException SQLException
	 */
	 public static void isRowWait(Connection conn, String sql, int checkCount, long waitTime) throws SQLException {
		 
		 for(int i=0 ; i<checkCount ; i++){
			 
			 if(isRowData(conn, sql)) {
				 break;
			 }
			 	
			 try {
				 Thread.sleep(waitTime);
			 }catch(Exception e) {
				 log.error(ExceptionUtil.getStackTrace(e));
			 }
			 
		 }	 
	 }

	public static void isRowWait(String sql, Map<Integer, PrepareStatementData> prepareStatementDataMap) {
		try(Connection conn = ApplicationConnectionPool.getInstance().getCommitConnection()){
			isRowWait(conn, sql, prepareStatementDataMap, 3, 350);
		}catch(SQLException e){
			log.error(ExceptionUtil.getStackTrace(e));
		}
	}


	/**
	 * row data insert wait
	 * @param conn Connection
	 * @param sql String sql row data check
	 * @param checkCount int max check count
	 * @param waitTime long check 당 wait time
	 * @throws SQLException SQLException
	 */
	public static void isRowWait(Connection conn, String sql, Map<Integer, PrepareStatementData> prepareStatementDataMap, int checkCount, long waitTime) throws SQLException {

		for(int i=0 ; i<checkCount ; i++){

			if(isRowData(conn, sql, prepareStatementDataMap)) {
				break;
			}

			try {
				Thread.sleep(waitTime);
			}catch(Exception e) {
				log.error(ExceptionUtil.getStackTrace(e));
			}

		}
	}



}
