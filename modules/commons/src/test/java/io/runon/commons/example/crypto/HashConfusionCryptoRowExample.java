package io.runon.commons.example.crypto;

import io.runon.commons.crypto.CharMap;
import io.runon.commons.crypto.CharMapManager;
import io.runon.commons.crypto.HashConfusionCryptos;

/**
 * @author macle
 */
public class HashConfusionCryptoRowExample {
    public static void main(String[] args) {

        //서비스가 켜질떄 한번만 호출
        CharMapManager.getInstance().setCharMap("http://127.0.0.1:31315/charmap/maps");

        //charmap 서비스 주소

        //rowid 는 String, int ,long 다가능 각 row 의 유니크한값 rownum 또는 ID
        String rowId = "rowId";
        CharMap charMap = CharMapManager.getInstance().getRowIdMap(rowId);

        String text =" test Text";

        //암호화
        String enc = HashConfusionCryptos.encStr(rowId, text, 32, charMap);
        System.out.println(enc);


        String dec =  HashConfusionCryptos.decStr(rowId, enc, 32, charMap);
        System.out.println(dec);

    }
}
