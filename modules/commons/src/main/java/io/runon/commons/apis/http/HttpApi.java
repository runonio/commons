package io.runon.commons.apis.http;

import io.runon.commons.exception.ConnectRuntimeException;
import io.runon.commons.exception.IORuntimeException;
import io.runon.commons.utils.FileUtils;
import lombok.Setter;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.GZIPInputStream;

/**
 * @author macle
 */
@Setter
public class HttpApi {

    public static final String LINE_FEED = "\r\n";


    private String defaultAddress ="";
    private String defaultMethod = "GET";

    private Integer connectTimeOut= null;

    private Integer readTimeOut= null;


    private Integer fileReadTimeOut= null;


    private Charset defaultCharSet= StandardCharsets.UTF_8;


    private Map<String, String> defaultRequestProperty = null;

    public HttpApi(){

    }

    public void setRequestProperty(String key, String value){
        if(defaultRequestProperty == null){
            defaultRequestProperty = new HashMap<>();
        }
        defaultRequestProperty.put(key,value);
    }


    public String getMessage(String urlAddr){
        return getResponse(urlAddr, defaultMethod, null, null, defaultCharSet).getMessage();
    }


    public HttpApiResponse getResponse(String urlAddr){
        return getResponse(urlAddr, defaultMethod, null, null, defaultCharSet);
    }

    public String getMessage(String urlAddr,  String outStreamParam){
        return getResponse(urlAddr, defaultMethod, null, outStreamParam, defaultCharSet).getMessage();
    }

    public HttpApiResponse getResponse(String urlAddr, String outStreamParam){
        return getResponse(urlAddr, defaultMethod, null, outStreamParam, defaultCharSet);
    }

    public String getMessage(String urlAddr, Map<String, String> requestProperty ,String outStreamParam){
        return getResponse(urlAddr, defaultMethod, requestProperty, outStreamParam, defaultCharSet).getMessage();
    }

    public HttpApiResponse getResponse(String urlAddr, Map<String, String> requestProperty , String outStreamParam){
        return getResponse(urlAddr, defaultMethod, requestProperty, outStreamParam, defaultCharSet);
    }

    public String getMessage(String urlAddr, Map<String, String> requestProperty ){
        return getResponse(urlAddr, defaultMethod, requestProperty, null, defaultCharSet).getMessage();
    }


    public HttpApiResponse getResponse(String urlAddr, Map<String, String> requestProperty){
        return getResponse(urlAddr, defaultMethod, requestProperty, null, defaultCharSet);
    }




    public HttpApiResponse getResponse(String urlAddr, String method, Map<String, String> requestProperty, String outStreamParam, Charset charset){

        try {
            URL url = new URL(defaultAddress+urlAddr);
            HttpURLConnection conn;
            conn = (HttpURLConnection) url.openConnection();
            if(method == null){
                conn.setRequestMethod(defaultMethod);
            }else{
                conn.setRequestMethod(method);
            }

            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);


            if(readTimeOut != null) {
                conn.setReadTimeout(readTimeOut);
            }

            if(connectTimeOut != null) {
                conn.setConnectTimeout(connectTimeOut);
            }

            if(defaultRequestProperty != null){
                Set<String> keys = defaultRequestProperty.keySet();

                for(String key: keys){
                    conn.setRequestProperty(key, defaultRequestProperty.get(key));
                }
            }

            if(requestProperty != null){
                Set<String> keys = requestProperty.keySet();

                for(String key: keys){
                    conn.setRequestProperty(key, requestProperty.get(key));
                }
            }

            if (outStreamParam != null) {
                byte[] contents = outStreamParam.getBytes(charset);
                OutputStream outSteam = conn.getOutputStream();
                outSteam.write(contents);
                outSteam.flush();
                outSteam.close();
            }

            HttpApiResponse httpResponse = new HttpApiResponse();
            httpResponse.setResponseCode(conn.getResponseCode());
            httpResponse.setHeaderFields(conn.getHeaderFields());
            httpResponse.setMessage(getMessage(conn, charset));

            try{
                conn.disconnect();
            }catch (Exception ignore){}

            return httpResponse;
        }catch (ConnectException connectException){
            throw new ConnectRuntimeException(connectException);
        } catch (IOException e){
            throw new IORuntimeException(e);
        }
    }
    public HttpApiResponse sendFile(String urlAddr, File file){
        return sendFile(urlAddr, file, "file", null,null, StandardCharsets.UTF_8);
    }

    public HttpApiResponse sendFile(String urlAddr, File file, String message){
        return sendFile(urlAddr, file, "file", message,"message", StandardCharsets.UTF_8);
    }

    public HttpApiResponse sendFile(String urlAddr, File file, String fileFiledName, String message, String messageFiledName, Charset charset){

        FileInputStream inputStream = null;
        PrintWriter writer = null;
        try {

            String boundary = UUID.randomUUID().toString();

            URL url = new URL(defaultAddress+urlAddr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            if(readTimeOut != null) {
                conn.setReadTimeout(readTimeOut);
            }

            if(connectTimeOut != null) {
                conn.setConnectTimeout(connectTimeOut);
            }

            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            if(fileFiledName == null){
                fileFiledName = "file";
            }


            OutputStream outputStream = conn.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);

            if(message != null){
                if(messageFiledName == null){
                    messageFiledName = "message";
                }

                writer.append("--" + boundary).append(LINE_FEED);
                writer.append("Content-Disposition: form-data; name=\"" + messageFiledName + "\"").append(LINE_FEED);
                writer.append("Content-Type: text/plain; charset=" + charset).append(LINE_FEED);
                writer.append(LINE_FEED);
                writer.append(message).append(LINE_FEED);
                writer.flush();
            }

            String fileName = file.getName();
            String contentType = URLConnection.guessContentTypeFromName(fileName); // MIME 타입 추정
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"" + fileFiledName + "\"; filename=\"" + fileName + "\"")
                    .append(LINE_FEED);
            writer.append("Content-Type: " + (contentType != null ? contentType : "application/octet-stream"))
                    .append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();

            inputStream = new FileInputStream(file);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();
            inputStream = null;
            writer.append(LINE_FEED);
            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.close();
            writer =null;

            HttpApiResponse httpResponse = new HttpApiResponse();
            httpResponse.setResponseCode(conn.getResponseCode());
            httpResponse.setHeaderFields(conn.getHeaderFields());
            httpResponse.setMessage(getMessage(conn, charset));

            try{
                conn.disconnect();
            }catch (Exception ignore){}

            return httpResponse;

        }catch (IOException e){
            throw new IORuntimeException(e);
        }finally {
            try {
                    if (inputStream != null) {
                    inputStream.close();
                }
            }catch (Exception ignore){}

            try {
                if (writer != null) {
                    writer.close();
                }
            }catch (Exception ignore){}
        }

    }


    public File downloadFile(String urlAddress, String downloadPath){
        InputStream in = null;
        FileOutputStream fos = null ;
        HttpURLConnection conn = null ;

        File pathFile = new File(downloadPath);
        String dirPath = pathFile.getParentFile().getAbsolutePath();
        if(!FileUtils.isDirectory(dirPath)){
            //noinspection ResultOfMethodCallIgnored
            new File(dirPath).mkdirs();
        }

        if(pathFile.isFile()){
            //noinspection ResultOfMethodCallIgnored
            pathFile.delete();
        }

        try {
            File file = null;
            URL url = new URL(defaultAddress+urlAddress);
            conn = (HttpsURLConnection) url.openConnection();

            if(fileReadTimeOut != null){
                conn.setReadTimeout(fileReadTimeOut);
            }else{
                if(readTimeOut != null) {
                    conn.setReadTimeout(readTimeOut);
                }
            }

            if(connectTimeOut != null) {
                conn.setConnectTimeout(connectTimeOut);
            }

            if(defaultRequestProperty != null){
                Set<String> keys = defaultRequestProperty.keySet();

                for(String key: keys){
                    conn.setRequestProperty(key, defaultRequestProperty.get(key));
                }
            }

            if (conn != null && conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

                file = new File(downloadPath);
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

                fos.getFD().sync();

                fos.close();
                in.close();


            }
            return file;
        }
        catch (IOException e) {
            throw new IORuntimeException(e);
        }finally{
            if(in != null){
                try{in.close();}catch(Exception ignore){}
            }
            if(fos != null){
                try{fos.close();}catch(Exception ignore){}
            }
            if(conn != null){
                try{  conn.disconnect();}catch(Exception ignore){}
            }
        }
    }

    public static String getMessage(HttpURLConnection conn, Charset charset){
        StringBuilder message = new StringBuilder();
        BufferedReader br = null;
        try {
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                if(Objects.equals(conn.getContentEncoding(), "gzip")) {
                    try {
                        br = new BufferedReader(new InputStreamReader(new GZIPInputStream(conn.getInputStream()), charset));
                    } catch (IOException e) {
                        br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
                    }
                }
                else {
                    br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
                }
                for (;;) {
                    String line = br.readLine();
                    if (line == null) break;
                    message.append(line).append('\n');
                }

            } else {
                try {
                    br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
                    for (;;) {
                        String line = br.readLine();
                        if (line == null) break;
                        message.append(line).append('\n');
                    }
                }catch (Exception ignore){}

                if(message.length() == 0){
                    br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), charset));
                    for (;;) {
                        String line = br.readLine();
                        if (line == null) break;
                        message.append(line).append('\n');
                    }
                }

            }
        }catch (IOException e){
            throw new IORuntimeException(e);
        } finally{
            try{
                if(br != null) {
                    br.close();
                }
            }catch(Exception ignore){}
        }

        if(message.length()>0){
            //마지막 엔터제거
            message.setLength(message.length()-1);
        }
        return message.toString();
    }




}
