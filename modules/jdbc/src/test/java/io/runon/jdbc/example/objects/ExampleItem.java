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

import io.runon.jdbc.annotation.Column;
import io.runon.jdbc.annotation.PrimaryKey;
import io.runon.jdbc.annotation.Table;

/**
 * @author macle
 */
@Table(name="COPY_TEMP")
public class ExampleItem {
    @PrimaryKey(seq = 1)
    @Column(name = "ITEM_CD")
    private String code;

    @Column(name = "ITEM_NM")
    private String name;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
