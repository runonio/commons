package io.runon.commons.data.service.exception;
/**
 * 전송 메일 정보에 문제가 있는경우
 * @author macle
 */
public class MailSendFailException  extends RuntimeException{

    public MailSendFailException(){
        super();
    }

    public MailSendFailException(Exception e){
        super(e);
    }

    public MailSendFailException(String message){
        super(message);
    }

}
