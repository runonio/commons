import io.lettuce.core.RedisFuture;
import io.runon.commons.data.service.redis.RedisSubListener;
import io.runon.commons.data.service.redis.ServiceRedis;
import io.runon.commons.utils.time.Times;

import java.util.concurrent.ExecutionException;

public class SubTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ServiceRedis.getInstance().addListener(new RedisSubListener());
        RedisFuture<Void> future  = ServiceRedis.getInstance().getPubCommands().subscribe("data_test_channel");

        try {
            future.get();
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

        Thread.sleep(Times.DAY_1);
    }
}
