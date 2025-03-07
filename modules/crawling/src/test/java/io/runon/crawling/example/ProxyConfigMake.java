
package io.runon.crawling.example;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * proxy_config value 생성 예제
 * @author macle
 */
public class ProxyConfigMake {
    public static void main(String[] args) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("communication_count", 5);

        JSONArray array = new JSONArray();
        JSONObject info1= new JSONObject();
        info1.put("host_address", "crawling.seomse.com");
        info1.put("port", 22210);
        array.put(info1);

        JSONObject info2= new JSONObject();
        info2.put("host_address", "crawling.seomse.com");
        info2.put("port", 22211);
        array.put(info2);

        JSONObject info3= new JSONObject();
        info3.put("host_address", "crawling.seomse.com");
        info3.put("port", 22212);
        array.put(info3);

        jsonObject.put("connection_infos",array);
        System.out.println(jsonObject.toString());


    }
}
