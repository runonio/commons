package io.runon.commons.utils;

import java.util.*;

/**
 * Hash 관련 유틸
 * @author macle
 */
public class HashMapBuilder<K, V> {

    private final HashMap<K, V> map = new HashMap<>();


    public HashMapBuilder<K,V> put(K key, V value) {
        map.put(key,value);
        return this;
    }

    public HashMap<K, V> getMap() {
        return map;
    }
}
