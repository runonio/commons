


package io.runon.jdbc.naming;
/**
 * jdbc column class
 * @author macle
 */
public class JdbcColumnClass {
	private final String columnName;
	private Class<?> classes;
	
	/**
	 * 생성자
	 * @param columnName String 컬럼명
	 * @param classes Class 클래스
	 */
	JdbcColumnClass(String columnName, Class<?> classes){
		this.columnName = columnName;
		this.classes = classes;
	}
	
	/**
	 * 컬럼이름 얻기
	 * @return String Column Name
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * 컬럼 이름에 맞는 클래스를 얻기.
	 * @return  Class  ColumnName To Class
	 */
	public Class<?> getClasses() {
		return classes;
	}

	/**
	 * 클래스 설정
	 * @param classes Class
	 */
	void setClasses(Class<?> classes) {
		this.classes = classes;
	}
	
	
}