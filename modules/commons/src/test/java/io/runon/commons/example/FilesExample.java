package io.runon.commons.example;

import io.runon.commons.utils.FileUtil;
import io.runon.commons.validation.NumberNameFileValidation;

import java.io.File;

/**
 * @author macle
 */
public class FilesExample {
    public static void main(String[] args) {

        File[] files = FileUtil.getFiles("D:\\data\\candle", new NumberNameFileValidation(), FileUtil.SORT_NAME_LONG_DESC);
        for (File file : files){
            System.out.println(file.getName());
        }

    }
}
