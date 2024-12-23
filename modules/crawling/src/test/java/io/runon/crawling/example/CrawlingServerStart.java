/*
 * Copyright (C) 2020 Seomse Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.runon.crawling.example;

import io.runon.commons.api.server.ApiServer;
import io.runon.crawling.CrawlingManager;

/**
 * server start
 * @author macle
 */
public class CrawlingServerStart {
    public static void main(String[] args) {

        ApiServer apiServer = new ApiServer(33001,"com.seomse");
        apiServer.start();

        //noinspection ResultOfMethodCallIgnored
        CrawlingManager.getInstance();

    }

}
