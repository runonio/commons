package io.runon.commons.crypto;

import io.runon.commons.config.Config;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author macle
 */
public class HashConfusionCryptos {

    public static final String DEFAULT_HASH =  Config.getConfig("crypto.default.key.hash", "MD5");

    public static final HashConfusionCryptoGcm GCM = new HashConfusionCryptoGcm();

    public static final HashConfusionCryptoCbc CBC = new HashConfusionCryptoCbc();

    private static final HashConfusionCrypto DEFAULT = makeDefault();

    private static  HashConfusionCrypto makeDefault(){
        HashConfusionCrypto.Type type = HashConfusionCrypto.Type.valueOf(Config.getConfig("crypto.hash.confusion.type", "GCM"));
        if(type == HashConfusionCrypto.Type.GCM){
            return GCM;
        }else{
            return CBC;
        }
    }


    public static String encStr(String key, String str){
        return encStr(key, str, Config.getInteger("crypto.default.key.size", 32), null);
    }


    public static String encStr(int key, String str, int keySize, CharMap charMap){
        return encStr(Integer.toString(key), str, keySize, charMap);
    }

    public static String encStr(long key, String str, int keySize, CharMap charMap){

        return encStr(Long.toString(key), str, keySize, charMap);
    }


    public static String encStr(String key, String str, int keySize, CharMap charMap){
        try{
            byte [] data = str.getBytes(StandardCharsets.UTF_8);
            byte [] encData = enc(key, data, keySize, charMap);
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(encData);
        }catch (Exception e){
            throw new CryptoException(e);
        }
    }

    public static String decStr(String key, String str){
        return decStr(key, str, Config.getInteger("crypto.default.key.size", 32), null);
    }


    public static String decStr(int key, String str, int keySize, CharMap charMap){
        return decStr(Integer.toString(key), str, keySize, charMap);
    }

    public static String decStr(long key, String str, int keySize, CharMap charMap){
        return decStr(Long.toString(key), str, keySize, charMap);
    }

    public static String decStr(String key, String encStr, int keySize, CharMap charMap){
        try{
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] encByte = decoder.decode(encStr);

//            byte[] keyBytes = Cryptos.makeKeyByte(key, keySize);
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
//            IvParameterSpec ivSpec;
//            if (keySize == 16) {
//                ivSpec = new IvParameterSpec(keyBytes);
//            } else {
//                ivSpec = new IvParameterSpec(Cryptos.makeKeyByte(key, 16));
//            }
//
//            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
//
//            byte[] results = cipher.doFinal(encByte);
//            return new String(results, StandardCharsets.UTF_8);


//
            byte [] data = dec(key, encByte, keySize, charMap);
            return new String(data, StandardCharsets.UTF_8);
        }catch (Exception e){
            throw new CryptoException(e);
        }
    }

    public static byte [] enc(String key, byte [] data){

        return enc(key, data, Config.getInteger("crypto.default.key.size", 32), null);
    }

    public static byte [] enc(String key, byte [] data, int keySize, CharMap charMap) {
        return  DEFAULT.enc(key, DEFAULT_HASH, data, keySize, charMap);

    }

    public static  byte [] dec(String key, byte [] data) {
        return dec(key, data, Config.getInteger("crypto.default.key.size", 32), null);
    }

    public static  byte [] dec(String key, byte [] data, int keySize, CharMap charMap){
        return  DEFAULT.dec(key, DEFAULT_HASH, data, keySize, charMap);
    }



}
