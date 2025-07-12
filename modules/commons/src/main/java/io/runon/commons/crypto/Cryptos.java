package io.runon.commons.crypto;

import io.runon.commons.config.Config;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author macle
 */
public class Cryptos {

    public static String getHashText(byte [] bytes, String algorithm) throws NoSuchAlgorithmException {
        StringBuilder builder = new StringBuilder();
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte [] hashBytes = md.digest(bytes);

        for (byte b : hashBytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public static byte [] makeKeyByte(String key, int size){
        byte[] keyBytes= new byte[size];
        byte[] b= key.getBytes(StandardCharsets.UTF_8);
        int len= b.length;
        if (len > keyBytes.length) len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);

        return keyBytes;
    }

    public static byte[] encByte(byte [] bytes, Object keyObj){
        CryptoType cryptoType =  CryptoType.valueOf(Config.getConfig("crypto.default.type", "SCM"));
        int keySize = Config.getInteger("crypto.default.key.size", 32);
        return encByte(bytes, cryptoType, keyObj, keySize);
    }


    public static byte[] encByte(byte [] bytes, CryptoType cryptoType, Object keyObj,  int keySize){
        String key = keyObj.toString();

        if(cryptoType == CryptoType.Y){
            return HashConfusionCrypto.enc(key, bytes, keySize, null);
        }else if(cryptoType == CryptoType.SCM){
            CharMap charMap = CharMapManager.getInstance().getRowIdMap(keyObj);
            return HashConfusionCrypto.enc(key, bytes, keySize, charMap);
        }

        return bytes;
    }

    public static String encStr(String str, Object keyObj){
        CryptoType cryptoType =  CryptoType.valueOf(Config.getConfig("crypto.default.type", "SCM"));
        int keySize = Config.getInteger("crypto.default.key.size", 32);
        return encStr(str, cryptoType, keyObj, keySize);
    }

    public static String encStr(String str, CryptoType cryptoType, Object keyObj, int keySize){
        String key = keyObj.toString();

        if(cryptoType == CryptoType.Y){
            return HashConfusionCrypto.encStr(key, str, keySize, null);
        }else if(cryptoType == CryptoType.SCM){
            CharMap charMap = CharMapManager.getInstance().getRowIdMap(keyObj);
            return HashConfusionCrypto.encStr(key, str, keySize, charMap);
        }
        return str;
    }

    public static byte[] decByte(byte [] bytes, Object keyObj){
        CryptoType cryptoType =  CryptoType.valueOf(Config.getConfig("crypto.default.type", "SCM"));
        int keySize = Config.getInteger("crypto.default.key.size", 32);
        return decByte(bytes, cryptoType, keyObj, keySize);
    }

    public static byte[] decByte(byte [] bytes, CryptoType cryptoType, Object keyObj,  int keySize){
        String key = keyObj.toString();
        if(cryptoType == io.runon.commons.crypto.CryptoType.Y){
            return HashConfusionCrypto.dec(key, bytes, keySize, null);
        }else if(cryptoType == io.runon.commons.crypto.CryptoType.SCM){
            CharMap charMap = CharMapManager.getInstance().getRowIdMap(keyObj);
            return HashConfusionCrypto.dec(key, bytes, keySize, charMap);
        }

        return bytes;
    }

    public static String decStr(String str, Object keyObj){
        CryptoType cryptoType =  CryptoType.valueOf(Config.getConfig("crypto.default.type", "SCM"));
        int keySize = Config.getInteger("crypto.default.key.size", 32);
        return decStr(str, cryptoType, keyObj, keySize);
    }

    public static String decStr(String str, CryptoType cryptoType, Object keyObj,  int keySize){
        String key = keyObj.toString();

        if(cryptoType == CryptoType.Y){
            return HashConfusionCrypto.decStr(key, str, keySize, null);
        }else if(cryptoType == CryptoType.SCM){
            CharMap charMap = CharMapManager.getInstance().getRowIdMap(keyObj);
            return HashConfusionCrypto.decStr(key, str, keySize, charMap);
        }
        return str;
    }

}
