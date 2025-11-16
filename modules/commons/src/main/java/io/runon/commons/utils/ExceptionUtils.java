
package io.runon.commons.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.slf4j.Logger;

import io.runon.commons.handler.ExceptionHandler;

/**
 * 예외처리 관련 유틸
 * @author macle
 */
public class ExceptionUtils {
	/**
	 *  Exception.printStackTrace 내용을 String 으로 가져오기
	 * @param e Exception e
	 * @return String stackTrace String Value
	 */
	public static  String getStackTrace(Exception e){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
    	PrintStream printStream = new PrintStream(out);
    	e.printStackTrace(printStream);
    	String exceptionStackTrace = out.toString();

		//noinspection CatchMayIgnoreException
		try{out.close();}catch(Exception e1){}
		//noinspection CatchMayIgnoreException
        try{printStream.close();}catch(Exception e1){}
        
        return exceptionStackTrace;
	}
	
	/**
	 * 기본 예외처리
	 * @param e Exception
	 * @param logger Logger
	 * @param exceptionHandler ExceptionHandler
	 */
	public static  void exception(Exception e, Logger logger, ExceptionHandler exceptionHandler){
		if(exceptionHandler == null) {
			logger.error(getStackTrace(e));
		}else{
			exceptionHandler.exception(e);
		}
	}
	
}