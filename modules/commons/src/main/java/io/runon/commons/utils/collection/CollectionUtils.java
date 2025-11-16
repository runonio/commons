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
package io.runon.commons.utils.collection;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * java.util.Collection 패지키 유틸성 메소드
 *
 * @author macle
 */
public class CollectionUtils {
	/**
	 * 동등비교 null 인식
	 * @param obj Collection
	 * @param obj2 Collection
	 * @return boolean equals
	 */
	@SuppressWarnings("rawtypes") 
	public static boolean equals(Collection obj , Collection obj2) {
		
		if(obj  == null && obj2 == null) {
			return true;
		}
		
		if(obj == null) {
			return false;
		}
		
		
		if(obj2 == null) {
			return false;
		}

		return obj.equals(obj2);
	}

    /**
     * 객체형이 아니라 toArray로 빼내기 어려운 기본형에 대한 메소드
     */
	public static long [] getLongs(Collection<Long> longCollection){
		long [] values = new long[longCollection.size()];
		int index = 0;

		for(Long l : longCollection){
			values[index++] = l;
		}
		return values;
	}



    /**
     * 객체형이 아니라 toArray로 빼내기 어려운 기본형에 대한 메소드
     */
    public static int [] getInts(Collection<Integer> collections){
        int [] values = new int[collections.size()];
        int index = 0;
        for(Integer value : collections){
            values[index++] = value;
        }
        return values;
    }
    
    /**
     * 객체형이 아니라 toArray로 빼내기 어려운 기본형에 대한 메소드
     */
    public static double [] getDoubles(Collection<Double> collections){
        double [] values = new double[collections.size()];
        int index = 0;
        for(Double value : collections){
            values[index++] = value;
        }
        return values;
    }


    public static Set<String> makeStrSet(String text){
		return makeStrSet(text, ",",true);
	}

	public static  Set<String> makeStrSet(String text, String splitRegex, boolean isTrim){
		String [] lines = text.split(splitRegex);
		Set<String> set = new HashSet<>();

		for(String line : lines){
			if(isTrim){
				set.add(line.trim());
			}else{
				set.add(line);
			}
		}

		return set;
	}







}
