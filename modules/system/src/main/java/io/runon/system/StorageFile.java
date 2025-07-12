package io.runon.system;


import io.runon.commons.crypto.Cryptos;
import io.runon.commons.utils.ExceptionUtil;
import io.runon.jdbc.annotation.Column;
import io.runon.jdbc.annotation.Table;
import io.runon.jdbc.annotation.PrimaryKey;
import io.runon.jdbc.annotation.DateTime;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


/**
 * 저장소 파일
 * @author macle
 */
@Slf4j
@Data
@Table(name="file")
public class StorageFile {

    @PrimaryKey(seq = 1)
    @Column(name = "file_id")
    String fileId;

    @Column(name = "file_name")
    String fileName;

    @Column(name = "file_bytes")
    byte [] fileBytes;

    @Column(name = "sha256")
    String sha256;

    @Column(name = "split_type")
    String splitType = "single";

    @Column(name = "split_info")
    String splitInfo;


    @Column(name = "encrypt_type")
    String encryptType ="SCM";

    @Column(name = "file_path_type")
    String filePathType;

    @Column(name = "file_path")
    String filePath;

    @Column(name = "meta_data")
    String metaData;

    @DateTime
    @Column(name = "updated_at")
    long time = System.currentTimeMillis();


    public void setSha256(){
        try {
            sha256 = Cryptos.getHashText(fileBytes, "SHA-256");
        }catch (Exception e){
            log.error(ExceptionUtil.getStackTrace(e));
        }
    }
}
