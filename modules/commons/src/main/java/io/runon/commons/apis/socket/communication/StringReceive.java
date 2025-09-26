
package io.runon.commons.apis.socket.communication;


import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 문자열 받기
 * 대용량 메시지를 전달받기 위해 개발함
 * @author macle
 */
public class StringReceive {
	private Socket socket ;
	private InputStreamReader reader ;
	private final char [] charBuffer;

	/**
	 * 생성자
	 * @param socket Socket
	 * @param bufSize int receive buffer size
	 * @throws IOException IOException
	 */
	public StringReceive(Socket socket, int bufSize) throws IOException{
		this.socket =socket;
		reader  = new InputStreamReader(socket.getInputStream(), CommunicationDefault.CHAR_SET);
		charBuffer = new char[bufSize];
	}
	

	/**
	 * 메시지 얻기
	 * @return Socket receive message
	 * @throws IOException IOException
	 */
	public String receive() throws IOException{
	
		int i = reader.read(charBuffer);
		if( i == -1){
			return null;
		}
		
		
		return new String(charBuffer,0,i);
	}
	
	/**
	 * 연결 해제
	 */
	public void disConnect(){

		//noinspection CatchMayIgnoreException
		try{
			if(reader != null ){
				reader.close();	
			}
		}catch(Exception e){}

		reader = null;
		//noinspection CatchMayIgnoreException
		try{
			if(socket != null && !socket.isClosed() ){
				socket.close();
			}
		}catch(Exception e){}
		socket = null;
	}
	
}
