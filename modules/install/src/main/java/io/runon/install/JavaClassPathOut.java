package io.runon.install;

import io.runon.install.utils.FileUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * java -cp @classpath.txt mainclass
 * 에서의 classpath.txt 부분의 내용을 작성해서 내린다
 * 다른 라이브러리를 최대한 참조하지 않기위해 직접 구현한 구현체 위주로 사용해야한다.
 * @author macle
 */
public class JavaClassPathOut {


    private String dirSeparator ="/";

    private String classesDirName  ="classes";

    private String dependenciesDirName  ="dependencies";


    private String outFileName = "classpath_all";


    public JavaClassPathOut(){

    }

    public void setDirSeparator(String dirSeparator) {
        this.dirSeparator = dirSeparator;
    }

    public void setDependenciesDirName(String dependenciesDirName) {
        this.dependenciesDirName = dependenciesDirName;
    }

    public void setClassesDirName(String classesDirName) {
        this.classesDirName = classesDirName;
    }

    public void setOutFileName(String outFileName) {
        this.outFileName = outFileName;
    }


    public void out(String dirPath){
        String classesPath = dirPath + dirSeparator +classesDirName;
        String dependenciesPah = dirPath  + dirSeparator + dependenciesDirName;
        String outPath = dirPath + dirSeparator + outFileName;
        out(classesPath, dependenciesPah, outPath);
    }

    public static void out(String classesPath, String dependenciesPah, String outPath) {
        StringBuilder sb = new StringBuilder();
        if (classesPath != null) {
            sb.append(classesPath).append(";");
        }

        File[] files = new File(dependenciesPah).listFiles();


        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    continue;
                }

                if (!file.getName().endsWith(".jar")) {
                    continue;
                }

                sb.append(file.getAbsolutePath()).append(";");
            }
        }
        FileUtils.fileOutput(sb.toString(), outPath, false);
    }


    public static void out(){
        JavaClassPathOut javaClassPathOut = new JavaClassPathOut();
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("win")){
            javaClassPathOut.setDirSeparator("\\");
        }
        Path currentPath = Paths.get("");
        String path = currentPath.toAbsolutePath().toString();
        javaClassPathOut.out(path);
    }

    public static void main(String[] args) {
        out();
    }
}
