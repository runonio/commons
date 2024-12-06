package io.runon.commons.example;

import io.runon.commons.utils.FileUtil;

import java.io.File;
import java.util.List;
/**
 * @author macle
 */
public class DirListExample {
    public static void main(String[] args) {
        List<File> dirList = FileUtil.getDirList("D:\\data");

        for(File dir : dirList){
            System.out.println(dir.getAbsolutePath());
        }
    }
}
