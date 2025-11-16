package io.runon.commons.example;

import io.runon.commons.utils.FileUtils;
import io.runon.commons.validation.NumberNameFileValidation;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * @author macle
 */
public class FileLinesExample {
    public static void main(String[] args) {
        File file = new File("D:\\data\\cryptocurrency\\futures\\candle\\BTCUSDT\\5m");

        String [] lines = FileUtils.getLines(file, StandardCharsets.UTF_8,new NumberNameFileValidation(), FileUtils.SORT_NAME_LONG,150000);
        for(String line : lines){
            System.out.println(line);
        }

        System.out.println(lines.length);
    }
}
