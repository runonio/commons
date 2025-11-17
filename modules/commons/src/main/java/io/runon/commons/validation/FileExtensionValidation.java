package io.runon.commons.validation;

import io.runon.commons.utils.FileUtils;

import java.io.File;

/**
 * 파일 확장자 유효성 체크
 * @author macle
 */
public class FileExtensionValidation implements FileValidation{

    private final String[] extensions;
    public FileExtensionValidation(String ... extensions){
        this.extensions = extensions;
    }


    @Override
    public boolean isValid(File file) {

        String fileExtension = FileUtils.getExtension(file);

        for(String extension : extensions){
            if(extension.equals(fileExtension)){
                return true;
            }
        }

        return false;

    }
}
