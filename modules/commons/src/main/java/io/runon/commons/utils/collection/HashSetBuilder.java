package io.runon.commons.utils.collection;

import java.util.HashSet;

/**
 * Hash 관련 유틸
 * @author macle
 */
public class HashSetBuilder<E> {
    private final HashSet<E> set = new HashSet<>();


    public HashSetBuilder<E> add(E e) {
        set.add(e);
        return this;
    }

    public HashSet<E> get() {
        return set;
    }

}
