
package io.runon.collect.crawling.core.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * UrlResolver
 * @author yh.heo
 */
public class UrlResolver {


	/**
	 * Url에서 사용 하는 쿼리의 데이터 변환
	 * @param query String
	 * @param key String
	 * @param value String
	 * @return String
	 */
	public static String changeURLQueryValue(String query , String key , String value) {
		return changeURLQueryValue(query , key , value , "UTF-8");
	}

	/**
	 * Url에서 사용 하는 쿼리의 데이터 변환
	 * @param query String
	 * @param key String
	 * @param value String
	 * @param charset String
	 * @return String
	 */
    public static String changeURLQueryValue(String query , String key , String value, String charset){
    	
    	if ( query == null || key == null)
    		return query;
    	
    	String queryKey;
    	
    	if (!query.contains("?"))
    		queryKey = "?" + key + "=";
    	else if (query.contains("?" + key))
    		queryKey = "?" + key + "=";
    	else
    		queryKey= "&" + key + "=";
    	
    	
    	try {
    		if ( charset != null && !charset.equals(""))
			value = URLEncoder.encode(value, charset);
    	} catch (UnsupportedEncodingException ignored) {}
    	
    	int firstIdx = query.indexOf(queryKey);
    	if ( firstIdx == -1 ) { //변수 존재 하지 않음
    		return query + queryKey + value;
    	}
		int i = query.indexOf('&', query.indexOf(queryKey) + 1);
		int lastIdx = i != -1 ? // 마지막 변수인지 검사
				i : query.length();
    	String target = query.substring( firstIdx , lastIdx );
    	query = query.replace(target, queryKey + value);
    	return query;
    }
}