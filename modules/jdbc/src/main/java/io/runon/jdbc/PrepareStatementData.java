
package io.runon.jdbc;

import io.runon.jdbc.naming.JdbcDataType;

/**
 * data 유형과  data
 * @author macle
 */
public class PrepareStatementData {
	private JdbcDataType type;
	private Object data;
	/**
	 * 데이터 타입 얻기
	 * @return JdbcDataType
	 */
	public JdbcDataType getType() {
		return type;
	}
	/**
	 * 데이터 타입 설정
	 * @param type JdbcDataType
	 */
	public void setType(JdbcDataType type) {
		this.type = type;
	}
	/**
	 * 데이터 얻기
	 * @return Object 데이터 타입에 유효한 데이터
	 */
	public Object getData() {
		return data;
	}
	/**
	 * 데이터 설정
	 * @param data Object 데이터 타입에 유효한 데이터
	 */
	public void setData(Object data) {
		this.data = data;
	}

    public PrepareStatementData(JdbcDataType type, Object data) {
        this.type = type;
        this.data = data;
    }
    public PrepareStatementData() {

    }



}