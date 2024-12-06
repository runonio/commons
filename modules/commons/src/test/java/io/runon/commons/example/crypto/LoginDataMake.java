package io.runon.commons.example.crypto;

import io.runon.commons.crypto.CharMap;
import io.runon.commons.crypto.LoginCrypto;

/**
 * @author macle
 */
public class LoginDataMake {
    public static void main(String[] args) {

        CharMap charMap = new CharMap(CharMap.makeRandomMap());

        String [] infos = LoginCrypto.encryption("id", "password", 32, charMap);
        System.out.println(infos[0]);
        System.out.println(infos[1]);

        infos = LoginCrypto.decryption(infos[0], infos[1], 32 , charMap);

        System.out.println(infos[0]);
        System.out.println(infos[1]);

    }
}
