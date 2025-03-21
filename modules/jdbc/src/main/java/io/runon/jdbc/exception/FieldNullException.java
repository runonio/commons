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
package io.runon.jdbc.exception;
/**
 * field 가 null 일때
 * @author macle
 */
public class FieldNullException extends RuntimeException{
	
	
	private static final long serialVersionUID = 6355980120432389512L;
	private final String message;

	/**
	 * 생성자
	 * @param message String exception message
	 */
	public FieldNullException(String message){
		super(message + " is filed not found");
		this.message = message + " is filed not found";
	}
	@Override
	public String getMessage(){
		return message;
	}
	
}