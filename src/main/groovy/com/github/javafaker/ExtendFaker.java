package com.github.javafaker;

import java.lang.reflect.InvocationTargetException;

public class ExtendFaker extends Faker {

    private final Faker1 faker1;

    public Faker1 faker1() {
        return faker1;
    }

    public ExtendFaker() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        super();

        faker1 = new Faker1(this);
    }
}
