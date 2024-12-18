
package io.runon.commons.api.server;

import io.runon.commons.api.ApiRequest;


/**
 * api 요청용 핸들러
 * 클라이언트가 서버의 api를 이용하는것이 아닌
 * 서버가 클라이언트의 api를 이용할떄 사용 한다.
 * api 요청이 들어왔을때 전달받는 핸들러
 *  클라이언트가 서버에 나를 컨트롤 해달라고 하는 경우에 사용
 *
 * @author macle
 */
public interface ApiRequestConnectHandler {
	/**
	 * 연결
	 * @param apiRequest ApiRequest
	 */
	void connect(ApiRequest apiRequest);
}
