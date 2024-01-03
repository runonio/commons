package io.runon.install.utils;

import io.runon.install.KeyValue;

/**
 * @author macle
 */
public class XmlUtils {

    public static KeyValue getKeyValue(String line){
        String match2 = "\\s{2,}";
        line = line.replaceAll(match2, " ");

        String keyStartSearch = "<entry key=\"";

        int index = line.indexOf(keyStartSearch);
        if(index < 0){
            return null;
        }
        String keyEndSearch = "\">";

        int keyStartIndex = index + keyStartSearch.length();

        index = line.indexOf(keyEndSearch, keyStartIndex);
        if(index < 0){
            return null;
        }

        String key = line.substring(keyStartIndex, index);

        int valueStartIndex = index + keyEndSearch.length();

        String entryEndSearch = "</entry>";

        index = line.indexOf(entryEndSearch, valueStartIndex);
        if(index < 0){
            return null;
        }

        String value = line.substring(valueStartIndex, index);

        return new KeyValue(key, value);
    }

    public static void appendLine(StringBuilder sb, String key, String value){
        sb.append("<entry key=\"").append(key).append("\">").append(value).append("</entry>");
    }



}
