package com.metacoding.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/joinForm")
    public String joinFormBef() {
        return "/user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginFormBef() {
        return "/user/loginForm";
    }

    /**
     * 인증이 안된 사용자들이 출입할 수 있는 경로 /auth/** 허용
     * url이 "/"인 경우 index.jsp 허용
     * static 이하에 있는 /js/**, /css/**, /image/** 허용
     */
    @GetMapping("/auth/joinForm")
    public String joinForm() {
        return "/user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm() {
        return "/user/loginForm";
    }
}
