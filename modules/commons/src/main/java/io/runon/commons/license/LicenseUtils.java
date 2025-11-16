package io.runon.commons.license;

import io.runon.commons.exception.SocketRuntimeException;
import io.runon.commons.utils.NetworkUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.TimeZone;
/**
 * @author macle
 */
public class LicenseUtils {


    public static String getDateText(long time){
        ZoneId zoneId = ZoneId.of("Asia/Seoul");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone(zoneId));
        return sdf.format(new Date(time));
    }

    public static String [] getMacAddressArray() {
        LinkedHashSet<String> set = new LinkedHashSet<>();

        try {
            Enumeration<NetworkInterface> networkInterfaceEum = NetworkInterface.getNetworkInterfaces();
            while(networkInterfaceEum.hasMoreElements()){

                NetworkInterface networkInterface = networkInterfaceEum.nextElement();

                byte [] macBytes = networkInterface.getHardwareAddress();

                Enumeration<InetAddress> inetAddressEum = networkInterface.getInetAddresses();

                while(inetAddressEum.hasMoreElements()){
                    long t = System.currentTimeMillis();
                    InetAddress inetAddress = inetAddressEum.nextElement();

                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
                        if(macBytes != null){
                            set.add(NetworkUtils.getMacAddress(networkInterface.getHardwareAddress()));
                        }
                    }
                }
            }
        }catch (SocketException e){
            throw new SocketRuntimeException(e);
        }

        if(set.size() > 0){
            String [] array = set.toArray(new String[0]);
            set.clear();
            return array;

        }

        return new String[0];
    }

    public static String getWinUUID() {
        String command = "wmic csproduct get UUID";
        BufferedReader sNumReader = null;
        try {
            StringBuilder output = new StringBuilder();

            Process SerNumProcess = Runtime.getRuntime().exec(command);
            sNumReader = new BufferedReader(new InputStreamReader(SerNumProcess.getInputStream()));

            String line;
            while ((line = sNumReader.readLine()) != null) {
                output.append(line).append("\n");
            }
            return output.substring(output.indexOf("\n"), output.length()).trim();

        }catch (IOException e){
            return null;
        }finally {
            try{if(sNumReader != null){sNumReader.close();}}catch (Exception ignore){}
        }

    }
}
