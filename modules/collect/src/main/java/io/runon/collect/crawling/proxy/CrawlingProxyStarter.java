
package io.runon.collect.crawling.proxy;

import io.runon.commons.apis.socket.ApiRequests;
import io.runon.commons.apis.socket.communication.HostAddrPort;
import io.runon.commons.utils.ExceptionUtil;
import io.runon.commons.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

/**
 * CrawlingProxyStarter remote proxy
 * @author macle
 */
@Slf4j
public class CrawlingProxyStarter extends Thread{


    //10초에 한번씩 체크
    private static final long sleepTime = 1000L * 10L;

    private boolean isStop = false;
    private CrawlingProxy crawlingProxy= null;
    @Override
    public void run(){


        String fileContents = FileUtil.getFileContents(new File("config/proxy_config"), "UTF-8");
        JSONObject jsonObject = new JSONObject(fileContents);
        int communicationCount = jsonObject.getInt("communication_count");


        JSONArray jsonArray = jsonObject.getJSONArray("connection_infos");
        HostAddrPort[] hostAddrPortArray = new HostAddrPort[jsonArray.length()];

        for (int i = 0; i <hostAddrPortArray.length ; i++) {
            JSONObject info =  jsonArray.getJSONObject(i);
            HostAddrPort hostAddrPort = new  HostAddrPort();
            hostAddrPort.setHostAddress(info.getString("host_address"));
            hostAddrPort.setPort(info.getInt("port"));
            hostAddrPortArray[i] = hostAddrPort;
        }

        //무한 접속 체크
        while(!isStop){
            try {
                log.debug("connect request");
                //noinspection ForLoopReplaceableByForEach
                for (int i = 0; i <hostAddrPortArray.length ; i++) {
                    try {

                        String response = ApiRequests.sendToReceiveMessage(hostAddrPortArray[i].getHostAddress(), hostAddrPortArray[i].getPort(), "io.runon.crawling.ha", "ActiveAddrPortApi", "");

                        if (response.startsWith("S")) {
                            String[] activeInfo = response.substring(1).split(",");
                            String addr;
                            int port;
                            int crawlingPort ;
                            if(activeInfo.length ==1){
                                addr = hostAddrPortArray[i].getHostAddress();
                                port = hostAddrPortArray[i].getPort();
                                crawlingPort = Integer.parseInt(response.substring(1).trim());
                            }else{
                                addr = activeInfo[0];
                                port = Integer.parseInt(activeInfo[1]);
                                crawlingPort = Integer.parseInt(activeInfo[2]);
                            }
//                            if (PingApi.ping(addr, port)) {
//                                crawlingProxy = new CrawlingProxy(addr, crawlingPort, communicationCount);
//                                log.debug("connect success");
//
//                                break;
//                            }
                        }
                    }catch(Exception ignore){ }
                }

                if(crawlingProxy == null){
                    //noinspection BusyWait
                    Thread.sleep(sleepTime);
                    continue;
                }

                //크롤링 서버가 죽은경우 다른서버에 붙기위해 다시 대기함
                while (crawlingProxy.isConnect()) {
                    //noinspection BusyWait
                    Thread.sleep(sleepTime);
                }

                
               try {
                   //연결 종료중 에러 무시
                   crawlingProxy.disConnect();
                   crawlingProxy = null;
               }catch(Exception e){
                   log.error(ExceptionUtil.getStackTrace(e));
               }
               
               try {
                   //강제 종료 이후에 2초후 재연결
                   //noinspection BusyWait
                   Thread.sleep(2000L);
               }catch(Exception e){
                   log.error(ExceptionUtil.getStackTrace(e));
               }

            } catch (Exception e) {
                log.error(ExceptionUtil.getStackTrace(e));
                try{ //noinspection BusyWait
                    Thread.sleep(sleepTime); }catch (Exception ignore){}
            }

        }
    }

    /**
     * 서비스 중지
     */
    public void stopService(){

        isStop = true;
        if(crawlingProxy != null){
            crawlingProxy.disConnect();
        }

    }

    public static void main(String[] args) {
        new CrawlingProxyStarter().start();
    }
}
