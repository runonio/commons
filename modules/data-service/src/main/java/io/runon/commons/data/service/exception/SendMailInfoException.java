package io.runon.commons.data.service.exception;
/**
 * 전송 메일 정보에 문제가 있는경우
 * @author macle
 */
public class SendMailInfoException extends RuntimeException{

    public SendMailInfoException(){
        super();
    }

    public SendMailInfoException(Exception e){
        super(e);
    }

    public SendMailInfoException(String message){
        super(message);
    }
}
