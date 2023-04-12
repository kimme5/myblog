package com.metacoding.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloController {

    @GetMapping("/")
//    @RequestMapping(value="/index", method= RequestMethod.GET)
    public String hell() {
        System.out.println("home controller start");
        return "index";
    }
}
