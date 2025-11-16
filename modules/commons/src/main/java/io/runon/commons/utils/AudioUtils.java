package io.runon.commons.utils;

import io.runon.commons.exception.IORuntimeException;
import io.runon.commons.exception.UnSupportedException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 * @author macle
 */
public class AudioUtils {

    public static long getDuration(String filePath){
        return getDuration(new File(filePath));
    }

    public static long getDuration(File file){
        AudioInputStream audioStream = null;
        try {
            audioStream = AudioSystem.getAudioInputStream(file);
        } catch (UnsupportedAudioFileException e) {
            throw new UnSupportedException(e);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
        AudioFormat format = audioStream.getFormat();
        long frames = audioStream.getFrameLength();

        // (1) 초 단위 계산
        double durationInSeconds = frames / format.getFrameRate();

        // (2) 밀리초 단위로 변환
        return Math.round(durationInSeconds * 1000);
    }

}
