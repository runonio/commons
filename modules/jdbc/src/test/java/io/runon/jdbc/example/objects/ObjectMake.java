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
package io.runon.jdbc.example.objects;

import io.runon.jdbc.connection.ApplicationConnectionPool;
import io.runon.jdbc.objects.JdbcObjects;

/**
 * @author macle
 */
public class ObjectMake {

    public static void main(String[] args) {
        //noinspection ResultOfMethodCallIgnored
        ApplicationConnectionPool.getInstance();

        String tableName = "file";
        System.out.println("\nclass make info");
        System.out.println(JdbcObjects.makeObjectValue(tableName, false));


    }
}
