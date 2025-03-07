
package io.runon.crawling.proxy.api;

import io.runon.commons.api.Messages;
import org.json.JSONObject;

import io.runon.commons.api.ApiMessage;
import io.runon.commons.utils.ExceptionUtil;
import io.runon.crawling.core.http.HttpUrl;

/**
 * HttpScript proxy node api
 * @author macle
 */
public class HttpScript extends ApiMessage{

	@Override
	public void receive(String message) {
		try {
			JSONObject messageObj = new JSONObject(message);
			JSONObject optionData = null;
			if(!messageObj.isNull("option_data")) {
				Object obj = messageObj.get("option_data");
				if(obj.getClass() == String.class){
					optionData = new JSONObject((String) obj);
				}else{
					optionData = (JSONObject)obj;
				}
			}
			sendMessage(Messages.SUCCESS+HttpUrl.getScript(messageObj.getString("url"), optionData));
		}catch(Exception e) {
			sendMessage(Messages.FAIL + ExceptionUtil.getStackTrace(e));
		}
	}

}
