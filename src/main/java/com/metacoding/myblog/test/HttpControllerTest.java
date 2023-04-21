package com.metacoding.myblog.test;

import org.springframework.web.bind.annotation.*;

/**
 * @RestController --> 사용자의 Request에 대해 서버가 Data로 Response함
 * @Controller --> 사용자의 Request에 대해 서버가 html 파일로 Response함
 * */
@RestController
public class HttpControllerTest {

    private static final String TAG = "HttpControllerTest: ";

    @GetMapping("/http/lombok")
    public String lombokTest() {
        Member member = new Member(1, "tori", "tori!1234", "tori@gmail.com");
        System.out.println(TAG + "getter: " + member.getId());
        member.setId(5000);
        System.out.println(TAG + "setter: " + member.getId());

        return "lombok test 완료";
    }

    @GetMapping("/http/lombok2")
    public String lombokTest2() {
        /**
         * Member Class 내에서 lombok 에서 지원하는 @Builder 어노테이션을 사용하면 아래와 같이
         * DB 에서 자동 생성하는 id를 제외한 나머지 정보들만을 전달할 수 있음
         * */
        Member member = Member.builder().username("kongja").password("kongja!1234").email("kongja@gmail.com").build();
        System.out.println(TAG + "getter: " + member.getId());
        member.setId(5000);
        System.out.println(TAG + "setter: " + member.getId());

        return "lombok test2 완료";
    }

    // 인터넷 브라우저 Request는 무조건 Get요청 밖에 할 수 없음
    @GetMapping("/http/get")
//    public String getTest(@RequestParam int id, @RequestParam String username) {
    public String getTest(Member member) {
        return "get요청: id = " + member.getId()
                + ", username = " + member.getUsername()
                + ", password = " + member.getPassword()
                + ", email = " + member.getEmail();
    }

    // post, put, delete는 모두 405 error가 발생함
    @PostMapping("/http/post")
//    public String postTest(@RequestBody int id
//                         , @RequestBody String username
//                         , @RequestBody String password
//                         , @RequestBody String email)
    public String postTest(Member member) {
        return "post요청: id = " + member.getId()
                + ", username = " + member.getUsername()
                + ", password = " + member.getPassword()
                + ", email = " + member.getEmail();
    }

    // MIME - text/plain
    @PostMapping("/http/post2")
    public String postTest2(@RequestBody String text) {
        return "post요청2: " + text;
    }

    // MIME - application/json
    @PostMapping("/http/post3")
    public String postTest3(@RequestBody Member member) {
        return "post요청3: id = " + member.getId()
                + ", username = " + member.getUsername()
                + ", password = " + member.getPassword()
                + ", email = " + member.getEmail();
    }

    @PutMapping("/http/put")
    public String putTest() {
        return "put Request";
    }

    @PutMapping("/http/put2")
    public String putTest2(@RequestBody Member member) {
        return "put요청2: id = " + member.getId()
                + ", username = " + member.getUsername()
                + ", password = " + member.getPassword()
                + ", email = " + member.getEmail();
    }

    @DeleteMapping("/http/delete")
    public String deleteTest() {
        return "delete Request";
    }
}
