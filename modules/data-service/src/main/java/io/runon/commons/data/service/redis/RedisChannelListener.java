package io.runon.commons.data.service.redis;
/**
 * @author macle
 */
public class RedisChannelListener extends RedisSubListener {

    private final String channel;
    public RedisChannelListener(String channel){
        this.channel = channel;
    }

    @Override
    public void message(String channel, String message) {
        if(channel.equals(this.channel)){
            message(message);
        }
    }

    public void message(String message){

    }
}
