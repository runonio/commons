
package io.runon.collect.crawling.core.http;

import io.runon.commons.config.Config;
import io.runon.commons.exception.ConnectRuntimeException;
import io.runon.commons.exception.IORuntimeException;
import io.runon.commons.exception.SocketTimeoutRuntimeException;
import io.runon.commons.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.zip.GZIPInputStream;

/**
 * HttpURLConnection 을 활용한 script
 * @author macle
 */
@Slf4j
public class HttpUrl {

	/**
	 * Rest Get 형태로 활용
	 * @param url address
	 * @return rest result or script
	 */
	public static String get(String url){
		return get(url, "UTF-8");
	}

	/**
	 * Rest Get 형태로 활용
	 * @param url address
	 * @param charSet char set
	 * @return rest result or script
	 */
	public static String get(String url, String charSet){
		return HttpUrl.getScript(url, getChromeGetSimple(charSet));
	}

	/**
	 * 크롬 기본 옵션정보
	 * @param charSet char set
	 * @return 크롬 User-Agent 세팅 된 옵션
	 */
	public static JSONObject getChromeGetSimple(String charSet){
		JSONObject requestProperty = new JSONObject();
		requestProperty.put("User-Agent", getChromeUserAgent());

		JSONObject optionData = new JSONObject();
		optionData.put(HttpOptionDataKey.REQUEST_METHOD, "GET");
		optionData.put(HttpOptionDataKey.CHARACTER_SET,charSet);
		optionData.put(HttpOptionDataKey.REQUEST_PROPERTY, requestProperty);
		return optionData;
	}

	/**
	 * 크롬 기본 정보 (크롬 버젼이 올라가면 아래설정이 변경 됨)
	 * @return chrome user agent
	 */
	public static String getChromeUserAgent(){
		return Config.getConfig("chrome.user.agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36");
	}

	public static String getScript(String url, String jsonValue) {
		return getScript(url, new JSONObject(jsonValue), true);
	}

	/**
	 * url 에 해당하는 스크립트를 얻기
	 * 통신용이기 때문에 오류처리에 대한 메시지도 정의함
	 * optionData
	 * - requestMethod (GET, POST, HEAD, OPTIONS, PUT, DELETE, TRACE)
	 * - requestProperty (+Cookie)
	 * - charSet (def : UTF-8)
	 * - outputStreamValue
	 * - readTimeout (def : 30000)
	 * - connectTimeout (def : 30000)
	 *
	 * @param url url
	 * @param optionData JSONObject
	 * @return String script
	 */
	public static String getScript(String url, JSONObject optionData) {
		return getScript(url, optionData, false);
	}

	/**
	 * url 에 해당하는 스크립트를 얻기
	 * 통신용이기 때문에 오류처리에 대한 메시지도 정의함
	 * optionData
	 * - requestMethod (GET, POST, HEAD, OPTIONS, PUT, DELETE, TRACE)
	 * - requestProperty (+Cookie)
	 * - charSet (def : UTF-8)
	 * - outputStreamValue
	 * - readTimeout (def : 30000)
	 * - connectTimeout (def : 30000)
	 * 
	 * @param url url
	 * @param optionData JSONObject
	 * @param isErrorLog error log extract flag
	 * @return String script
	 */
	public static String getScript(String url, JSONObject optionData, boolean isErrorLog) {
		return getMessage(url, optionData, isErrorLog).getMessage();
	}

	public static HttpMessage getMessage(String url, JSONObject optionData){
		return getMessage(url, optionData, false);
	}

	public static HttpMessage getMessage(String url, String jsonOptionValue){
		return getMessage(url, new JSONObject(jsonOptionValue), false);
	}

	public static HttpMessage getMessage(String url, JSONObject optionData, boolean isErrorLog){

		try {
			HttpURLConnection conn = newHttpURLConnection(url, optionData);
			try {
				int MAX_REDIRECT_COUNT = 3;
				for (int i = 0; i < MAX_REDIRECT_COUNT; i++) {
					if (conn.getResponseCode() == HttpsURLConnection.HTTP_MOVED_TEMP
							|| conn.getResponseCode() == HttpsURLConnection.HTTP_MOVED_PERM) {
						// Redirected URL 받아오기
						String redirectedUrl = conn.getHeaderField("Location");
						conn = newHttpURLConnection(redirectedUrl, optionData);

					}
					else {
						break;
					}
				}

			} catch (IOException e) {
				if(isErrorLog) {
					log.error(ExceptionUtil.getStackTrace(e));
				}
			}
			HttpMessage httpMessage = new HttpMessage();
			httpMessage.setHeaderFields( conn.getHeaderFields());

			String charSet = "UTF-8";

			if (optionData!= null && !optionData.isNull(HttpOptionDataKey.CHARACTER_SET)) {
				try {
					charSet = optionData.getString(HttpOptionDataKey.CHARACTER_SET);
				} catch (JSONException e) {
					if(isErrorLog) {
						log.error(ExceptionUtil.getStackTrace(e));
					}
				}
			}
			httpMessage.setResponseCode(conn.getResponseCode());
			httpMessage.setMessage(getScript(conn, charSet));
			try{
				conn.disconnect();
			}catch (Exception ignore){}

			return httpMessage;
		}catch(SocketTimeoutException e){
			throw new SocketTimeoutRuntimeException(e);
		}catch(ConnectException e){
			throw new ConnectRuntimeException(e);
		}catch(IOException e){
			throw new IORuntimeException(e);
		}
	}

	/**
	 * HttpURLConnection 에 해당 하는 script 를 얻어옴
	 * @param conn HttpURLConnection
	 * @param charSet String
	 * @return String script
	 * @throws IOException IOException
	 */
	public static String getScript(HttpURLConnection conn, String charSet) throws IOException {
		StringBuilder message = new StringBuilder(); 
		BufferedReader br = null;
		try {
			if (conn != null && conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				if(Objects.equals(conn.getContentEncoding(), "gzip")) {
					try {
						br = new BufferedReader(new InputStreamReader(new GZIPInputStream(conn.getInputStream()), charSet));
					} catch (IOException e) {
						br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charSet));
					}
				}
				else {
					br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charSet));
				}
						
				for (;;) {
					String line = br.readLine();
					if (line == null) break;
					message.append(line).append('\n');
				}


				if(message.length()>0){
					//마지막 엔터제거
					message.setLength(message.length()-1);
				}
			} else if(conn != null && conn.getResponseCode() == HttpsURLConnection.HTTP_FORBIDDEN &&  conn.getHeaderField("Server") != null && conn.getHeaderField("Server").startsWith("cloudflare")) {
				if(Objects.equals(conn.getContentEncoding(), "gzip")) {
					try {
						br = new BufferedReader(new InputStreamReader(new GZIPInputStream(conn.getErrorStream()), charSet));
					} catch (IOException e) {
						br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), charSet));
					}
				}
				else {
					br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), charSet));
				}

				for (;;) {
					String line = br.readLine();
					if (line == null) break;
					message.append(line).append('\n');
				}

				if(message.length()>0){
					//마지막 엔터제거
					message.setLength(message.length()-1);
				}
			}else if( conn != null){

				try {
					br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charSet));
					for (;;) {
						String line = br.readLine();
						if (line == null) break;
						message.append(line).append('\n');
					}
				}catch (Exception ignore){
				}

				if(message.length() == 0){
					br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), charSet));
					for (;;) {
						String line = br.readLine();
						if (line == null) break;
						message.append(line).append('\n');
					}
				}

				if(message.length()>0){
					//마지막 엔터제거
					message.setLength(message.length()-1);
				}

			}
		}
		finally{
			try{
				if(br != null) {
					br.close();
				}
			}catch(Exception ignore){}
		}
		
		return message.toString();
	}

	/**
	 * url에 해당하는 파일을 다운 받아서 filePath 에 저장
	 * @param urlAddress String
	 * @param filePath String save path
	 * @return File
	 * @throws IOException IOException
	 */
	public static File getFile(String urlAddress, String filePath) throws IOException {
		InputStream in = null;
		FileOutputStream fos = null ;
		//noinspection CaughtExceptionImmediatelyRethrown
		try {
			File file = null;
			HttpURLConnection conn = newHttpURLConnection(urlAddress, null);
			
			if (conn != null && conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				
				file = new File(filePath);
				//noinspection ResultOfMethodCallIgnored
				file.getParentFile().mkdirs();
				if(file.exists()){
					//noinspection ResultOfMethodCallIgnored
					file.delete();
			     }
				//noinspection ResultOfMethodCallIgnored
				file.createNewFile();
				in = conn.getInputStream();
				fos = new FileOutputStream(file);

		        byte[] buffer = new byte[1024];
		        int len1 ;
		        while ((len1 = in.read(buffer)) != -1) {
		            fos.write(buffer, 0, len1);
		        }
		        fos.close();
		        in.close();
				conn.disconnect();
			}
			return file;
		} 
		catch (IOException e) {
			throw e;
		}finally{
			if(in != null){
				//noinspection CatchMayIgnoreException
				try{in.close();}catch(Exception e){}
			}
			if(fos != null){
				//noinspection CatchMayIgnoreException
				try{fos.close();}catch(Exception e){}
			}
		}
	}

	/**
	 * HttpUrlConnection 생성
	 * @param urlAddr String
	 * @param optionData JSONObject
	 * @return HttpURLConnection
	 * @throws IOException IOException
	 */
	public static HttpURLConnection newHttpURLConnection(String urlAddr, JSONObject optionData) throws IOException {

	 	URL url = new URL(urlAddr);
	 	HttpURLConnection conn ;

        String protocol = url.getProtocol();
        if(protocol == null){
        	protocol = "";
        }
        protocol = protocol.toLowerCase();

        if (protocol.equals("https")) {
            trustAllHosts();
            HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
            https.setHostnameVerifier(DO_NOT_VERIFY);
            conn = https;
        } else {
        	conn = (HttpURLConnection) url.openConnection();
        }

        if (conn != null) {
	 		conn.setUseCaches(false);
	 		conn.setDoInput( true ) ;
	 		conn.setDoOutput( true ) ;
	 		conn.setInstanceFollowRedirects( false );

			int connectTimeout = 30000;
			if (optionData == null) {
				conn.setConnectTimeout(connectTimeout);
				conn.setRequestMethod("GET");
				return conn;
			}
			if (!optionData.isNull(HttpOptionDataKey.REQUEST_PROPERTY)) {
				JSONObject property = optionData.getJSONObject(HttpOptionDataKey.REQUEST_PROPERTY);

				Iterator<String> keys = property.keys();
				while (keys.hasNext()) {
					String key = keys.next();
					conn.setRequestProperty(key, property.getString(key));
				}

			}

			if (!optionData.isNull(HttpOptionDataKey.REQUEST_METHOD)) {
				String req = optionData.getString(HttpOptionDataKey.REQUEST_METHOD);
				conn.setRequestMethod(req);
			} else {
				conn.setRequestMethod("GET");
			}

			int readTimeout = 30000;
			if (!optionData.isNull(HttpOptionDataKey.READ_TIME_OUT)) {
				try {
					readTimeout = optionData.getInt(HttpOptionDataKey.READ_TIME_OUT);
				} catch (JSONException e) {
					log.error(ExceptionUtil.getStackTrace(e));
				}
			}
			conn.setReadTimeout(readTimeout);

			if (!optionData.isNull(HttpOptionDataKey.CONNECT_TIME_OUT)) {
				try {
					connectTimeout = optionData.getInt(HttpOptionDataKey.CONNECT_TIME_OUT);
				} catch (JSONException e) {
					log.error(ExceptionUtil.getStackTrace(e));
				}
			}
			conn.setConnectTimeout(connectTimeout);

			String charSet = "UTF-8";
			if (!optionData.isNull(HttpOptionDataKey.CHARACTER_SET)) {
				try {
					charSet = optionData.getString(HttpOptionDataKey.CHARACTER_SET);
				} catch (JSONException e) {
					log.error(ExceptionUtil.getStackTrace(e));
				}
			}

			if (!optionData.isNull(HttpOptionDataKey.OUTPUT_STREAM_WRITE)) {
				byte[] contents;
				String outputStreamValue = optionData.getString(HttpOptionDataKey.OUTPUT_STREAM_WRITE);
				contents = outputStreamValue.getBytes(charSet);
				OutputStream outSteam = conn.getOutputStream();
				outSteam.write(contents);
				outSteam.flush();
				outSteam.close();
			}
        }

        return conn;
	}


	private static boolean isTrustAllHosts = false;
    private static void trustAllHosts() {
		if(isTrustAllHosts){
			return;
		}
        // Create a trust manager that does not validate certificate chains 
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() { 
                public java.security.cert.X509Certificate[] getAcceptedIssuers() { 
                        return new java.security.cert.X509Certificate[] {}; 
                } 
 
                public void checkClientTrusted( 
                        java.security.cert.X509Certificate[] chain, 
                        String authType) {

                } 
 
                public void checkServerTrusted( 
                        java.security.cert.X509Certificate[] chain, 
                        String authType) {

                } 
        } }; 
 
        // Install the all-trusting trust manager 
        try { 
                SSLContext sc = SSLContext.getInstance("TLS"); 
                sc.init(null, trustAllCerts, new java.security.SecureRandom()); 
                HttpsURLConnection 
                                .setDefaultSSLSocketFactory(sc.getSocketFactory()); 
        } catch (Exception e) {
				log.error(ExceptionUtil.getStackTrace(e));
        }
		isTrustAllHosts = true;
    }
	private final static HostnameVerifier DO_NOT_VERIFY = (arg0, arg1) -> true;



}