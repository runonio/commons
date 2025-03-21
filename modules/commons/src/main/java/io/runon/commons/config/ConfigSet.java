
package io.runon.commons.config;

/**
 *
 *  config 설정 정보를 변경할 경우 main 에서 여기 설정을 ㅁ너저 변경 
 *  설정에서 사용되는 초기 로드정보 설정
 *  기본 설정을 변경할 경우
 *  초기 구동시 Config 호출 보다 먼저 호출되게 구현
 *
 * @author macle
 */
public class ConfigSet {

    public static String CONFIG_DIR_PATH = "config";


    public static String CONFIG_PATH = CONFIG_DIR_PATH + "/config.xml";

    public static String LOG_BACK_PATH = CONFIG_DIR_PATH +"/logback.xml";

    public static int XML_FILE_PRIORITY = 1000;

    //기본 시스템 프로퍼티도 우선순위에 들어갈지 여부
    public static boolean IS_SYSTEM_PROPERTIES_USE = true;
    //기본은 제일 나중에 호출
    public static int SYSTEM_PROPERTIES_PRIORITY = Integer.MAX_VALUE;

}
