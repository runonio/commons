package io.runon.commons.example.crypto.file;

import io.runon.commons.config.Config;
import io.runon.commons.crypto.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class FileEncExample {


    public static void enc() throws IOException {

        //파일이름
        String inPath ="D:\\stt\\news.mp3";
        String outPath = "D:\\stt\\news_enc";



        File inFile = new File(inPath);
        File outFile = new File(outPath);

        String password = outFile.getName();

        byte [] fileBytes = Files.readAllBytes(inFile.toPath());

        byte [] encByte = Cryptos.encByte(fileBytes, CryptoType.SCM, password, 32);


        try(FileOutputStream fos = new FileOutputStream(outFile)) {
            fos.write(encByte);
            fos.flush();
            fos.getFD().sync();
        }



    }


    public static void dec() throws IOException {
        //파일이름
        String inPath ="D:\\stt\\news_enc";
        String outPath = "D:\\stt\\newdec.mp3";



        File inFile = new File(inPath);
        File outFile = new File(outPath);

        String password = inFile.getName();

        byte [] fileBytes = Files.readAllBytes(inFile.toPath());

        byte [] decByte = Cryptos.decByte(fileBytes, CryptoType.SCM, password, 32);


        try(FileOutputStream fos = new FileOutputStream(outFile)) {
            fos.write(decByte);
            fos.flush();
            fos.getFD().sync();
        }
    }

    public static void main(String[] args) throws IOException {

        // http://hostaddress/charmap/maps
        String hostAddress = Config.getConfig("crypto.charmap.address");
        CharMapManager.getInstance().setCharMap(hostAddress);

//        enc();
        dec();

    }
}
