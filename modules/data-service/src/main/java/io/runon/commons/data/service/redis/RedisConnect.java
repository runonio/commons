package io.runon.commons.data.service.redis;

import io.lettuce.core.api.StatefulConnection;
import io.lettuce.core.api.async.RedisHashAsyncCommands;
import io.lettuce.core.api.async.RedisStringAsyncCommands;
import io.lettuce.core.api.sync.RedisHashCommands;
import io.lettuce.core.api.sync.RedisListCommands;
import io.lettuce.core.api.sync.RedisStringCommands;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;

/**
 * @author macle
 */
public interface RedisConnect extends AutoCloseable{

    RedisStringCommands<String, String> syncString();
    RedisHashCommands<String, String> syncHash();

    RedisListCommands<String, String> syncList();


    RedisStringAsyncCommands<String, String> asyncString();
    RedisHashAsyncCommands<String, String> asyncHash();

    StatefulConnection<String, String> connection();
    StatefulRedisPubSubConnection<String, String> connectPubSub();



}
