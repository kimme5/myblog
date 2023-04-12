package com.metacoding.myblog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring이 com.metatcoding.myblog 패키지 이하를 스캔해서 모든 파일을 메모리에 new 하는 것이 아니라,
 * 특정 어노테이션이 붙어있는 클래스 파일들을 new 해서(IoC) 스프링 컨테이너에 관리해 줌
 * */
@RestController
public class BlogControllerTest {

    @GetMapping("/test/hello")
    public String hello() {
        return "<h1>Hello Spring Boot for 토리와 콩자!</h1>";
    }
}
