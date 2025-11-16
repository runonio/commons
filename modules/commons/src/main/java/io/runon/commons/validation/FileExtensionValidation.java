package io.runon.commons.validation;

import io.runon.commons.utils.FileUtils;

import java.io.File;

/**
 * 파일 확장자 유효성 체크
 * @author macle
 */
public class FileExtensionValidation implements FileValidation{

    private final String extension;
    public FileExtensionValidation(String extension){
        this.extension =extension;
    }

    @Override
    public boolean isValid(File file) {
        if(extension.equals(FileUtils.getExtension(file))){
            return true;
        }else{
            return false;
        }

    }
}
