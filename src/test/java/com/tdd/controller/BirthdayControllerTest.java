package com.tdd.controller;

import com.tdd.data.Greeting;
import com.tdd.service.BirthdayService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

class BirthdayControllerTest {

    @MockBean
    BirthdayService birthdayService;


    BirthdayController birthdayController = new BirthdayController(birthdayService);

}