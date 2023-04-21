package com.metacoding.myblog.controller.api;

import com.metacoding.myblog.dto.ResponseDto;
import com.metacoding.myblog.model.RoleType;
import com.metacoding.myblog.model.User;
import com.metacoding.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> saveUser(@RequestBody User user) {
        userService.saveJoin(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PostMapping("/api/user1")
    public int saveUser1(@RequestBody User user) {
        System.out.println("UserApiController: save 호출됨");

        return 1;
    }

    @PostMapping("/api/user2")
    public ResponseDto<Integer> saveUser2(@RequestBody User user) {
        System.out.println("UserApiController: save 호출됨");

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @PostMapping("/api/user3")
    public ResponseDto<Integer> saveUser3(@RequestBody User user) {
        user.setRole(RoleType.USER);
        int result = userService.saveJoin1(user);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), result);
    }

    /**
     * Exception에 대한 모든 처리 권한이 handler.GlobalExceptionHandler 클래스에 있기 때문에
     * 위의 saveUser3() 메소드에서 처럼 userRepository.saveJoin1(user)에 대한 return을 받지 않아도
     * 오류가 발생시 동일하게 처림됨
     */
    @PostMapping("/api/user")
    public ResponseDto<Integer> saveUser4(@RequestBody User user) {
        // user 오브젝트에는 username, password, email만 존재하므로, role은 별도로 입력해 줌
        user.setRole(RoleType.USER);
        userService.saveJoin(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    /**
     * Spring Security를 사용하지 않는 전통적인 login
     * 49강 이후로는 해당 메소드를 이용한 login을 사용하지 않음
     */
    @PostMapping("/api/user/login2")
    public ResponseDto<Integer> loginBef(@RequestBody User user) {
        System.out.println("UserApiController: login호출됨");
        // principal(접근주체)
        User principal = userService.loginBef(user);

        if(principal != null) {
            session.setAttribute("principal", principal);
        }
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

}
