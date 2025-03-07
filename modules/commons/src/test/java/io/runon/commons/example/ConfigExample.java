
package io.runon.commons.example;

import io.runon.commons.config.Config;
import io.runon.commons.config.ConfigSet;

/**
 * config test
 * @author macle
 */
public class ConfigExample {
    public static void main(String[] args) {
        ConfigSet.IS_SYSTEM_PROPERTIES_USE = true;

        System.out.println(Config.getConfig("application.jdbc.type"));
    }
}
