package io.runon.commons.service;

import io.runon.commons.config.Config;

/**
 * @author macle
 */
public class SyncServiceInitializer  implements Initializer {

    @Override
    public void init() {
        if(Config.getBoolean("sync.service.flag", true)) {
            new SyncService().start();
        }
    }
}

