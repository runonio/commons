import io.runon.commons.config.Config;
import io.runon.crypto.service.CryptoServiceStarter;

public class Stater {
    public static void main(String[] args) {
        Config.getConfig("");
        CryptoServiceStarter.startApp();
    }
}
