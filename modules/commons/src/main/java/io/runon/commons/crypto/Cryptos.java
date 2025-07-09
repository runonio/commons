package io.runon.commons.crypto;

import java.nio.charset.StandardCharsets;
/**
 * @author macle
 */
public class Cryptos {
    public static byte [] makeKeyByte(String key, int size){
        byte[] keyBytes= new byte[size];
        byte[] b= key.getBytes(StandardCharsets.UTF_8);
        int len= b.length;
        if (len > keyBytes.length) len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);

        return keyBytes;
    }


    public static void main(String[] args) {
        int a = 1;
        Object b =a;
        System.out.println(b.getClass() == int.class );
    }


}
