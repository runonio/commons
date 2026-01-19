package io.runon.commons.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author macle
 */
public class HashConfusionCryptoCbc implements HashConfusionCrypto {



    @Override
    public byte [] enc(String key, String hash, byte [] data, int keySize, CharMap charMap) {

        try {
            String hKey = HashConfusionString.get(hash, key);

            if(charMap != null){
                hKey = charMap.change(hKey);
            }

            byte[] keyBytes = Cryptos.makeKeyByte(hKey, keySize);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

            IvParameterSpec ivSpec;
            if(keySize == 16){
                ivSpec = new IvParameterSpec(keyBytes);
            }else{
                ivSpec = new IvParameterSpec(Cryptos.makeKeyByte(hKey, 16));
            }

            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            return  cipher.doFinal(data);
        }catch (Exception e){
            throw new CryptoException(e);
        }
    }

    @Override
    public byte [] dec(String key, String hash, byte [] data, int keySize, CharMap charMap){
        try {
            String hKey = HashConfusionString.get(hash, key);

            if(charMap != null){
                hKey = charMap.change(hKey);
            }

            byte[] keyBytes = Cryptos.makeKeyByte(hKey, keySize);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivSpec;
            if(keySize == 16){
                ivSpec = new IvParameterSpec(keyBytes);
            }else{
                ivSpec = new IvParameterSpec(Cryptos.makeKeyByte(hKey, 16));
            }
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            return cipher.doFinal(data);

        }catch (Exception e){
            throw new CryptoException(e);
        }
    }

}
