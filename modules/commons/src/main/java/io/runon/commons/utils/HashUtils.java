
package io.runon.commons.utils;

import java.security.NoSuchAlgorithmException;

/**
 * Hash 관련 유틸
 * @author macle
 */
public class HashUtils {
	

	/**
	 * 특정해쉬알고리즘의 문자열 값 얻기
	 * @param hash String "MD5","SHA1","SHA-256","SHA-384","SHA-512"
	 * @param value String hash target value
	 * @return String hash
	 * @throws NoSuchAlgorithmException NoSuchAlgorithmException
	 */
	public static String getHash(String hash, String value) throws NoSuchAlgorithmException{
		return  getHash(java.security.MessageDigest.getInstance(hash), value);
	}
	
	/**
	 * 특정해쉬알고리즘의 문자열 값 얻기
	 * @param messageDigest MessageDigest 알고리즘
	 * @param value String hash target
	 * @return String hash
	 */
	public static String getHash(java.security.MessageDigest messageDigest, String value){
		messageDigest.update(value.getBytes());
		byte[] byteData = messageDigest.digest();
		StringBuilder sb = new StringBuilder();
		//noinspection ForLoopReplaceableByForEach
		for(int i = 0 ; i < byteData.length ; i++){
			sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}
}
