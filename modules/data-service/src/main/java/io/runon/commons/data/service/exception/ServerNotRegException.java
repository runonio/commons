package io.runon.commons.data.service.exception;
/**
 * 서버가 등록되지 않은 경우
 * @author macle
 */
public class ServerNotRegException extends RuntimeException{

    public ServerNotRegException(){
        super();
    }

    public ServerNotRegException(Exception e){
        super(e);
    }

    public ServerNotRegException(String message){
        super(message);
    }

}
