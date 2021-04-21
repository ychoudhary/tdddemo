package com.tdd.service.impl;


import com.tdd.service.BirthdayService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class BasicBirthdayService implements BirthdayService {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate getValidBirthday(String birthdayString) {
        if (birthdayString == null) {
            throw new RuntimeException("Must include birthday");
        }
        try {
            LocalDate birthdate = LocalDate.parse(birthdayString, formatter);
            return birthdate;
        } catch (Exception e) {
            throw new RuntimeException("Must include valid birthday in yyyy-MM-dd format");
        }
    }

    @Override
    public String getBirthDOW(LocalDate birthday) {
        return birthday.getDayOfWeek().toString();
    }

    /*
        Chinese Zodiac
        Get the Mod of the Year(year % 12)
        0 -> Monkey
        1 -> Rooster
        2 -> Dog
        3 -> Pig
        4 -> Rat
        5 -> Ox
        6 -> Tiger
        7 -> Rabbit
        8 -> Dragon
        9 -> Snake
        10 -> Horse
        11 -> Sheep
     */
    @Override
    public String getChineseZodiac(LocalDate birthday) {
        return null;
    }

    /*
    Aquarius - 20 Jan - 18 Feb
    Pisces - 19 Feb - 20 Mar
    Aries - 21 Mar - 19 Apr
    Taurus - 20 Apr - 20 May
    Gemini - 21 May - 20 Jun
    Cancer - 21 June - 22 Jul
    Leo - 23 Jul - 22 Aug
    Virgo - 23 Aug - 22 Sept
    Libra - 23 Sept- 22 Oct
    Scorpio - 23 oct - 21 Nov
    Sagittarius - 22 Nov - 21 Dec
    Capricorn - 22 Dec - 19 Jan
     */
    @Override
    public String getStarSign(LocalDate birthday) {
        return null;
    }
}
