
package io.runon.commons.example;

import io.runon.commons.utils.string.RandomString;

/**
 * @author macle
 */
public class RandomStringExample {

    public static void main(String[] args) {
        RandomString randomString = new RandomString();
        String value = randomString.create(true,true,false,true,12);
        System.out.println(value);
    }
}
