package io.runon.commons.crypto;

import io.runon.commons.config.Config;
import io.runon.commons.exception.IORuntimeException;
import io.runon.commons.utils.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author macle
 */
public class Cryptos {


    public static String getHashText(byte [] bytes, String algorithm) throws NoSuchAlgorithmException {
        StringBuilder builder = new StringBuilder();
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte [] hashBytes = md.digest(bytes);

        for (byte b : hashBytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public static byte [] makeKeyByte(String key, int size){
        byte[] keyBytes= new byte[size];
        byte[] b= key.getBytes(StandardCharsets.UTF_8);
        int len= b.length;
        if (len > keyBytes.length) len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);

        return keyBytes;
    }

    public static byte[] encByte(byte [] bytes, Object keyObj){
        CryptoType cryptoType =  CryptoType.valueOf(Config.getConfig("crypto.default.type", "SCM"));
        int keySize = Config.getInteger("crypto.default.key.size", 32);
        return encByte(bytes, cryptoType, keyObj, keySize);
    }


    public static byte[] encByte(byte [] bytes, CryptoType cryptoType, Object keyObj,  int keySize){
        String key = keyObj.toString();

        if(cryptoType == CryptoType.Y){
            return HashConfusionCrypto.enc(key, bytes, keySize, null);
        }else if(cryptoType == CryptoType.SCM){
            CharMap charMap = CharMapManager.getInstance().getRowIdMap(keyObj);
            return HashConfusionCrypto.enc(key, bytes, keySize, charMap);
        }

        return bytes;
    }

    public static String encStr(String str, Object keyObj){
        CryptoType cryptoType =  CryptoType.valueOf(Config.getConfig("crypto.default.type", "SCM"));
        int keySize = Config.getInteger("crypto.default.key.size", 32);
        return encStr(str, cryptoType, keyObj, keySize);
    }

    public static String encStr(String str, CryptoType cryptoType, Object keyObj, int keySize){
        String key = keyObj.toString();

        if(cryptoType == CryptoType.Y){
            return HashConfusionCrypto.encStr(key, str, keySize, null);
        }else if(cryptoType == CryptoType.SCM){
            CharMap charMap = CharMapManager.getInstance().getRowIdMap(keyObj);
            return HashConfusionCrypto.encStr(key, str, keySize, charMap);
        }
        return str;
    }

    public static byte[] decByte(byte [] bytes, Object keyObj){
        CryptoType cryptoType =  CryptoType.valueOf(Config.getConfig("crypto.default.type", "SCM"));
        int keySize = Config.getInteger("crypto.default.key.size", 32);
        return decByte(bytes, cryptoType, keyObj, keySize);
    }

    public static byte[] decByte(byte [] bytes, CryptoType cryptoType, Object keyObj,  int keySize){
        String key = keyObj.toString();
        if(cryptoType == io.runon.commons.crypto.CryptoType.Y){
            return HashConfusionCrypto.dec(key, bytes, keySize, null);
        }else if(cryptoType == io.runon.commons.crypto.CryptoType.SCM){
            CharMap charMap = CharMapManager.getInstance().getRowIdMap(keyObj);
            return HashConfusionCrypto.dec(key, bytes, keySize, charMap);
        }

        return bytes;
    }

    public static String decStr(String str, Object keyObj){
        CryptoType cryptoType =  CryptoType.valueOf(Config.getConfig("crypto.default.type", "SCM"));
        int keySize = Config.getInteger("crypto.default.key.size", 32);
        return decStr(str, cryptoType, keyObj, keySize);
    }

    public static String decStr(String str, CryptoType cryptoType, Object keyObj,  int keySize){
        String key = keyObj.toString();

        if(cryptoType == CryptoType.Y){
            return HashConfusionCrypto.decStr(key, str, keySize, null);
        }else if(cryptoType == CryptoType.SCM){
            CharMap charMap = CharMapManager.getInstance().getRowIdMap(keyObj);
            return HashConfusionCrypto.decStr(key, str, keySize, charMap);
        }
        return str;
    }

    /**
     * 복호화 하여저장
     */
    public static boolean copyDec(String inPath, String outPath, CryptoType cryptoType, Object keyObject, int keySize){
        File file = new File(inPath);
        if(!file.exists()){
            return false;
        }

        String filePath = file.getAbsolutePath();

        if(file.isDirectory()){
            FileUtil.mkdirs(outPath);
            //하위까지 전체복사
            List<File> fileList = FileUtil.getFileList(inPath);
            for(File subFile : fileList){
                String subPath = subFile.getAbsolutePath();
                String newPath = subPath.substring(filePath.length());
                newPath = outPath + "/" + newPath;


                File newFile = new File(newPath);
                //noinspection ResultOfMethodCallIgnored
                newFile.getParentFile().mkdirs();
                if(subFile.isDirectory())//noinspection SingleStatementInBlock
                {
                    //noinspection ResultOfMethodCallIgnored
                    newFile.mkdir();
                }else{
                    copyFileDec(subPath, newPath, cryptoType, keyObject, keySize);
                }

            }
            fileList.clear();

        }else{
            return copyFileDec(inPath, outPath, cryptoType, keyObject, keySize);
        }
        return true;
    }


    public static boolean copyFileDec(String inFilePath, String outFilePath, CryptoType cryptoType, Object keyObject, int keySize){

       if(!FileUtil.isFile(inFilePath))
            return false;
        try {
            if(cryptoType == CryptoType.N){
                FileUtil.fileCopy(inFilePath, outFilePath);
                return true;
            }

            byte[] bytes = Files.readAllBytes(new File(inFilePath).toPath());
            bytes = Cryptos.decByte(bytes, cryptoType, keyObject, keySize);
            try (FileOutputStream fos = new FileOutputStream(outFilePath, false)) {
                fos.write(bytes);
                fos.flush();
                fos.getFD().sync();
            }
            return true;
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }

    }



}
