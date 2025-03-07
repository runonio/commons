

package io.runon.commons.example;

import io.runon.commons.utils.FileUtil;
import io.runon.commons.utils.time.TimeUtil;

/**
 * 인메모리 검색 분석 엔진에서 
 * 파엘에 저장된 상세 정보를 가져올때 사용하려고 core 기술 개발중 빠른 line 텍스트 성능 테스트
 * @author macle
 */
public class FileReadLineNumberSpeedTest {
    public static void main(String[] args) {


        String testFilePath = "D:\\seomse\\index\\20200201\\index_0.md";
        //200 라인의 파일을 사용
		for (int i = 0; i <200 ; i++) {
            //일치여부 테스트
			if(!FileUtil.getLineNio(testFilePath, i).equals(FileUtil.getLine(testFilePath, i))){
				System.out.println(i);
			}
		}

        long startTime = System.currentTimeMillis();
		for (int i = 0; i <500 ; i++) {
            FileUtil.getLineNio(testFilePath, 50);
		}
        System.out.println("line value Nio 속도: " + TimeUtil.getSecond(System.currentTimeMillis()-startTime));
        startTime = System.currentTimeMillis();
        for (int i = 0; i <500 ; i++) {
            FileUtil.getLine(testFilePath, 50);
        }
        System.out.println("line value core 기술 구현체 속도: " + TimeUtil.getSecond(System.currentTimeMillis()-startTime));

        startTime = System.currentTimeMillis();
        for (int i = 0; i <500 ; i++) {
            FileUtil.getLineCountNio(testFilePath);
        }
        System.out.println("line count  Nio 속도: " + TimeUtil.getSecond(System.currentTimeMillis()-startTime) + " " + FileUtil.getLineCountNio(testFilePath));

        startTime = System.currentTimeMillis();
        for (int i = 0; i <500 ; i++) {
            FileUtil.getLineCount(testFilePath);
		}
        System.out.println("line count core 기술 구현체 속도: " + TimeUtil.getSecond(System.currentTimeMillis()-startTime) +" " + FileUtil.getLineCount(testFilePath));

    }
}
