package com.metacoding.myblog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempController {

    // http://localhost:8080/myblog/temp/home
    @GetMapping("/temp/home")
    public String tempHome() {
        /**
         * 파일리턴 기본경로 : src/main/resources/static
         * 리턴명 : /home.html
         * 풀경로 : src/main/resources/static/home.html
         * */
        return "/home.html";
    }

    @GetMapping("/temp/img")
    public String tempImg() {
        return "/a.png";
    }

    @GetMapping("/temp/jsp")
    // prefix: /WEB-INF/views/
    // suffix: .jsp
    // full name : /WEB-INF/views/test.jsp
    public String tempJsp() {
        return "test";
    }

}
