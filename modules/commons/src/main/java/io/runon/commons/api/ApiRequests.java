

package io.runon.commons.api;
/**
 * api 요청 전역 메소드 모음
 *
 * @author macle
 */
public class ApiRequests {

    /**
     * 1회성 메시지
     * 결과를 전송받은후에 통신종료
     * @param hostAddress String address
     * @param port int port
     * @param packageName String package name
     * @param code String code = run class name
     * @param message String request message
     * @return String response message
     */
    public static String sendToReceiveMessage(String hostAddress, int port, String packageName, String code, String message){
        return sendToReceiveMessage(hostAddress,port,packageName,code,message,null,null);
    }

    /**
     * 1회성 메시지
     * 결과를 전송받은후에 통신종료
     * @param hostAddress String address
     * @param port int port
     * @param packageName String package name
     * @param code String code = run class name
     * @param message String request message
     * @param connectTimeOut Integer 연결대기
     * @param waitTimeOut Long 연결이후 결과를 얻기까지 기다리는 시간
     * @return String response message
     */
    public static String sendToReceiveMessage(String hostAddress, int port, String packageName, String code, String message, Integer connectTimeOut, Long waitTimeOut){

        ApiRequest apiRequest = new ApiRequest(hostAddress , port);
        if(packageName != null){
            apiRequest.setPackageName(packageName);
        }

        if(connectTimeOut != null){
            apiRequest.setConnectTimeOut(connectTimeOut);
        }

        if(waitTimeOut != null){
            apiRequest.setWaitTimeOut(waitTimeOut);
        }

        apiRequest.connect();

        String receiveMessage = apiRequest.sendToReceiveMessage(code,message);
        apiRequest.disConnect();
        return receiveMessage;

    }


}