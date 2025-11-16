package io.runon.commons.crypto;

import io.runon.commons.config.Config;
import io.runon.commons.utils.string.Strings;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * @author macle
 */
public class HashConfusionCryptoGcm implements HashConfusionCrypto{

    private int ivLength = Config.getInteger("crypto.gcm.iv.length",12);
    private int maxDummyLength = Config.getInteger("crypto.gcm.max.dummy.length",10);
    private int tagLength = Config.getInteger("crypto.gcm.tag.length",128);

    @Override
    public byte[] enc(String key, String hash, byte[] data, int keySize, CharMap charMap) {
        SecureRandom rnd = new SecureRandom();
        try {
            String hKey = HashConfusionString.get(hash, key, keySize);

            if(charMap != null){
                hKey = charMap.change(hKey);
            }

            int dummyLength = Strings.getStringInteger(hKey, 1, maxDummyLength);
            byte[] dummy= new byte[dummyLength];
            rnd.nextBytes(dummy);

            byte[] iv= new byte[ivLength];
            rnd.nextBytes(iv);

            byte[] keyBytes = Cryptos.makeKeyByte(hKey, keySize);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(tagLength, iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, spec);
            byte[] enc = cipher.doFinal(data);
            byte[] sum = new byte[dummy.length + iv.length + enc.length];
            System.arraycopy(dummy, 0, sum, 0, dummy.length);
            System.arraycopy(iv, 0, sum, dummy.length, iv.length);
            System.arraycopy(enc, 0, sum, dummy.length +iv.length, enc.length);

            return sum;

        }catch (Exception e){
            throw new CryptoException(e);
        }

    }

    @Override
    public byte[] dec(String key, String hash, byte[] data, int keySize, CharMap charMap) {
        try {
            // 복호화에 필요한 키 문자열 생성 (암호화와 동일한 방식)
            String hKey = HashConfusionString.get(hash, key, keySize);

            if (charMap != null) {
                hKey = charMap.change(hKey);
            }

            // dummyLength 계산 방식은 암호화와 동일해야 함
            int dummyLength = Strings.getStringInteger(hKey, 1, maxDummyLength);

            // IV 추출
            byte[] iv = new byte[ivLength];
            System.arraycopy(data, dummyLength, iv, 0, ivLength);

            // 암호문(enc + tag) 추출
            int encLength = data.length - dummyLength - ivLength;
            byte[] enc = new byte[encLength];
            System.arraycopy(data, dummyLength + ivLength, enc, 0, encLength);

            // 키 생성
            byte[] keyBytes = Cryptos.makeKeyByte(hKey, keySize);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

            // 복호화 수행
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(tagLength, iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, spec);

            return cipher.doFinal(enc);

        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    public void setIvLength(int ivLength) {
        this.ivLength = ivLength;
    }

    public void setMaxDummyLength(int maxDummyLength) {
        this.maxDummyLength = maxDummyLength;
    }

    public void setTagLength(int tagLength) {
        this.tagLength = tagLength;
    }

}
