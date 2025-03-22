
package io.runon.commons.config;

/**
 *
 * 설정 변경 정보
 * @author macle
 */
public class ConfigInfo {
	String key;
	String value ;
	boolean isDelete = false;
	/**
	 * 생성자
	 * @param key String 설정 키
	 * @param value String 설정 값
	 */
	public ConfigInfo(String key, String value){
		this.key = key;
		this.value = value;
	}

	/**
	 * 생성자
	 */
	public ConfigInfo(){

	}

	/**
	 * 데이터 삭제
	 */
	public void setDelete(){
		isDelete = true;
	}

	/**
	 * 설정 키 얻기
	 * @return String
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 키설정
	 * @param key String
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 값 얻기
	 * @return String
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 값 설정
	 * @param value String
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 삭제 여부
	 * @return boolean
	 */
	public boolean isDelete() {
		return isDelete;
	}


	public void setDelete(boolean delete) {
		isDelete = delete;
	}
}
