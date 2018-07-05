package com.github.javafaker;

import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.util.Locale;
import java.util.Random;

public class Faker1 {

    private final Faker faker;

    protected Faker1(Faker faker) {
        this.faker = faker;
    }

    public String faker() {
        return faker.resolve("faker1.faker");
    }

}
