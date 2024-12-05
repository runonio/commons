/*
 * Copyright (C) 2021 Seomse Inc.
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

package com.seomse.jdbc.exception;

import java.sql.SQLException;

/**
 * SQLException
 * @author macle
 */
public class SQLRuntimeException extends RuntimeException{
    /**
     * 생성자
     * @param e exception
     */
    public SQLRuntimeException(SQLException e){
        super(e);
    }
}
