package com.tdd.controller;

import com.tdd.service.BirthdayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/birthday")
public class BirthdayController {
    @Resource
    private final BirthdayService birthdayService;

    public BirthdayController(BirthdayService birthdayService) {
        this.birthdayService = birthdayService;
    }

    @GetMapping("/dayOfYear")
    public String getDayOfYear() {
        LocalDate birthday = LocalDate.now();
        return String.valueOf(birthday.getDayOfYear());
    }

    @PostMapping("/dayOfWeek")
    public String getDayOfWeek(@RequestBody String birthdayString) {
        LocalDate birthday = birthdayService.getValidBirthday(birthdayString);
        String dow = birthdayService.getBirthDOW(birthday);
        return dow;
    }

    @PostMapping("/chineseZodiac")
    public String getChineseZodiac(@RequestBody String birthdayString) {
        LocalDate birthday = birthdayService.getValidBirthday(birthdayString);
        String sign = birthdayService.getChineseZodiac(birthday);
        return sign;
    }

    @PostMapping("/starSign")
    public String getStarSign(@RequestBody String birthdayString) {
        LocalDate birthday = birthdayService.getValidBirthday(birthdayString);
        String sign = birthdayService.getStarSign(birthday);
        return sign;
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
        return new ResponseEntity<Exception>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
