
package io.runon.commons.apis.socket.communication;
/**
 * host address and port
 * connect default info
 * @author macle
 */
public class HostAddrPort {
    private String hostAddress;
    private int port;


    /**
     * 생성자
     */
    public HostAddrPort(){

    }

    /**
     * 생성자
     * @param hostAddress string host address
     * @param port int port
     */
    public HostAddrPort(String hostAddress, int port){
        this.hostAddress = hostAddress;
        this.port = port;
    }

    /**
     * @return host address
     */
    public String getHostAddress() {
        return hostAddress;
    }

    /**
     * host address setting
     * @param hostAddress string
     */
    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    /**
     * @return int port number
     */
    public int getPort() {
        return port;
    }

    /**
     * port setting
     * @param port int
     */
    public void setPort(int port) {
        this.port = port;
    }
}
