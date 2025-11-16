package io.runon.commons.example;

import io.runon.commons.utils.FileUtils;

import java.io.File;
import java.util.List;
/**
 * @author macle
 */
public class DirListExample {
    public static void main(String[] args) {
        List<File> dirList = FileUtils.getDirList("D:\\data");

        for(File dir : dirList){
            System.out.println(dir.getAbsolutePath());
        }
    }
}
