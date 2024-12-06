package io.runon.commons.example.crypto;

import io.runon.commons.crypto.StringCrypto;
/**
 * @author macle
 */
public class StringCryptoExample {
    public static void main(String[] args) {
        StringCrypto stringCrypto = new StringCrypto();
//        stringCrypto.setKeySize(32);
//        stringCrypto.setCharMap(new CharMap(CharMap.makeRandomMap()));

        String enc = stringCrypto.enc("test");

        System.out.println(enc);

        System.out.println(stringCrypto.dec(enc));


    }
}
