package io.runon.commons.http;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author macle
 */
public class HttpApis {
    public static final HttpApi GET = makeDefaultGet();

    public static final HttpApi POST_JSON = makeDefaultPostJson();

    public static final HttpApi POST = makeDefaultPost();

    public static final Map<String,String> JSON_UTF8_PROPERTY = makeJsonUtf8RequestProperty();

    public static HttpApi makeDefaultGet(){
        HttpApi httpApi = new HttpApi();
        httpApi.setDefaultMethod("GET");
        httpApi.setDefaultCharSet(StandardCharsets.UTF_8);
        return httpApi;
    }

    public static HttpApi makeDefaultPost(){
        HttpApi httpApi = new HttpApi();
        httpApi.setDefaultMethod("POST");
        httpApi.setDefaultCharSet(StandardCharsets.UTF_8);
        return httpApi;
    }


    public static Map<String,String> makeJsonUtf8RequestProperty(){
        Map<String,String> requestProperty = new HashMap<>();
        requestProperty.put("Content-Type","application/json; charset=utf-8");
        requestProperty.put("Accept", "application/json");
        return requestProperty;
    }

    public static HttpApi makeDefaultPostJson(){
        HttpApi httpApi = new HttpApi();
        httpApi.setDefaultMethod("POST");
        httpApi.setDefaultCharSet(StandardCharsets.UTF_8);
        Map<String,String> requestProperty = new HashMap<>();
        requestProperty.put("Content-Type","application/json; charset=utf-8");
        requestProperty.put("Accept", "application/json");
        httpApi.setDefaultRequestProperty(requestProperty);
        //용량이 큰 요청을 할 수 있다.
//        httpApi.setReadTimeOut((int)Times.MINUTE_10);
        return httpApi;
    }

    public static HttpApiResponse getResponse(String url){
        return GET.getResponse(url);
    }
    public static String getMessage(String url){
        return GET.getResponse(url).getMessage();
    }

    public static HttpApiResponse getResponse(String url, Map<String, String> requestProperty){
        return GET.getResponse(url, requestProperty);
    }
    public static String getMessage(String url, Map<String, String> requestProperty){
        return GET.getResponse(url).getMessage();
    }

    public static HttpApiResponse postJson(String url, String outStreamJson){
        return POST_JSON.getResponse(url, outStreamJson);
    }

    public static String postJsonMessage(String url, String outStreamJson){
        return POST_JSON.getResponse(url, outStreamJson).getMessage();
    }

    public static HttpApiResponse postJson(String url, Map<String, String> requestProperty, String outStreamJson){
        return POST_JSON.getResponse(url, requestProperty, outStreamJson);
    }
    public static String postJsonMessage(String url,  Map<String, String> requestProperty, String outStreamJson){
        return POST_JSON.getResponse(url, requestProperty, outStreamJson).getMessage();
    }

    public static HttpApiResponse post(String url, Map<String, String> requestProperty, String outStreamJson) {
        return POST.getResponse(url, requestProperty, outStreamJson);
    }

    public static File downloadFile(String urlAddress, String downloadPath){
        return GET.downloadFile(urlAddress, downloadPath);
    }

    public static HttpApiResponse sendFile(String urlAddress, File file){
        return POST.sendFile(urlAddress, file);
    }
    public static HttpApiResponse sendFile(String urlAddress, File file, String message){
        return POST.sendFile(urlAddress, file, message);
    }
}
