
package io.runon.collect.crawling.node;

import io.runon.collect.crawling.core.http.HttpMessage;
import io.runon.collect.crawling.core.http.HttpUrl;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
/**
 * local node
 * @author macle
 */
@Slf4j
public class CrawlingLocalNode extends CrawlingNode {

	@Override
	public String getHttpScript(String url, JSONObject optionData){
		log.debug("local node seq: " + seq);
		return HttpUrl.getScript(url, optionData);

	}

	@Override
	public HttpMessage getHttpMessage(String url, JSONObject optionData) {
		log.debug("local node seq: " + seq);
		return HttpUrl.getMessage(url, optionData);
	}
}
