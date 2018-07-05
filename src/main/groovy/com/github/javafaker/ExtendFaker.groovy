package com.github.javafaker;

import java.lang.reflect.InvocationTargetException;

public class ExtendFaker extends Faker {

    private final Faker1 faker1;

    private final FakerRest fakerRest;

    public Faker1 faker1() {
        return faker1;
    }

    public String fakerRest(String baseUrl, String path, String xpath) {
        return fakerRest.rest(baseUrl, path, xpath);
    }

    public ExtendFaker() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        super();

        faker1 = new Faker1(this);
        fakerRest = new FakerRest(this);
    }
}
