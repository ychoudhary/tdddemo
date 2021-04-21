package com.tdd.controller;

import com.tdd.data.Mortage;
import com.tdd.service.MortageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class MortageController {

    @Resource
    private final MortageService mortageService;

    public MortageController(MortageService mortageService) {
        this.mortageService = mortageService;
    }


    @GetMapping("/mortage")
    public Mortage greeting(@RequestParam(value = "principal") int principal,@RequestParam(value = "duration") int duration) {
        return mortageService.calculateMortage(principal,duration);
    }
}
