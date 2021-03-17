package com.tdd.service.impl;


import com.tdd.service.BirthdayService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BasicBirthdayService implements BirthdayService {

    @Override
    public LocalDate getValidBirthday(String birthdayString) {
        return null;
    }

    @Override
    public String getBirthDOW(LocalDate birthday) {
        return null;
    }

    @Override
    public String getChineseZodiac(LocalDate birthday) {
        return null;
    }

    @Override
    public String getStarSign(LocalDate birthday) {
        return null;
    }
}
