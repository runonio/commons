package io.runon.system;

import com.google.gson.JsonArray;
import io.runon.commons.config.Config;
import io.runon.commons.crypto.CryptoType;
import io.runon.commons.crypto.Cryptos;
import io.runon.commons.exception.IORuntimeException;
import io.runon.commons.utils.FileUtil;
import io.runon.commons.utils.GsonUtils;
import io.runon.jdbc.Database;
import io.runon.jdbc.JdbcQuery;
import io.runon.jdbc.objects.JdbcObjects;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * 저장소 파일
 * @author macle
 */
public class StorageFiles {

    public static final int SPLIT_SIZE = 1024*1024* Config.getInteger("file.split.size.mb", 300);

    public static void saveFile(StorageFile storageFile, String sequenceName, String sequencePreFix){

        storageFile.setSha256();
        storageFile.setFileType(StorageFileType.F);
        FilePathType filePathType = storageFile.getFilePathType();
        if (filePathType == FilePathType.DB) {
            //DB 저장이면
            if(storageFile.fileBytes.length > SPLIT_SIZE){
                saveSplit(storageFile, sequenceName, sequencePreFix);
                return;
            }
            storageFile.setFileBytes(Cryptos.encByte(storageFile.getFileBytes(), storageFile.getEncryptType(), storageFile.getFileId(), Config.getInteger("crypto.default.key.size", 32)));
            storageFile.setSplitType("single");
            storageFile.setSplitInfo(null);
            JdbcObjects.insertOrUpdate(storageFile);

        } else {
            //경로 저장이면
            storageFile.setFileBytes(Cryptos.encByte(storageFile.getFileBytes(), storageFile.getEncryptType(), storageFile.getFileId(), Config.getInteger("crypto.default.key.size", 32)));

            String filePath = storageFile.getFilePath();
            FileUtil.mkdirsParent(filePath);
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(storageFile.getFileBytes());
                fos.flush();
                fos.getFD().sync();
            } catch (IOException e) {

                return;
            }
            //DB에 저장하지 않으므로 null 입력
            storageFile.setFileBytes(null);
            JdbcObjects.insertOrUpdate(storageFile);
        }
    }

    /**
     * 같은 네트워크 드라이버일때 파일을 경로체로 이동시킨다. storge 관리체계에서 관리되는 형태로 저장한다.
     */
    public static void saveDir(StorageFile storageFile, String copyDir){
        storageFile.setFileType(StorageFileType.D);
        Cryptos.copyDec(copyDir, storageFile.getFilePath(), storageFile.getEncryptType(), storageFile.getFileId(), Config.getInteger("crypto.default.key.size", 32));
        JdbcObjects.insertOrUpdate(storageFile);
    }

    /**
     * 파일분할저장
     */
    public static void saveSplit(StorageFile storageFile, String sequenceName, String sequencePreFix){


        byte [] bytes = storageFile.fileBytes;
        storageFile.setSplitType("split_m");
        if(storageFile.fileId == null){
            storageFile.fileId = sequencePreFix + Database.nextLong(sequenceName);
        }

        byte [] splitBytes = new byte[SPLIT_SIZE];
        System.arraycopy(bytes, 0, splitBytes, 0, splitBytes.length);
        storageFile.setFileBytes(splitBytes);
        storageFile.setFileBytes(Cryptos.encByte(storageFile.getFileBytes(), storageFile.getEncryptType(), storageFile.getFileId(), Config.getInteger("crypto.default.key.size", 32)));
        JsonArray splitIds = new JsonArray();

        int start = SPLIT_SIZE;

        for(;;){
            StorageFile splitFile = new StorageFile();
            splitFile.setFileId( sequencePreFix + Database.nextLong(sequenceName));
            splitFile.setSplitType("split_s");
            splitFile.setSplitInfo(storageFile.getFileId());
            splitIds.add(splitFile.getFileId());

            int end =  start + SPLIT_SIZE;

            boolean isBreak = false;

            if(end >= bytes.length){
                end = bytes.length;
                isBreak = true;
            }

            byte [] subSplitBytes = new byte[end  - start];
            System.arraycopy(bytes, start, subSplitBytes, 0, subSplitBytes.length);

            splitFile.setFileBytes(subSplitBytes);
            splitFile.setFileBytes(Cryptos.encByte(splitFile.getFileBytes(), splitFile.getEncryptType(), splitFile.getFileId(), Config.getInteger("crypto.default.key.size", 32)));
            JdbcObjects.insert(splitFile);
            //분할정보 저장
            if(isBreak){
                break;
            }
            start = end;

        }

        //마스터파일에 분할정보 기록
        storageFile.setSplitInfo(splitIds.toString());
        JdbcObjects.insertOrUpdate(storageFile);
    }


    public static void out(StorageFile storageFile, String outPath){

        FilePathType filePathType = storageFile.getFilePathType();
        if (filePathType == FilePathType.DB) {
            if(storageFile.getSplitType().startsWith("split")){
                outFileSplit(storageFile, outPath);
                return;
            }
            outBytes(storageFile.getFileId(), storageFile.fileBytes, outPath, storageFile.getEncryptType(), false);

        }else{
            if(storageFile.getFileType() == StorageFileType.F) {
                //파일유형이 파일이면
                //저장 경로이면
                Cryptos.copyFileDec(storageFile.getFilePath(), outPath, storageFile.getEncryptType(), storageFile.getFileId(), Config.getInteger("crypto.default.key.size", 32));

            }else{
                //파일유형이 디렉토리 이면
                //관련 경로에 있는 파일을 전부 복화화 해서 저장
                Cryptos.copyDec(storageFile.getFilePath(), outPath, storageFile.getEncryptType(), storageFile.getFileId(), Config.getInteger("crypto.default.key.size", 32));

            }
        }
    }

    public static StorageFile getStorageFile(String fileId){
       return JdbcObjects.getObj(StorageFile.class, "file_id='" + fileId +"'");
    }

    public static String makeExtension(StorageFile storageFile, String path){
        try {

            int index = path.lastIndexOf(".");
            if(index > 0){
                return path;
            }

            String originalName = storageFile.getFileName();
            String extension = FileUtil.getExtension(originalName);
            path = path + "." + extension;
        } catch (Exception ignore) {}
        return path;
    }


    public static CryptoType getEncryptType(String fileId){
        String value = JdbcQuery.getResultOne("select encrypt_type from file where file_id='" + fileId +"'");
        return CryptoType.valueOf(value);
    }


    public static void outFileSplit(StorageFile storageFile, String outPath)  {

        if(storageFile.getSplitType().equals("split_s")){
            //마스터 파일 정보로 가져오기
            storageFile = JdbcObjects.getObj(StorageFile.class, "file_id='" + storageFile.getSplitInfo() +"'");
        }

        //마스터 파일 내리기
        outBytes(storageFile.getFileId(), storageFile.fileBytes, outPath,  storageFile.getEncryptType(), false);

        JsonArray jsonArray = GsonUtils.fromJsonArray(storageFile.splitInfo);

        for (int i = 0; i <jsonArray.size() ; i++) {
            String fileId = jsonArray.get(i).getAsString();
            byte [] bytes = JdbcQuery.getResultBytes("select file_bytes from file where file_id='" + fileId +"'");
            outBytes(fileId, bytes, outPath, storageFile.getEncryptType(), true);
        }
    }


    public static void outBytes(String fileId, byte [] bytes, String outPath, CryptoType cryptoType, boolean isAppend) {
        bytes = Cryptos.decByte(bytes, cryptoType, fileId,Config. getInteger("crypto.default.key.size", 32));
        try(FileOutputStream fos = new FileOutputStream(outPath, isAppend)) {

            fos.write(bytes);
            fos.flush();
            fos.getFD().sync();
        }catch (IOException e){
            throw new IORuntimeException(e);
        }
    }

}
