package io.runon.commons.example.crypto;

import io.runon.commons.crypto.HashConfusionString;

import java.security.NoSuchAlgorithmException;

/**
 * @author macle
 */
public class HashConfusionStringExample {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String hKey = HashConfusionString.get("MD5", "1", 32);
        System.out.println(hKey);
        System.out.println(hKey.length());
    }

}
