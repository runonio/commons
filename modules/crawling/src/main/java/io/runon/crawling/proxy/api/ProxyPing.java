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

package io.runon.crawling.proxy.api;

import io.runon.commons.api.ApiMessage;
import io.runon.commons.api.Messages;

/**
 * 연결 유지 용 ping
 * @author macle
 */
public class ProxyPing  extends ApiMessage {

    @Override
    public void receive(String message) {
        sendMessage(Messages.SUCCESS);
    }
}
