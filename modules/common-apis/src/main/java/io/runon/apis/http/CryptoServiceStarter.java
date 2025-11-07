package io.runon.apis.http;

import io.runon.apis.boot.RunonBootApp;
import io.runon.commons.config.Config;
/**
 * @author macle
 */
public class CryptoServiceStarter {
    public static void main(String[] args) {
        Config.setConfig("crypto.service.flag", "Y");
        RunonBootApp.start();
    }
}
