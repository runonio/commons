package io.runon.commons.data.service.redis;

import io.lettuce.core.pubsub.RedisPubSubListener;
/**
 * @author macle
 */
public class RedisSubListener implements RedisPubSubListener<String, String> {

    @Override
    public void message(String channel, String message) {
//        log.info("1 Received message: {} from channel: {}", message, channel);
    }

    @Override
    public void message(String pattern, String channel, String message) {
//        log.info("2 message to pattern: {}, message: {}", pattern, message);
    }


    @Override
    public void subscribed(String channel, long count) {
//        log.info("3 Subscribed to channel: {}, count: {}", channel, count);
    }

    @Override
    public void psubscribed(String pattern, long count) {
//        log.info("4 Psubscribed to pattern: {}, count: {}", pattern, count);
    }

    @Override
    public void unsubscribed(String channel, long count) {
//        log.info("5 Unsubscribed from channel: {}, count: {}", channel, count);
    }

    @Override
    public void punsubscribed(String pattern, long count) {
//        log.info("6 Punsubscribed from pattern: {}, count: {}", pattern, count);
    }
}