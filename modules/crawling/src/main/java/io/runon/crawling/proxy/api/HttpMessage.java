package io.runon.crawling.proxy.api;

import com.google.gson.Gson;
import io.runon.commons.api.ApiMessage;
import io.runon.commons.api.Messages;
import io.runon.commons.utils.ExceptionUtil;
import io.runon.crawling.core.http.HttpUrl;
import org.json.JSONObject;
/**
 * HttpMessage proxy node api
 * @author macle
 */
public class HttpMessage  extends ApiMessage {

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
            Gson gson = new Gson();
            sendMessage(Messages.SUCCESS+ gson.toJson(HttpUrl.getMessage(messageObj.getString("url"), optionData)));
        }catch(Exception e) {
            sendMessage(Messages.FAIL + ExceptionUtil.getStackTrace(e));
        }
    }

}
