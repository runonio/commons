
package io.runon.collect.crawling.core.http;

/**
 * option key
 * @author macle
 */
public class HttpOptionDataKey {

    /**
     * request_method (GET, POST, HEAD, OPTIONS, PUT, DELETE, TRACE)
     */
    public static final String REQUEST_METHOD = "request_method";

    /**
     * request_property (+Cookie)
     */
    public static final String REQUEST_PROPERTY = "request_property";


    /**
     * character_set (def : UTF-8)
     */
    public static final String CHARACTER_SET = "character_set";


    /**
     * output_stream_write
     */
    public static final String OUTPUT_STREAM_WRITE = "output_stream_write";


    /**
     * read_time_out (def : 30000)
     */
    public static final String READ_TIME_OUT = "read_time_out";


    /**
     * connect_time_out (def : 30000)
     */
    public static final String CONNECT_TIME_OUT = "connect_time_out";


}
