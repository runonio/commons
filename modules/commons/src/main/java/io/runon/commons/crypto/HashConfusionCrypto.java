package io.runon.commons.crypto;
/**
 * @author macle
 */
public interface HashConfusionCrypto {

    enum Type{
        CBC
        , GCM
    }


    byte [] enc(String key, String hash, byte [] data, int keySize, CharMap charMap);

    byte [] dec(String key, String hash, byte [] data, int keySize, CharMap charMap);
}