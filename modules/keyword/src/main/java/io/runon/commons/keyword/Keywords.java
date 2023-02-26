package io.runon.commons.keyword;

import com.seomse.jdbc.JdbcQuery;
import com.seomse.jdbc.objects.JdbcObjects;

import java.security.MessageDigest;
import java.util.List;

/**
 * @author macle
 */
public class Keywords {


    public static String checksum(String keyword, String dataValue){
        try {
            String keywordData;
            if (dataValue == null) {
                keywordData = keyword;
            } else {
                keywordData = keyword + "," + dataValue;
            }

            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(keywordData.getBytes());
            byte[] byteData = messageDigest.digest();

            StringBuilder sb = new StringBuilder();
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static Long getNumber(String keyword, String dataValue){
        String checkSum = checksum(keyword, dataValue);
        return JdbcQuery.getResultLong("select keyword_no from keyword where checksum='" + checkSum + "'");
    }

    public static Keyword save(String keywordText, String dataValue){
        String checkSum = checksum(keywordText, dataValue);
        List<Keyword> keywordList = JdbcObjects.getObjList(Keyword.class, "checksum='" + checkSum + "'");


        if(keywordList.size() > 0){
            for(Keyword keyword : keywordList){
                if(keyword.equals(keywordText, dataValue)){
                    return keyword;
                }
            }

        }

        Keyword keyword = new Keyword();
        keyword.keyword = keywordText;
        keyword.dataValue = dataValue;
        keyword.checksum = checkSum;
        keyword.time = System.currentTimeMillis();
        JdbcObjects.insert(keyword);

        return keyword;
    }




    public static void main(String[] args) {
//        String text = checksum("증시", null);
//        System.out.println(text);
//        System.out.println("4d7a3f10a328d0077dc2ed1f2c174d24".length());

        System.out.println(save("증시", null));
    }
}
