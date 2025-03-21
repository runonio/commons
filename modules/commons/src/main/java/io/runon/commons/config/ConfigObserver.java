
package io.runon.commons.config;

/**
 *
 * 설정 정보 변화 감시
 * @author macle
 */
public interface ConfigObserver {
	/**
	 * 설정정보 변경정보
	 * @param changeInfos ConfigInfo [] 변경된 설정 정보
	 */
	void updateConfig(ConfigInfo [] changeInfos);
}