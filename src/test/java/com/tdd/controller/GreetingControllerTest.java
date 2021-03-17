package com.tdd.controller;

import com.tdd.data.Greeting;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

class GreetingControllerTest {


    GreetingController greetingController = new GreetingController();

    @Test
    void greeting() {
        Greeting gretting = greetingController.greeting("IES");
        Assert.hasText("Hello IES", gretting.toString());
    }
}