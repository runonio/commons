import io.runon.commons.data.service.redis.Redis;

public class PubTest {
    public static void main(String[] args) {
        Redis.publish("data_test_channel","test");
    }
}
